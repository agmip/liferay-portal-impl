<#list finderColsList as finderCol>
	<#if !finderCol.isPrimitiveType()>
		if (${finderCol.name} != null) {
	</#if>

	qPos.add(

	<#if finderCol.type == "Date">
		CalendarUtil.getTimestamp(
	</#if>

	${finderCol.name}${serviceBuilder.getPrimitiveObjValue("${finderCol.type}")}

	<#if finderCol.type == "Date">
		)
	</#if>

	);

	<#if !finderCol.isPrimitiveType()>
		}
	</#if>
</#list>