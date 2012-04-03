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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionsListFilterFactory {

	public static PermissionsListFilter getInstance() {
		if (_permissionsListFilter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Instantiate " + PropsValues.PERMISSIONS_LIST_FILTER);
			}

			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			try {
				_permissionsListFilter =
					(PermissionsListFilter)classLoader.loadClass(
						PropsValues.PERMISSIONS_LIST_FILTER).newInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return " + _permissionsListFilter.getClass().getName());
		}

		return _permissionsListFilter;
	}

	public static void setInstance(
		PermissionsListFilter permissionsListFilter) {

		if (_log.isDebugEnabled()) {
			_log.debug("Set " + permissionsListFilter.getClass().getName());
		}

		_permissionsListFilter = permissionsListFilter;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PermissionsListFilterFactory.class);

	private static PermissionsListFilter _permissionsListFilter;

}