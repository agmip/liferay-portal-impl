#!/bin/ksh -p
# Uncomment next line for testing
# set -x
#------------------------------------------------------------------------
# Name:               mailadmin.ksh
# Purpose:            Provide Liferay a shell interface to any Mail Subsystem
#                     
# Environment:        Linix/AIX/Solaris/FreeBSD
# Usage:
#   mailadmin --help
#   mailadmin  
#   mailadmin addForward          [userId] [emailAddresses]
#   mailadmin addUser             [userId] [password] [firstName] [middleName] [lastName] [emailAddress]
#   mailadmin addVacationMessage  [userId] [emailAddress] [vacationMessage]
#   mailadmin deleteEmailAddress  [userId]
#   mailadmin deleteUser          [userId]
#   mailadmin updateBlocked       [userId] [blockedEmailAddress]
#   mailadmin updateEmailAddress  [userId] [emailAddress]
#   mailadmin updatePassword      [userId] [password]
#                     
# Author:             mlawrence
# Date:               Feb-22-2005
# Environment:        exec from JVM or run interactively from command shell
#
# Installation:
#   cp mailadmin.ksh /usr/sbin
#   chmod 750    /usr/sbin/mailadmin.ksh
#   chgrp tomcat /usr/sbin/mailadmin.ksh
#   chown tomcat /usr/sbin/mailadmin.ksh
#   touch        /var/log/mailadmin.log
#   chmod 660    /var/log/mailadmin.log
#   chgrp tomcat /var/log/mailadmin.log
#   chown tomcat /var/log/mailadmin.log
#   vi /usr/local/tomcat/common/classes/portal-ext.properties
#      mail.hook.impl=com.liferay.mail.util.ShellHook
#      mail.hook.shell.script=/usr/sbin/mailadmin.ksh
#      mail.box.style=INBOX
#   /usr/local/tomcat/common/lib/ext/mail-ejb.jar   -> add MailAdminHook.class
#   /usr/local/tomcat/common/lib/ext/portal-ejb.jar -> update PropsUtil.class
#
#
#
#Before using the script below, you should make a few replacements:
#
# domain being installed (eg xyz.com)
#   replace "EMAILDOMAIN"   "xyz.com"         --  mailadmin.ksh
#
# mysql email database user (eg DBUSR)
#   replace "DBUSR"         "DBUSR"           --  mailadmin.ksh
#
# mysql email database password (eg dbpassw1d)
#   replace "DBPASSWD"      "dbpassw1d"          --  mailadmin.ksh
#
#
#
# Change History:
#   Date         Who  Description
#   Feb 22, 2005 MAL  Initial version
#   Mar  7, 2005 MAL  Added dovecot_addForward,  dovecot_updatePassword
#
#########################################################################
#                            CONSTANTS
#########################################################################
# GLOBAL CONSTANTS 
DOMAIN=EMAILDOMAIN                  # Domain being managed
MYSQL_USERNAME=DBUSR                # MySQL user
MYSQL_PASSWORD=DBPASSWD             # MySQL password
MYSQL_DATABASE=mail                 # MySQL database name
MAIL_TYPE=DOVECOT                   # DOVECOT, SENDMAIL, OR CYRUS

umask 007                           # Set file creation mask u=rwx,g=rwx,o=rwx
integer LOG_FILE_MAX=200000         # Max file length in bytes
LOG_FILENAME=/var/log/mailadmin.log # Log file or blank for stdout
MYSQL_ENV="--user=$MYSQL_USERNAME --password=$MYSQL_PASSWORD --database=$MYSQL_DATABASE --host=127.0.0.1"

VMAIL_GROUP=vmail                   # Mail File Creation group
VMAIL_PERM=770                      # Mail File Creation permissions
TOMCAT_UID=500                      # Mail File Creation user id for tomcat user
VMAIL_GID=510                       # Mail File Creation group id - vmail
VMAIL_HOME=/var/vmail               # Mail root directory

#########################################################################
#                            VARIABLES
#########################################################################
userId=""
emailAddresses=""
password=""
firstName=""
middleName=""
lastName=""
emailAddress=""
vacationMessage=""
blockedEmailAddress=""

#########################################################################
#                            UTILS
#########################################################################
# Execute a shell command, log the results if any error or any output occurs
function LogCmdOut { # CallingFunctionName Cmd

   if [[ $# != 2 ]] then
     ErrorExit "LogCmd" "expected 2 parms, found $# $1,$2,$3,$4,$5,$6,$7,$8"
   fi
   LCFunction=$1
   LCCmd=$2

   $LCCmd 2>/tmp/LogCmd.out >/tmp/LogCmd.out
   LCResult=$?
   LCOutput=$(cat /tmp/LogCmd.out 2>/dev/null)
   if [[ $LCResult != 0 ]] || [[ -n $LCOutput ]] then
      WriteLog "INFO" "LogCmdOut: $LCFunction:$LCCmd=$LCResult Output: $LCOutput"
   fi
   rm -f "/tmp/LogCmd.out" >/dev/null 2>/dev/null # ignore errors

   return $LCResult
}
#------------------------------------------------------------------------
# remove a file, ignore errors
function KillFile  {   # Filename
   if [[ $# != 1 ]] then
     return   # ignore removing empty names
   else
      KFFileName=$1
      if [[ -f  $KFFileName ]] then        # if it exists
         LogCmdOut "KillFile" "rm -f $KFFileName"     # remove it
      fi
   fi
}

#------------------------------------------------------------------------
# write status message to log file or stdout
function WriteLog { # level, msg
   PurgeLog   # make sure log doesnt grow too big
   #2002-01-20 01:02:30
   TODAY=`date "+20%y-%m-%d %H:%M:%S"`

   msg="$TODAY $1 mailadmin.ksh $gsUserName $2" 
   if [[ -n $LOG_FILENAME ]] then
      echo $msg >> $LOG_FILENAME
      echo $msg
   else
      echo $1 $2 # log to console
   fi
}

#------------------------------------------------------------------------
# remove logfile over LOG_FILE_MAX bytes
function PurgeLog {
   integer PLFileLen
   if [[ -n $LOG_FILENAME ]] then    # if we are not using stdout (ie logfile is not blank)
         if [[ -f $LOG_FILENAME ]] then # if logfile exists
            PLFileLen=$(ls -alL $LOG_FILENAME 2>/dev/null | awk '{print $5}')
            if [[ -n $PLFileLen ]] then
               if (( $PLFileLen > $LOG_FILE_MAX )) ; then
                   TODAY=`date "+20%y-%m-%d %H:%M:%S"`
                   # dont remove file or you'll break the stdout connection to the processes writing to it
                   echo "$TODAY mailadmin.ksh Purged $LOG_FILENAME FileLen:$PLFileLen bytes. MaxLen:$LOG_FILE_MAX " > $LOG_FILENAME
               fi
            fi
         fi
   fi
}


#########################################################################
#                    INIT/EXIT FUNCTIONS
#########################################################################
#------------------------------------------------------------------------
# Initialize the Application
function AppInit { 
   TODAY=`date "+20%y-%m-%d %H:%M:%S"`   # get a time stamp   

   # extract user id from id command:  uid=28191(systemsp) gid=100(vuser) groups=100(vuser) -> systemsp
   #   don't use whoami, it lives in different places on different OS's
   gsUserName=$(  id | awk '{print $1}' |  sed s/.\*\(//  )  
   gsUserName=${gsUserName%?}      # remove trailing paren:  systemsp)  ->  systemsp

   
}
#------------------------------------------------------------------------
function ErrorExit { # Function, Msg
   WriteLog "ERROR" "ErrorExit: $gsServerName:mailadmin.ksh:$1: $2"
   CommonExit  1
}
#------------------------------------------------------------------------
function CommonExit { # resultCode
   trap     EXIT     # Remove exit trap  (This doesnt work for some reason)
   if [[ $gsAppMode != "menu" ]] then
      WriteLog "INFO" "CommonExit: $gsAppMode Exited rc=$1"
      exit $1  # exit return code
   else
      exit 0   # no errors if exiting menu
   fi
}
# --------------------------------------------------------------------
function getServerHostName {
 gsServerName=$(hostname |  sed s/\\..*//  )         # get this computer's host name. remove anything after the 1st period (eg sun.com -> sun)
}
#------------------------------------------------------------------------
# GET IP ADDRESS
function getServerIPAddress {
   getServerHostName
   gsOS=$(uname)
   if [[ $gsOS = "SunOS" ]] then  # SunOS Operating System
      gsServerPublicIP=$(/usr/sbin/ping -v -n -I 1 $gsServerName 1 1 | grep bytes | awk '{print $4}')
      gsServerPublicIP=${gsServerPublicIP%?}      # eg 63.73.131.95   private IP
   fi
   if [[ $gsOS = "HP-UX" ]] then  # HP Unix
      gsServerPublicIP=$(  /usr/sbin/ping  -v $gsServerName -n 1 | grep from | awk '{print $4}'  )
      gsServerPublicIP=${gsServerPublicIP%?}      # eg 63.73.131.95   private IP
   fi 
   if [[ $gsOS = "AIX" ]] then  # AIX
      gsServerPublicIP=$(/usr/sbin/ping $gsServerName 1 1 | grep from  | awk '{print $4}' | grep -v crc)
      gsServerPublicIP=${gsServerPublicIP%?}      # eg 63.73.131.95   private IP
   fi 
   if [[ $gsOS = "FreeBSD" ]] then  # FreeBSD Operating System
      gsServerPublicIP=$(ping -c 1 $gsServerName | grep from  | awk '{print $4}' | grep -v crc )
      gsServerPublicIP=${gsServerPublicIP%?}      # eg 63.73.131.95   private IP
   fi
   if [[ $gsOS = "Linux" ]] then  # FreeBSD Operating System
       #64 bytes from linux00001 (11.48.13.74): icmp_seq=1 ttl=64 time=0.069 ms
      gsServerPublicIP=$(ping -c 1 $gsServerName | grep from  | head -1 | awk '{print $5}'  )
   fi
}
#------------------------------------------------------------------------
# called by any virtual method that is not implemented
function notImplemented { # methodName
   WriteLog "WARN" "$1: not implemented"
   return 0
}

######################## VIRTUAL METHODS ########################################
function addForward { # userId, emailAddresses
    userId=$1
    emailAddresses=$2
    WriteLog "INFO" "$MAIL_TYPE addForward: userId=$userId emailAddresses=$emailAddresses"
    case $MAIL_TYPE in  # select an implemention based on MAIL_TYPE
        "DOVECOT")  dovecot_addForward;
                    return $?;;
        "SENDMAIL") sendmail_addForward;
                    return $?;;
        "CYRUS")    cyrus_addForward;
                    return $?;;
        *)          notImplemented "addForward"
                    return $?;;
    esac    
}
#------------------------------------------------------------------------
function addUser { # userId password firstName middleName lastName emailAddress
    userId=$1
    password=$2
    firstName=$3
    middleName=$4
    lastName=$5
    emailAddress=$6
    WriteLog "INFO" "$MAIL_TYPE addUser: userId=$userId password=$password firstName=$firstName middleName=$middleName lastName=$lastName emailAddress=$emailAddress"
    case $MAIL_TYPE in  # select an implemention based on MAIL_TYPE
        "DOVECOT")  dovecot_addUser;
                    return $?;;
        "SENDMAIL") sendmail_addUser;
                    return $?;;
        "CYRUS")    cyrus_addUser;
                    return $?;;
        *)          notImplemented "addUser"
                    return $?;;
    esac    
}

#------------------------------------------------------------------------
function addVacationMessage { # userId emailAddress vacationMessage
    userId=$1
    emailAddress=$2
    vacationMessage=$3
    WriteLog "INFO" "$MAIL_TYPE addVacationMessage: userId=$userId emailAddress=$emailAddress vacationMessage=$vacationMessage"
    case $MAIL_TYPE in  # select an implemention based on MAIL_TYPE
        "DOVECOT")  dovecot_addVacationMessage;
                    return $?;;
        "SENDMAIL") sendmail_addVacationMessage;
                    return $?;;
        "CYRUS")    cyrus_addVacationMessage;
                    return $?;;
        *)          notImplemented "addVacationMessage"
                    return $?;;
    esac    
}

#------------------------------------------------------------------------
function deleteEmailAddress { # userId
    userId=$1
    WriteLog "INFO" "$MAIL_TYPE deleteEmailAddress: userId=$userId "
    case $MAIL_TYPE in  # select an implemention based on MAIL_TYPE
        "DOVECOT")  dovecot_deleteEmailAddress;
                    return $?;;
        "SENDMAIL") sendmail_deleteEmailAddress;
                    return $?;;
        "CYRUS")    cyrus_deleteEmailAddress;
                    return $?;;
        *)          notImplemented "deleteEmailAddress"
                    return $?;;
    esac    
}

#------------------------------------------------------------------------
function deleteUser { # userId
    userId=$1
    WriteLog "INFO" "$MAIL_TYPE deleteUser: userId=$userId "
    case $MAIL_TYPE in  # select an implemention based on MAIL_TYPE
        "DOVECOT")  dovecot_deleteUser;
                    return $?;;
        "SENDMAIL") sendmail_deleteUser;
                    return $?;;
        "CYRUS")    cyrus_deleteUser;
                    return $?;;
        *)          notImplemented "deleteUser"
                    return $?;;
    esac    
}

#------------------------------------------------------------------------
function updateBlocked { # userId blockedEmailAddress
    userId=$1
    blockedEmailAddress=$2
    WriteLog "INFO" "$MAIL_TYPE updateBlocked: userId=$userId blockedEmailAddress=$blockedEmailAddress"
    case $MAIL_TYPE in  # select an implemention based on MAIL_TYPE
        "DOVECOT")  dovecot_updateBlocked;
                    return $?;;
        "SENDMAIL") sendmail_updateBlocked;
                    return $?;;
        "CYRUS")    cyrus_updateBlocked;
                    return $?;;
        *)          notImplemented "updateBlocked"
                    return $?;;
    esac    
}


#------------------------------------------------------------------------
function updateEmailAddress { # userId emailAddress
    userId=$1
    emailAddress=$2
    
    WriteLog "INFO" "$MAIL_TYPE updateEmailAddress: userId=$userId emailAddress=$emailAddress"
    case $MAIL_TYPE in  # select an implemention based on MAIL_TYPE
        "DOVECOT")  dovecot_updateEmailAddress;
                    return $?;;
        "SENDMAIL") sendmail_updateEmailAddress;
                    return $?;;
        "CYRUS")    cyrus_updateEmailAddress;
                    return $?;;
        *)          notImplemented "updateEmailAddress"
                    return $?;;
    esac    
}

#------------------------------------------------------------------------
function updatePassword { # userId password	
    userId=$1
    password=$2
    WriteLog "INFO" "$MAIL_TYPE updatePassword: userId=$userId password=$password"
    case $MAIL_TYPE in  # select an implemention based on MAIL_TYPE
        "DOVECOT")  dovecot_updatePassword;
                    return $?;;
        "SENDMAIL") sendmail_updatePassword;
                    return $?;;
        "CYRUS")    cyrus_updatePassword;
                    return $?;;
        *)          notImplemented "updatePassword"
                    return $?;;
    esac    
}

######################## DOVECOT ########################################
# Dovecot, Postfix, Mysql
# See: Liferay-Dovecot.txt


function dovecot_addForward { # userId, emailAddresses
# userId=liferay_com_1 emailAddresses=test1@cnn.com,test2@cnn.com
    # The tag FORWARD_PREFIX is prepended to the login email to prevent forwarding
    FORWARD_PREFIX=forward.liferay.com_
    WriteLog "INFO" "addForward: Finding email for userId=$userId"    
    liferayLogin=$(mysql $MYSQL_ENV --silent --execute "select email from postfix_virtual where destination=\"$userId@$DOMAIN\" " | tail -1)
    mysqlResult=$?
    if [[ $mysqlResult != 0 || $liferayLogin == "email" ]] then
       WriteLog "ERROR" "addForward: Cant find email in MySQL Database:mail Table:postfix_virtual. mysqlResult=$mysqlResult. liferayLogin=$liferayLogin"
       return 1
    fi
    WriteLog "INFO" "addForward: liferayLogin=$liferayLogin"    
    
    # remove any prefix
    emailPrefix=$(echo $liferayLogin | cut -c1-20)    
    if [[ $emailPrefix == "$FORWARD_PREFIX" ]] then
       # Remove email prefix
       liferayLogin=$(echo $liferayLogin | cut -c21-)
       WriteLog "INFO" "addForward: real liferayLogin=$liferayLogin"       
       # if no forwarding emails are specidied, then
       if [[ $emailAddresses == "_" ]] then
           WriteLog "INFO" "addForward: removing forwards"
           mysql $MYSQL_ENV --silent --execute "update postfix_virtual set email=\"$liferayLogin\" WHERE destination=\"$userId@$DOMAIN\" "
       fi    
    fi
    
    # Remove any existing forwards
    WriteLog "INFO" "addForward: removing existing forwards"
    mysql $MYSQL_ENV --silent --execute "delete from postfix_virtual where email= \"$liferayLogin\" and destination <> \"$userId@$DOMAIN\"  "
    
    # for each email address
    if [[ $emailAddresses != "_" ]] then
       # convert commas to spaces
       emailAddresses=$(echo $emailAddresses | sed "s/,/ /"g )
       for emailAddress in $emailAddresses
       do
          WriteLog "INFO" "addForward: Adding forward to emailAddress=$emailAddress"
          mysql $MYSQL_ENV --silent --execute "insert into postfix_virtual (email,destination) values(  \"$liferayLogin\" ,  \"$emailAddress\"  )  "
       done
       # change liferay email so it doesn't get forwarded
       WriteLog "INFO" "addForward: Disabling local email by prepending $FORWARD_PREFIX"
       mysql $MYSQL_ENV --silent --execute "update postfix_virtual set email= \"$FORWARD_PREFIX$liferayLogin\" where destination= \"$userId@$DOMAIN\"  "
    fi
    return 0
}

#------------------------------------------------------------------------
function dovecot_addUser { # userId password firstName middleName lastName emailAddress

    # Eg /usr/sbin/mailadmin.ksh liferay_com_22 passwd jim  jimy jimy@$DOMAIN
    
    # INSERT EMAIL ACCOUNT INTO MYSQL TABLE postfix_users 
    #  select * from postfix_users 
    #   id   email                           clear  crypt  name  uid  gid  homedir     maildir                                  quota  access  postfix  
    #   22   liferay_com_11@$DOMAIN  mypass              510  510  /var/vmail  $DOMAIN/liferay_com_11/Maildir/         Y       Y 
    WriteLog "INFO" "$MAIL_TYPE dovecot_addUser: Inserting user into MySQL database=$MYSQL_DATABASE"
    mysql $MYSQL_ENV --execute "insert into postfix_users values (0, \"$userId@$DOMAIN\", \"$password\", \"\", \"\", \"$TOMCAT_UID\", \"$VMAIL_GID\", \"$VMAIL_HOME\", \"$DOMAIN/$userId/Maildir/\", \"\", \"Y\", \"Y\");"
    mysqlResult=$?
    if [ $mysqlResult -ne 0 ]; then
        WriteLog "ERROR" "$MAIL_TYPE dovecot_addUser: Unable to add '$userId@$DOMAIN'.MySqlResult=$mysqlResult. Check MySQL settings: user=$MYSQL_USERNAME database=$MYSQL_DATABASE"         
        return $mysqlResult
    fi    

    # INSERT EMAIL ALIAS INTO MYSQL TABLE postfix_virtual
    #  select * from postfix_virtual 
    #   id   email                      destination  
    #   27   someuser@$DOMAIN   liferay_com_11@$DOMAIN 
    WriteLog "INFO" "$MAIL_TYPE dovecot_addUser: Adding email alias into MySQL database=$MYSQL_DATABASE"
    mysql $MYSQL_ENV --execute "insert into postfix_virtual values (0, \"$emailAddress\", \"$userId@$DOMAIN\");"
    mysqlResult=$?
    if [ $mysqlResult -ne 0 ]; then
        WriteLog "ERROR" "$MAIL_TYPE dovecot_addUser: Unable to add '$userId@$DOMAIN'.MySqlResult=$mysqlResult. Check MySQL settings: user=$MYSQL_USERNAME database=$MYSQL_DATABASE"         
        return $mysqlResult
    fi    
        
    # CREATE EMAIL DIRECTORY
    WriteLog "INFO" "$MAIL_TYPE dovecot_addUser: Creating path $VMAIL_HOME/$DOMAIN/$userId"
    mkdir -p "$VMAIL_HOME/$DOMAIN/$userId/Maildir"
    mkdirResult=$?
    if [ $mkdirResult -ne 0 ]; then
       WriteLog "ERROR" "$MAIL_TYPE dovecot_addUser: Unable to create path $VMAIL_HOME/$DOMAIN/$userId/Maildir. Check permissions"         
       return $mkdirResult
    fi

    # SET PERMISSIONS ON EMAIL DIRECTORY
    WriteLog "INFO" "$MAIL_TYPE dovecot_addUser: chmod $VMAIL_PERM path $VMAIL_HOME/$DOMAIN/$userId"         
    chmod -R $VMAIL_PERM "$VMAIL_HOME/$DOMAIN/$userId"
    chmodResult=$?
    if [ $chmodResult -ne 0 ]; then
        WriteLog "ERROR" "$MAIL_TYPE dovecot_addUser: Unable to chmod path $VMAIL_HOME/$DOMAIN/$userId. Check permissions."
        return $chmodResult
    fi
    
    # SET GROUP OF EMAIL DIRECTORY
    WriteLog "INFO" "$MAIL_TYPE dovecot_addUser: chgrp path $VMAIL_HOME/$DOMAIN/$userId. grp=$VMAIL_GROUP"         
    chgrp -R $VMAIL_GROUP "$VMAIL_HOME/$DOMAIN/$userId"
    chgrpResult=$?
    if [ $chgrpResult -ne 0 ]; then
       WriteLog "ERROR" "$MAIL_TYPE dovecot_addUser: Unable to chgrp path $VMAIL_HOME/$DOMAIN/$userId. Check permissions. grp=$VMAIL_GROUP"         
       return $chgrpResult
    fi
    
    return 0
}

#------------------------------------------------------------------------
function dovecot_addVacationMessage { # userId emailAddress vacationMessage
    notImplemented dovecot_addVacationMessage
}

#------------------------------------------------------------------------
function dovecot_deleteEmailAddress { # userId
    # REMOVE EMAIL ACCOUNT FROM MYSQL TABLE postfix_virtual, Ignore errors
    WriteLog "INFO" "$MAIL_TYPE dovecot_deleteUser: Removing user from MySQL database=$MYSQL_DATABASE table: postfix_virtual"
    mysql $MYSQL_ENV --execute "delete from postfix_virtual where destination = \"$userId@$DOMAIN\";"
    mysqlResult=$?
    if [ $mysqlResult -ne 0 ]; then
        WriteLog "ERROR" "$MAIL_TYPE dovecot_deleteUser: Unable to remove '$userId@$DOMAIN'.MySqlResult=$mysqlResult. Check MySQL settings: user=$MYSQL_USERNAME database=$MYSQL_DATABASE"         
        # return $mysqlResult  Continue removing path even if sql fails
    fi    
    return 0
}

#------------------------------------------------------------------------
function dovecot_deleteUser { # userId

    # REMOVE EMAIL ACCOUNT FROM MYSQL TABLE postfix_users 
    WriteLog "INFO" "$MAIL_TYPE dovecot_deleteUser: Removing user from MySQL database=$MYSQL_DATABASE table: postfix_users"
    mysql $MYSQL_ENV --execute "delete from postfix_users where email = \"$userId@$DOMAIN\";"
    mysqlResult=$?
    if [ $mysqlResult -ne 0 ]; then
        WriteLog "ERROR" "$MAIL_TYPE dovecot_deleteUser: Unable to remove '$userId@$DOMAIN'.MySqlResult=$mysqlResult. Check MySQL settings: user=$MYSQL_USERNAME database=$MYSQL_DATABASE"         
        # return $mysqlResult  Continue removing path even if sql fails
    fi    

    # REMOVE EMAIL ACCOUNT FROM MYSQL TABLE postfix_virtual
    WriteLog "INFO" "$MAIL_TYPE dovecot_deleteUser: Removing user from MySQL database=$MYSQL_DATABASE table: postfix_virtual"
    mysql $MYSQL_ENV --execute "delete from postfix_virtual where destination = \"$userId@$DOMAIN\";"
    mysqlResult=$?
    if [ $mysqlResult -ne 0 ]; then
        WriteLog "ERROR" "$MAIL_TYPE dovecot_deleteUser: Unable to remove '$userId@$DOMAIN'.MySqlResult=$mysqlResult. Check MySQL settings: user=$MYSQL_USERNAME database=$MYSQL_DATABASE"         
        # return $mysqlResult  Continue removing path even if sql fails
    fi    
    
    # REMOVE EMAIL DIRECTORIES
    WriteLog "INFO" "$MAIL_TYPE dovecot_deleteUser: removing path $VMAIL_HOME/$DOMAIN/$userId"    
    rm -rf "$VMAIL_HOME/$DOMAIN/$userId"
    rmdirResult=$?
    if [ $rmdirResult -ne 0 ]; then
       WriteLog "ERROR" "$MAIL_TYPE dovecot_deleteUser: Unable to delete path $VMAIL_HOME/$DOMAIN/$userId/Maildir. Check permissions"
       return $rmdirResult
    fi
    return 0
}

#------------------------------------------------------------------------
function dovecot_updateBlocked { # userId blockedEmailAddress
    notImplemented dovecot_updateBlocked
}
#------------------------------------------------------------------------
function dovecot_updateEmailAddress { # userId emailAddress

    # UPDATE EMAIL ALIAS IN MYSQL TABLE postfix_virtual
    #  select * from postfix_virtual 
    #   id   email                      destination  
    #   27   someuser@$DOMAIN   liferay_com_11@$DOMAIN 
    WriteLog "INFO" "$MAIL_TYPE dovecot_updateEmailAddress: Updating email alias in MySQL database=$MYSQL_DATABASE Table postfix_virtual"
    mysql $MYSQL_ENV --execute "update postfix_virtual set email=\"$emailAddress\" where destination=\"$userId@$DOMAIN\";"
    mysqlResult=$?
    if [ $mysqlResult -ne 0 ]; then
        WriteLog "ERROR" "$MAIL_TYPE dovecot_updateEmailAddress: Unable to update '$userId@$DOMAIN'. MySqlResult=$mysqlResult. Check MySQL settings: user=$MYSQL_USERNAME database=$MYSQL_DATABASE"         
        return $mysqlResult
    fi    
    return 0
}
#------------------------------------------------------------------------
function dovecot_updatePassword { # userId password	
    # UPDATE PASSWORD IN MYSQL TABLE postfix_users  
    #  select * from postfix_users 
    #   id   email                           clear  
    #   22   liferay_com_11@$DOMAIN          mypass 
    WriteLog "INFO" "$MAIL_TYPE dovecot_updatePassword: updating user password in MySQL database=$MYSQL_DATABASE table: postfix_users"
    mysql $MYSQL_ENV --execute "update postfix_users set clear=\"$password\" where email=\"$userId@$DOMAIN\" ;"
    mysqlResult=$?
    if [ $mysqlResult -ne 0 ]; then
        WriteLog "ERROR" "$MAIL_TYPE dovecot_updatePassword: Unable to update '$userId@$DOMAIN'. MySqlResult=$mysqlResult. Check MySQL settings: user=$MYSQL_USERNAME database=$MYSQL_DATABASE"   
        return $mysqlResult
    fi    
}
######################## SENDMAIL ########################################
function sendmail_addForward { # userId, emailAddresses
    notImplemented sendmail_addForward
}
#------------------------------------------------------------------------
function sendmail_addUser { # userId password firstName middleName lastName emailAddress
    notImplemented sendmail_addUser
}
#------------------------------------------------------------------------
function sendmail_addVacationMessage { # userId emailAddress vacationMessage
    notImplemented sendmail_addVacationMessage
}
#------------------------------------------------------------------------
function sendmail_deleteEmailAddress { # userId
    notImplemented sendmail_deleteEmailAddress
}
#------------------------------------------------------------------------
function sendmail_deleteUser { # userId
    notImplemented sendmail_deleteUser
}
#------------------------------------------------------------------------
function sendmail_updateBlocked { # userId blockedEmailAddress
    notImplemented sendmail_updateBlocked
}
#------------------------------------------------------------------------
function sendmail_updateEmailAddress { # userId emailAddress
    notImplemented sendmail_updateEmailAddress
}
#------------------------------------------------------------------------
function sendmail_updatePassword { # userId password	
    notImplemented sendmail_updatePassword
}
######################## CYRUS ########################################
function cyrus_addForward { # userId, emailAddresses
    notImplemented cyrus_addForward
}
#------------------------------------------------------------------------
function cyrus_addUser { # userId password firstName middleName lastName emailAddress
    notImplemented cyrus_addUser
}
#------------------------------------------------------------------------
function cyrus_addVacationMessage { # userId emailAddress vacationMessage
    notImplemented cyrus_addVacationMessage
}
#------------------------------------------------------------------------
function cyrus_deleteEmailAddress { # userId
    notImplemented cyrus_deleteEmailAddress
}
#------------------------------------------------------------------------
function cyrus_deleteUser { # userId
    notImplemented cyrus_deleteUser
}
#------------------------------------------------------------------------
function cyrus_updateBlocked { # userId blockedEmailAddress
    notImplemented cyrus_updateBlocked
}
#------------------------------------------------------------------------
function cyrus_updateEmailAddress { # userId emailAddress
    notImplemented cyrus_updateEmailAddress
}
#------------------------------------------------------------------------
function cyrus_updatePassword { # userId password	
    notImplemented cyrus_updatePassword
}
#########################################################################
#                            USER MENU MODE
#########################################################################

function HelpUsage {
   echo " "
   echo "    mailadmin.ksh                     (Display Interactive Menu)"
   echo " "
   echo "    mailadmin.ksh addForward          <userId> <emailAddresses>"
   echo "    mailadmin.ksh addUser             <userId> <password> <firstName> <middleName> <lastName> <emailAddress>"
   echo "    mailadmin.ksh addVacationMessage  <userId> <emailAddress> <vacationMessage>"
   echo "    mailadmin.ksh deleteEmailAddress  <userId>"
   echo "    mailadmin.ksh deleteUser          <userId>"
   echo "    mailadmin.ksh updateBlocked       <userId> <blockedEmailAddress>"
   echo "    mailadmin.ksh updateEmailAddress  <userId> <emailAddress>"
   echo "    mailadmin.ksh updatePassword      <userId> <password>"
   echo " "
   echo " "
}
#------------------------------------------------------------------------
function UserMenuPrompt {
   echo " "
   echo " "
   echo " *** mailadmin.ksh Menu *** "
   echo " "
   TODAY=`date "+20%y-%m-%d %H:%M:%S"`
prompt="$TODAY $gsServerName $gsServerPublicIP  [$gsUserName]  Menu Option or Enter: "
   PS3=$prompt   # Set prompt
}
#------------------------------------------------------------------------
function UserMenu {
   getServerIPAddress              # set $gsServerPublicIP
   gsAppMode="menu"
   WriteLog "INFO" "UserMenu: Host:$gsServerName "

   UserMenuPrompt
   select i in addForward addUser addVacationMessage deleteEmailAddress deleteUser \
               updateBlocked updateEmailAddress updatePassword \
               Logs Status Help QuitProgram  
   do case $i in
        addForward)  
            read userId?'   Enter userId:'
            read emailAddresses?'   Enter emailAddresses:'
            addForward          $userId $emailAddresses;;
        addUser)             
            read userId?'   Enter userId:'
            read password?'   Enter password:'
            read firstName?'   Enter firstName:'
            read middleName?'   Enter middleName:'
            read lastName?'   Enter lastName:'
            read emailAddress?'   Enter emailAddress:'
            addUser             $userId $password $firstName $middleName $lastName $emailAddress;;
        addVacationMessage)
            read userId?'   Enter userId:'
            read emailAddress?'   Enter emailAddress:'
            read vacationMessage?'   Enter vacationMessage:'
            addVacationMessage 	$userId $emailAddress $vacationMessage;;
        deleteEmailAddress) 
            read userId?'   Enter userId:'
            deleteEmailAddress  $userId;;
        deleteUser) 
            read userId?'   Enter userId:'
            deleteUser          $userId;;
        updateBlocked)
            read userId?'   Enter userId:'
            read blockedEmailAddress?'   Enter blockedEmailAddress:'
            updateBlocked       $userId $blockedEmailAddress;;
        updateEmailAddress)
            read userId?'   Enter userId:'
            read emailAddress?'   Enter emailAddress:'
            updateEmailAddress  $userId $emailAddress;;
        updatePassword) 
            read userId?'   Enter userId:'
            read password?'   Enter password:'
            updatePassword      $userId $password;;
        Status)  echo " ";
                 echo "user=           $gsUserName";
                 echo "MAIL_TYPE=      $MAIL_TYPE"
                 echo "DOMAIN=         $DOMAIN"
                 echo "LOG_FILE_MAX=   $LOG_FILE_MAX"
                 echo "LOG_FILENAME=   $LOG_FILENAME"
                 echo "MYSQL_USERNAME= $MYSQL_USERNAME"
                 echo "MYSQL_DATABASE= $MYSQL_DATABASE"
                 echo "VMAIL_GROUP=    $VMAIL_GROUP"
                 echo "VMAIL_PERM=     $VMAIL_PERM"
                 echo "TOMCAT_UID=     $TOMCAT_UID"
                 echo "VMAIL_GID=      $VMAIL_GID"
                 echo "VMAIL_HOME=     $VMAIL_HOME"                 
                 echo " ";;
        Help)    HelpUsage;;
        Logs)    tail -50 $LOG_FILENAME;;
        QuitProgram) break;;
        "")        print -u2 you must select one of the above options;;
     esac
     UserMenuPrompt
   done 
   WriteLog "INFO" "UserMenu Ended"
}

#########################################################################
#                            MAIN PROGRAM
#########################################################################
   AppInit                 # Get server name and IP address. Set defaults. 
   
   #set up exit trap
   trap 'echo "$TODAY WARN mailadmin.ksh $gsUserName $gsAppMode  Trap Exit errno:$ERRNO line:$LINENO" >> $LOG_FILENAME' 0  

   case $1 in                  # Run the correct program mode
    "--help" | "-?" | "-help") HelpUsage;
                               trap EXIT           # Remove exit trap
                               exit 0;;
    "logs"   | "LOGS")         echo $LOG_FILENAME;  
                               tail -50 $LOG_FILENAME; # display log     
                               trap EXIT           # Remove exit trap
                               exit 0;;
    "addForward")              addForward           $2 $3 $4 $5 $6 $7 $8;
                               CommonExit $?;;
    "addUser")                 addUser              $2 $3 $4 $5 $6 $7 $8;
                               CommonExit $?;;
    "addVacationMessage")      addVacationMessage   $2 $3 $4 $5 $6 $7 $8;
                               CommonExit $?;;
    "deleteEmailAddress")      deleteEmailAddress   $2 $3 $4 $5 $6 $7 $8;  
                               CommonExit $?;;
    "deleteUser")              deleteUser           $2 $3 $4 $5 $6 $7 $8;
                               CommonExit $?;;
    "updateBlocked")           updateBlocked        $2 $3 $4 $5 $6 $7 $8;
                               CommonExit $?;;
    "updateEmailAddress")      updateEmailAddress   $2 $3 $4 $5 $6 $7 $8;
                               CommonExit $?;;
    "updatePassword")          updatePassword       $2 $3 $4 $5 $6 $7 $8;
                               CommonExit $?;;
    *)                         UserMenu    ;; # Run as a command line menu
   esac
   trap EXIT                    # Remove exit trap
   exit 0   
#  End of file.
#------------------------------------------------------------------------
# DO NOT CHANGE LAST LINE. IT CAN BE USED FOR FTP SOFTWARE DEPLOYMENT
# TO MAKE SURE THE ENTIRE FILE WAS TRANSFERRED CORRECTLY
# MAILADMIN_LAST_LINE_MARKER



