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
public class LiferayTemplateSource {

	public LiferayTemplateSource(
		FreeMarkerTemplateLoader freeMarkerTemplateLoader,
		Object templateSource) {

		_freeMarkerTemplateLoader = freeMarkerTemplateLoader;
		_templateSource = templateSource;
	}

	public void close() {
		_freeMarkerTemplateLoader.closeTemplateSource(_templateSource);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LiferayTemplateSource) {
			LiferayTemplateSource liferayTemplateSource =
				(LiferayTemplateSource)obj;

			if (liferayTemplateSource._freeMarkerTemplateLoader.equals(
					_freeMarkerTemplateLoader) &&
				liferayTemplateSource._templateSource.equals(_templateSource)) {

				return true;
			}
		}

		return false;
	}

	public long getLastModified() {
		return _freeMarkerTemplateLoader.getLastModified(_templateSource);
	}

	public Reader getReader(String encoding) throws IOException {
		return _freeMarkerTemplateLoader.getReader(_templateSource, encoding);
	}

	@Override
	public int hashCode() {
		return _freeMarkerTemplateLoader.hashCode() +
			(31 * _templateSource.hashCode());
	}

	@Override
	public String toString() {
		return _templateSource.toString();
	}

	private FreeMarkerTemplateLoader _freeMarkerTemplateLoader;
	private Object _templateSource;

}