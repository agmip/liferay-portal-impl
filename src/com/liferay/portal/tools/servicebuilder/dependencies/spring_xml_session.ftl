<bean id="${packagePath}.service.${entity.name}${sessionType}Service" class="${packagePath}.service.impl.${entity.name}${sessionType}ServiceImpl" />

<#if pluginName != "">
	<bean id="${packagePath}.service.${entity.name}${sessionType}ServiceUtil" class="${packagePath}.service.${entity.name}${sessionType}ServiceUtil">
		<property name="service" ref="${packagePath}.service.${entity.name}${sessionType}Service" />
	</bean>
</#if>