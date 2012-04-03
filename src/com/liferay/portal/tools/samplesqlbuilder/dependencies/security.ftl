<#setting number_format = "0">

insert into Resource_ values (${resource.resourceId}, ${resource.codeId}, '${resource.primKey}');

<#assign permissions = dataFactory.addPermissions(resource)>

<#list permissions as permission>
	insert into Permission_ values (${permission.permissionId}, ${companyId}, '${permission.actionId}', ${permission.resourceId});
</#list>

<#assign rolesPermissions = dataFactory.addRolesPermissions(resource, permissions, dataFactory.siteMemberRole)>

<#list rolesPermissions as kvp>
	insert into Roles_Permissions values (${kvp.key}, ${kvp.value});
</#list>