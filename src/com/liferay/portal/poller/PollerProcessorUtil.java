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

import com.liferay.portal.kernel.poller.PollerProcessor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class PollerProcessorUtil {

	public static void addPollerProcessor(
		String portletId, PollerProcessor pollerProcessor) {

		_instance._addPollerProcessor(portletId, pollerProcessor);
	}

	public static void deletePollerProcessor(String portletId) {
		_instance._deletePollerProcessor(portletId);
	}

	public static PollerProcessor getPollerProcessor(String portletId) {
		return _instance._getPollerProcessor(portletId);
	}

	private PollerProcessorUtil() {
	}

	private void _addPollerProcessor(
		String portletId, PollerProcessor pollerProcessor) {

		_pollerPorcessors.put(portletId, pollerProcessor);
	}

	private void _deletePollerProcessor(String portletId) {
		_pollerPorcessors.remove(portletId);
	}

	private PollerProcessor _getPollerProcessor(String portletId) {
		return _pollerPorcessors.get(portletId);
	}

	private static PollerProcessorUtil _instance = new PollerProcessorUtil();

	private Map<String, PollerProcessor> _pollerPorcessors =
		new ConcurrentHashMap<String, PollerProcessor>();

}