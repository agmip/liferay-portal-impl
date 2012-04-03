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

package com.liferay.portlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.portlet.ClientDataRequest;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class ClientDataRequestImpl
	extends PortletRequestImpl implements ClientDataRequest {

	public String getCharacterEncoding() {
		return getHttpServletRequest().getCharacterEncoding();
	}

	public int getContentLength() {
		return getHttpServletRequest().getContentLength();
	}

	public String getContentType() {
		return getHttpServletRequest().getContentType();
	}

	@Override
	public String getMethod() {
		return getHttpServletRequest().getMethod();
	}

	public InputStream getPortletInputStream() throws IOException {
		return getHttpServletRequest().getInputStream();
	}

	public BufferedReader getReader()
		throws IOException, UnsupportedEncodingException {

		_calledGetReader = true;

		return getHttpServletRequest().getReader();
	}

	public void setCharacterEncoding(String enc)
		throws UnsupportedEncodingException {

		if (_calledGetReader) {
			throw new IllegalStateException();
		}

		getHttpServletRequest().setCharacterEncoding(enc);
	}

	private boolean _calledGetReader;

}