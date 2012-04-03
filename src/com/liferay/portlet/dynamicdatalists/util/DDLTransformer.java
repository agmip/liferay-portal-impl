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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.templateparser.BaseTransformer;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

/**
 * @author Marcellus Tavares
 */
public class DDLTransformer extends BaseTransformer {

	@Override
	protected String getTemplateParserClassName(String langType) {
		return PropsUtil.get(
			PropsKeys.DYNAMIC_DATA_LISTS_TEMPLATE_LANGUAGE_PARSER,
			new Filter(langType));
	}

	@Override
	protected String[] getTransformerListenersClassNames() {
		return PropsUtil.getArray(
			PropsKeys.DYNAMIC_DATA_LISTS_TRANSFORMER_LISTENER);
	}

}