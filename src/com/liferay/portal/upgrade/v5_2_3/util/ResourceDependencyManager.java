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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceDependencyManager extends DependencyManager {

	@Override
	public void update(
			long oldPrimaryKeyValue, Object[] oldColumnValues,
			Object[] oldExtraColumnValues, long newPrimaryKeyValue,
			Object[] newColumnValues, Object[] newExtraColumnValues)
		throws Exception {

		long resourceId = newPrimaryKeyValue;

		long permissionId = getPermissionId(resourceId);

		deleteDuplicateData("Permission_", resourceId);

		if (permissionId > 0) {
			DependencyManager permissionDependencyManager =
				new PermissionDependencyManager();

			permissionDependencyManager.setPrimaryKeyName("permissionId");

			permissionDependencyManager.update(permissionId);
		}
	}

	protected long getPermissionId(long resourceId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select permissionId from Permission_ where resourceId = ?");

			ps.setLong(1, resourceId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long permissionId = rs.getLong("permissionId");

				return permissionId;
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

}