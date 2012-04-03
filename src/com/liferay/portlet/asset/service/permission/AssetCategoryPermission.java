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

package com.liferay.portlet.asset.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;

/**
 * @author Eduardo Lundgren
 */
public class AssetCategoryPermission {

	public static void check(
			PermissionChecker permissionChecker, AssetCategory category,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, category, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, categoryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, categoryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, AssetCategory category,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				category.getCompanyId(), AssetCategory.class.getName(),
				category.getCategoryId(), category.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			category.getGroupId(), AssetCategory.class.getName(),
			category.getCategoryId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		if (categoryId == AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			return AssetPermission.contains(
				permissionChecker, groupId, actionId);
		}
		else {
			return contains(permissionChecker, categoryId, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException, SystemException {

		AssetCategory category = AssetCategoryLocalServiceUtil.getCategory(
			categoryId);

		return contains(permissionChecker, category, actionId);
	}

}