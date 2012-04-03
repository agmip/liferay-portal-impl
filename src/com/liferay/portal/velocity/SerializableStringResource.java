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

import java.io.Serializable;

import org.apache.velocity.runtime.resource.util.StringResource;

/**
 * @author Michael C. Han
 */
public class SerializableStringResource implements Serializable {

	public SerializableStringResource(String body, String encoding) {
		_body = body;
		_encoding = encoding;
	}

	public String getBody() {
		return _body;
	}

	public String getEncoding() {
		return _encoding;
	}

	public StringResource toStringResource() {
		return new StringResource(_body, _encoding);
	}

	private String _body;
	private String _encoding;

}