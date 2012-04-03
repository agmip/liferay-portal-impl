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

package com.liferay.portal.convert;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Alexander Chow
 */
public class ConvertPermissionTuner extends ConvertProcess {

	@Override
	public String getDescription() {
		return "fine-tune-generated-roles";
	}

	@Override
	public String getPath() {
		return "/admin_server/edit_permissions";
	}

	@Override
	public boolean isEnabled() {
		boolean enabled = false;

		try {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				int count = RoleLocalServiceUtil.getSubtypeRolesCount(
					"lfr-permission-algorithm-5");

				if (count > 0) {
					enabled = true;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return enabled;
	}

	@Override
	protected void doConvert() throws Exception {
	}

	private static Log _log = LogFactoryUtil.getLog(
		ConvertPermissionTuner.class);

}