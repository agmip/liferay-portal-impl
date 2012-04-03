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

import java.io.InputStream;

import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * @author Alexander Chow
 */
public abstract class VelocityResourceListener {

	public static final String JOURNAL_SEPARATOR = "_JOURNAL_CONTEXT_";

	public static final String SERVLET_SEPARATOR = "_SERVLET_CONTEXT_";

	public static final String THEME_LOADER_SEPARATOR =
		"_THEME_LOADER_CONTEXT_";

	public abstract InputStream getResourceStream(String source)
		throws ResourceNotFoundException;

}