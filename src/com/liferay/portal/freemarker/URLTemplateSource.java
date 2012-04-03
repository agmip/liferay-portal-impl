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
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

/**
 * @author Mika Koivisto
 */
public class URLTemplateSource {

	public URLTemplateSource(URL url) throws IOException {
		_url = url;
		_urlConnection = url.openConnection();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof URLTemplateSource) {
			URLTemplateSource urlTemplateSource = (URLTemplateSource)obj;

			if (_url.equals(urlTemplateSource._url)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return _url.hashCode();
	}

	@Override
	public String toString() {
		return _url.toString();
	}

	protected void closeStream() throws IOException {
		try {
			if (_inputStream != null) {
				_inputStream.close();
			}
			else {
				_urlConnection.getInputStream().close();
			}
		}
		finally {
			_inputStream = null;
			_urlConnection = null;
		}
	}

	protected InputStream getInputStream() throws IOException {
		_inputStream = _urlConnection.getInputStream();

		return _inputStream;
	}

	protected long getLastModified() {
		return _urlConnection.getLastModified();
	}

	private InputStream _inputStream;
	private URL _url;
	private URLConnection _urlConnection;

}