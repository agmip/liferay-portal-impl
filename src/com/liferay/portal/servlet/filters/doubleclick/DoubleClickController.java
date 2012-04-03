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

package com.liferay.portal.servlet.filters.doubleclick;

import com.liferay.portal.kernel.servlet.ByteBufferServletResponse;
import com.liferay.util.servlet.filters.CacheResponseData;
import com.liferay.util.servlet.filters.CacheResponseUtil;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Olaf Fricke
 * @author Brian Wing Shun Chan
 */
public class DoubleClickController implements Serializable {

	public void control(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws IOException, ServletException {

		boolean firstRequest = false;

		ByteBufferServletResponse byteBufferResponse = null;

		synchronized (this) {
			if (_byteBufferResponse == null) {
				firstRequest = true;

				_byteBufferResponse = new ByteBufferServletResponse(response);
				_throwable = null;
			}

			byteBufferResponse = _byteBufferResponse;
		}

		if (firstRequest) {
			try {
				filterChain.doFilter(request, _byteBufferResponse);
			}
			catch (Throwable t) {
				_throwable = t;
			}
			finally {
				synchronized (this) {
					_byteBufferResponse = null;

					notifyAll();
				}
			}
		}
		else {
			synchronized (this) {
				while (_byteBufferResponse != null) {
					try {
						wait();
					}
					catch (InterruptedException ie) {
						Thread currentThread = Thread.currentThread();

						currentThread.interrupt();
					}
				}
			}
		}

		if (_throwable != null) {
			try {
				throw _throwable;
			}
			catch (IOException ioe) {
				throw ioe;
			}
			catch (ServletException se) {
				throw se;
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Error e) {
				throw e;
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		CacheResponseData cacheResponseData = new CacheResponseData(
			byteBufferResponse);

		CacheResponseUtil.write(response, cacheResponseData);
	}

	private ByteBufferServletResponse _byteBufferResponse;
	private Throwable _throwable;

}