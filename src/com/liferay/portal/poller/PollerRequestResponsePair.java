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

package com.liferay.portal.poller;

import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;

/**
 * @author Edward Han
 */
public class PollerRequestResponsePair {

	public PollerRequestResponsePair(PollerRequest pollerRequest) {
		_pollerRequest = pollerRequest;
	}

	public PollerRequestResponsePair(
		PollerRequest pollerRequest, PollerResponse pollerResponse) {

		_pollerRequest = pollerRequest;
		_pollerResponse = pollerResponse;
	}

	public PollerRequest getPollerRequest() {
		return _pollerRequest;
	}

	public PollerResponse getPollerResponse() {
		return _pollerResponse;
	}

	public void setPollerRequest(PollerRequest pollerRequest) {
		_pollerRequest = pollerRequest;
	}

	public void setPollerResponse(PollerResponse pollerResponse) {
		_pollerResponse = pollerResponse;
	}

	private PollerRequest _pollerRequest;
	private PollerResponse _pollerResponse;

}