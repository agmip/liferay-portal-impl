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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;

/**
 * @author Mika Koivisto
 */
public abstract class URLTemplateLoader extends FreeMarkerTemplateLoader {

	@Override
	public void closeTemplateSource(Object templateSource) {
		if (templateSource instanceof URLTemplateSource) {
			URLTemplateSource urlTemplateSource =
				(URLTemplateSource)templateSource;

			try {
				urlTemplateSource.closeStream();
			}
			catch (IOException ioe) {
			}
		}
	}

	@Override
	public Object findTemplateSource(String name) throws IOException {
		URL url = getURL(name);

		if (url != null) {
			return new URLTemplateSource(url);
		}

		return null;
	}

	@Override
	public long getLastModified(Object templateSource) {
		if (templateSource instanceof URLTemplateSource) {
			URLTemplateSource urlTemplateSource =
				(URLTemplateSource)templateSource;

			return urlTemplateSource.getLastModified();
		}

		return super.getLastModified(templateSource);
	}

	@Override
	public Reader getReader(Object templateSource, String encoding)
		throws IOException {

		if (templateSource instanceof URLTemplateSource) {
			URLTemplateSource urlTemplateSource =
				(URLTemplateSource)templateSource;

			return new UnsyncBufferedReader(
				new InputStreamReader(
					urlTemplateSource.getInputStream(), encoding));
		}

		return null;
	}

	public abstract URL getURL(String name) throws IOException;

}