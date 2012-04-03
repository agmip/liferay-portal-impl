package ${packagePath}.service;

import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

public class ${entity.name}${sessionTypeName}ServiceClp implements ${entity.name}${sessionTypeName}Service {

	public ${entity.name}${sessionTypeName}ServiceClp(ClassLoaderProxy classLoaderProxy) {
		_classLoaderProxy = classLoaderProxy;

		<#list methods as method>
			<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
				<#assign parameters = method.parameters>

				_${method.name}MethodKey${method_index} = new MethodKey(_classLoaderProxy.getClassName(), "${method.name}"

				<#list parameters as parameter>
					, ${serviceBuilder.getLiteralClass(parameter.type)}
				</#list>

				);
			</#if>
		</#list>
	}

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			<#assign returnTypeName = serviceBuilder.getTypeGenericsName(method.returns)>
			<#assign parameters = method.parameters>

			<#if method.name = "dynamicQuery">
				@SuppressWarnings("rawtypes")
			</#if>

			public ${returnTypeName} ${method.name}(

			<#list parameters as parameter>
				${serviceBuilder.getTypeGenericsName(parameter.type)} ${parameter.name}

				<#if parameter_has_next>
					,
				</#if>
			</#list>

			)

			<#list method.exceptions as exception>
				<#if exception_index == 0>
					throws
				</#if>

				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				<#if returnTypeName != "void">
					Object returnObj = null;
				</#if>

				MethodHandler methodHandler = new MethodHandler(_${method.name}MethodKey${method_index}

				<#list parameters as parameter>
					<#assign parameterTypeName = serviceBuilder.getTypeGenericsName(parameter.type)>

					<#if (parameterTypeName == "boolean") || (parameterTypeName == "double") || (parameterTypeName == "float") || (parameterTypeName == "int") || (parameterTypeName == "long") || (parameterTypeName == "short")>
						, ${parameter.name}
					<#else>
						, ClpSerializer.translateInput(${parameter.name})
					</#if>
				</#list>

				);

				try {
					<#if returnTypeName != "void">
						returnObj =
					</#if>

					_classLoaderProxy.invoke(methodHandler);
				}
				catch (Throwable t) {
					<#list method.exceptions as exception>
						if (t instanceof ${exception.value}) {
							throw (${exception.value})t;
						}
					</#list>

					if (t instanceof RuntimeException) {
						throw (RuntimeException)t;
					}
					else {
						throw new RuntimeException(t.getClass().getName() + " is not a valid exception");
					}
				}

				<#if returnTypeName != "void">
					<#if returnTypeName == "boolean">
						return ((Boolean)returnObj).booleanValue();
					<#elseif returnTypeName == "double">
						return ((Double)returnObj).doubleValue();
					<#elseif returnTypeName == "float">
						return ((Float)returnObj).floatValue();
					<#elseif returnTypeName == "int">
						return ((Integer)returnObj).intValue();
					<#elseif returnTypeName == "long">
						return ((Long)returnObj).longValue();
					<#elseif returnTypeName == "short">
						return ((Short)returnObj).shortValue();
					<#else>
						return (${returnTypeName})ClpSerializer.translateOutput(returnObj);
					</#if>
				</#if>
			}
		</#if>
	</#list>

	public ClassLoaderProxy getClassLoaderProxy() {
		return _classLoaderProxy;
	}

	private ClassLoaderProxy _classLoaderProxy;

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			<#assign parameters = method.parameters>

			private MethodKey _${method.name}MethodKey${method_index};
		</#if>
	</#list>

}