/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.v5_2_3.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class UserTable {

	public static final String TABLE_NAME = "User_";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"userId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"defaultUser", Types.BOOLEAN},
		{"contactId", Types.BIGINT},
		{"password_", Types.VARCHAR},
		{"passwordEncrypted", Types.BOOLEAN},
		{"passwordReset", Types.BOOLEAN},
		{"passwordModifiedDate", Types.TIMESTAMP},
		{"reminderQueryQuestion", Types.VARCHAR},
		{"reminderQueryAnswer", Types.VARCHAR},
		{"graceLoginCount", Types.INTEGER},
		{"screenName", Types.VARCHAR},
		{"emailAddress", Types.VARCHAR},
		{"openId", Types.VARCHAR},
		{"portraitId", Types.BIGINT},
		{"languageId", Types.VARCHAR},
		{"timeZoneId", Types.VARCHAR},
		{"greeting", Types.VARCHAR},
		{"comments", Types.VARCHAR},
		{"firstName", Types.VARCHAR},
		{"middleName", Types.VARCHAR},
		{"lastName", Types.VARCHAR},
		{"jobTitle", Types.VARCHAR},
		{"loginDate", Types.TIMESTAMP},
		{"loginIP", Types.VARCHAR},
		{"lastLoginDate", Types.TIMESTAMP},
		{"lastLoginIP", Types.VARCHAR},
		{"lastFailedLoginDate", Types.TIMESTAMP},
		{"failedLoginAttempts", Types.INTEGER},
		{"lockout", Types.BOOLEAN},
		{"lockoutDate", Types.TIMESTAMP},
		{"agreedToTermsOfUse", Types.BOOLEAN},
		{"active_", Types.BOOLEAN}
	};

	public static final String TABLE_SQL_CREATE = "create table User_ (uuid_ VARCHAR(75) null,userId LONG not null primary key,companyId LONG,createDate DATE null,modifiedDate DATE null,defaultUser BOOLEAN,contactId LONG,password_ VARCHAR(75) null,passwordEncrypted BOOLEAN,passwordReset BOOLEAN,passwordModifiedDate DATE null,reminderQueryQuestion VARCHAR(75) null,reminderQueryAnswer VARCHAR(75) null,graceLoginCount INTEGER,screenName VARCHAR(75) null,emailAddress VARCHAR(75) null,openId VARCHAR(1024) null,portraitId LONG,languageId VARCHAR(75) null,timeZoneId VARCHAR(75) null,greeting VARCHAR(255) null,comments STRING null,firstName VARCHAR(75) null,middleName VARCHAR(75) null,lastName VARCHAR(75) null,jobTitle VARCHAR(75) null,loginDate DATE null,loginIP VARCHAR(75) null,lastLoginDate DATE null,lastLoginIP VARCHAR(75) null,lastFailedLoginDate DATE null,failedLoginAttempts INTEGER,lockout BOOLEAN,lockoutDate DATE null,agreedToTermsOfUse BOOLEAN,active_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table User_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_3A1E834E on User_ (companyId)",
		"create index IX_6EF03E4E on User_ (companyId, defaultUser)",
		"create unique index IX_615E9F7A on User_ (companyId, emailAddress)",
		"create unique index IX_C5806019 on User_ (companyId, screenName)",
		"create unique index IX_9782AD88 on User_ (companyId, userId)",
		"create unique index IX_5ADBE171 on User_ (contactId)",
		"create index IX_762F63C6 on User_ (emailAddress)",
		"create index IX_A9ED7DD3 on User_ (openId)",
		"create index IX_A18034A4 on User_ (portraitId)",
		"create index IX_E0422BDA on User_ (uuid_)"
	};

}