<#setting number_format = "0">

<#list dataFactory.roles as role>
	insert into Role_ values (${role.roleId}, ${companyId}, ${role.classNameId}, ${role.classPK}, '${role.name}', '', '', ${role.type}, '');
</#list>