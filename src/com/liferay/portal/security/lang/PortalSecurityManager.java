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

package com.liferay.portal.security.lang;

import java.security.Permission;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalSecurityManager extends SecurityManager {

	@Override
	public void checkPermission(Permission permission) {
		if (permission.getName().equals(_PERMISSION_EXIT_VM)) {
			Thread.dumpStack();
		}
	}

	@Override
	public void checkPermission(Permission permission, Object context) {
	}

	private static final String _PERMISSION_EXIT_VM = "exitVM";

}