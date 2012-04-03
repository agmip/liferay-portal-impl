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

package com.liferay.portal.upgrade.v5_2_3.util;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionDependencyManager extends DependencyManager {

	@Override
	public void update(
			long oldPrimaryKeyValue, Object[] oldColumnValues,
			Object[] oldExtraColumnValues, long newPrimaryKeyValue,
			Object[] newColumnValues, Object[] newExtraColumnValues)
		throws Exception {

		deleteDuplicateData("Groups_Permissions", newPrimaryKeyValue);
		deleteDuplicateData("OrgGroupPermission", newPrimaryKeyValue);
		deleteDuplicateData("Roles_Permissions", newPrimaryKeyValue);
		deleteDuplicateData("Users_Permissions", newPrimaryKeyValue);
	}

}