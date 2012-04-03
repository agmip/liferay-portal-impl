<#list entities as entity>
	<#if entity.hasColumns()>
		<mapped-superclass class="${packagePath}.model.impl.${entity.name}ModelImpl">
			<#if entity.hasCompoundPK()>
				<id-class class="${packagePath}.service.persistence.${entity.name}PK" />
			</#if>

			<attributes>
				<#if entity.hasCompoundPK()>
					<#assign pkList = entity.getPKList()>

					<#list pkList as column>
						<id name="${column.name}"

						<#if column.name != column.DBName || column.type == "Date">
							>
							<#if column.name != column.DBName>
								<column name="${column.DBName?upper_case}" />
							</#if>
							<#if column.type == "Date">
								<temporal>TIMESTAMP</temporal>
							</#if>
							</id>
						<#else>
							/>
						</#if>
					</#list>
				<#else>
					<#assign column = entity.getPKList()?first>

					<id name="${column.name}"

					<#if column.name != column.DBName || column.type == "Date">
						>
						<#if column.name != column.DBName>
							<column name="${column.DBName?upper_case}" />
						</#if>
						<#if column.type == "Date">
							<temporal>TIMESTAMP</temporal>
						</#if>

						</id>
					<#else>
						/>
					</#if>
				</#if>

				<#list entity.columnList as column>
					<#if column.EJBName??>
						<#assign ejbName = true>
					<#else>
						<#assign ejbName = false>
					</#if>

					<#if !column.isPrimary() && !column.isCollection() && !ejbName>
						<basic name="${column.name}"

						<#if column.name != column.DBName || column.type == "Date">
							>
							<#if column.name != column.DBName>
								<column name="${column.DBName?upper_case}" />
							</#if>
							<#if column.type == "Date">
								<temporal>TIMESTAMP</temporal>
							</#if>

							</basic>
						<#else>
							/>
						</#if>
					</#if>
				</#list>

				<#list entity.parentTransients as transient>
					<transient name="${transient}" />
				</#list>
			</attributes>
		</mapped-superclass>
	</#if>
</#list>

<#list entities as entity>
	<#if entity.hasColumns()>
		<entity class="${packagePath}.model.impl.${entity.name}Impl" name="${entity.name}">
			<table name="${entity.table}" />

			<attributes>
				<#list entity.transients as transient>
					<transient name="${transient}" />
				</#list>
			</attributes>
		</entity>
	</#if>
</#list>