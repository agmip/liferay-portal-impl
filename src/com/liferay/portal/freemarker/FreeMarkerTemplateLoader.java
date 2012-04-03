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

package com.liferay.portal.freemarker;

import java.io.IOException;
import java.io.Reader;

/**
 * @author Mika Koivisto
 */
public abstract class FreeMarkerTemplateLoader {

	public static final String JOURNAL_SEPARATOR = "_JOURNAL_CONTEXT_";

	public static final String SERVLET_SEPARATOR = "_SERVLET_CONTEXT_";

	public static final String THEME_LOADER_SEPARATOR =
		"_THEME_LOADER_CONTEXT_";

	public void closeTemplateSource(Object templateSource) {
	}

	public abstract Object findTemplateSource(String name) throws IOException;

	public long getLastModified(Object templateSource) {
		return 0;
	}

	public abstract Reader getReader(Object templateSource, String encoding)
		throws IOException;

}