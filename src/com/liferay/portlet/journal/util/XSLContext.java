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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.templateparser.TemplateContext;

import javax.xml.transform.Transformer;

/**
 * @author Brian Wing Shun Chan
 */
public class XSLContext implements TemplateContext {

	public XSLContext(Transformer transformer) {
		_transformer = transformer;
	}

	public Object get(String key) {
		return _transformer.getParameter(key);
	}

	public Transformer getTransformer() {
		return _transformer;
	}

	public void put(String key, Object value) {
		_transformer.setParameter(key, value);
	}

	private Transformer _transformer;

}