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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.InputStream;

import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * @author Brian Wing Shun Chan
 */
public class ClassLoaderVelocityResourceListener
	extends VelocityResourceListener {

	@Override
	public InputStream getResourceStream(String source)
		throws ResourceNotFoundException {

		try {
			return doGetResourceStream(source);
		}
		catch (Exception e) {
			throw new ResourceNotFoundException(source);
		}
	}

	protected InputStream doGetResourceStream(String source) throws Exception {
		if (source.contains(JOURNAL_SEPARATOR) ||
			source.contains(SERVLET_SEPARATOR) ||
			source.contains(THEME_LOADER_SEPARATOR)) {

			return null;
		}

		ClassLoader classLoader = getClass().getClassLoader();

		if (_log.isDebugEnabled()) {
			_log.debug("Loading " + source);
		}

		return classLoader.getResourceAsStream(source);
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClassLoaderVelocityResourceListener.class);

}