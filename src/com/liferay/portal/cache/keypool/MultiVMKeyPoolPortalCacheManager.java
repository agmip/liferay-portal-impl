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

package com.liferay.portal.cache.keypool;

import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.SingleVMPool;

import java.net.URL;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class MultiVMKeyPoolPortalCacheManager implements PortalCacheManager {

	public void clearAll() {
		for (MultiVMKeyPoolPortalCache multiVMKeyPoolPortalCache :
				_multiVMKeyPoolPortalCaches.values()) {

			multiVMKeyPoolPortalCache.removeAll();
		}
	}

	public PortalCache getCache(String name) {
		return getCache(name, false);
	}

	public PortalCache getCache(String name, boolean blocking) {
		MultiVMKeyPoolPortalCache multiVMKeyPoolPortalCache =
			_multiVMKeyPoolPortalCaches.get(name);

		if (multiVMKeyPoolPortalCache != null) {
			return multiVMKeyPoolPortalCache;
		}

		synchronized (_multiVMKeyPoolPortalCaches) {
			PortalCache clusterPortalCache = _multiVMPool.getCache(
				name, blocking);
			PortalCache localPortalCache = _singleVMPool.getCache(
				name, blocking);

			multiVMKeyPoolPortalCache = new MultiVMKeyPoolPortalCache(
				clusterPortalCache, localPortalCache);

			multiVMKeyPoolPortalCache.registerCacheListener(
				new MultiVMKeyPoolCacheListener(localPortalCache),
				CacheListenerScope.REMOTE);

			_multiVMKeyPoolPortalCaches.put(name, multiVMKeyPoolPortalCache);
		}

		return multiVMKeyPoolPortalCache;
	}

	public void reconfigureCaches(URL configurationURL) {
	}

	public void removeCache(String name) {
		synchronized (_multiVMKeyPoolPortalCaches) {
			MultiVMKeyPoolPortalCache multiVMKeyPoolPortalCache =
				_multiVMKeyPoolPortalCaches.get(name);

			if (multiVMKeyPoolPortalCache != null) {
				_multiVMPool.removeCache(name);
				_singleVMPool.removeCache(name);

				_multiVMKeyPoolPortalCaches.remove(name);
			}
		}
	}

	public void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	public void setSingleVMPool(SingleVMPool singleVMPool) {
		_singleVMPool = singleVMPool;
	}

	private Map<String, MultiVMKeyPoolPortalCache> _multiVMKeyPoolPortalCaches =
		new ConcurrentHashMap<String, MultiVMKeyPoolPortalCache>();
	private MultiVMPool _multiVMPool;
	private SingleVMPool _singleVMPool;

}