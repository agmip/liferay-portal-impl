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
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class DependencyManager {

	public void setColumns(Object[][] columns) {
		this.columns = columns;
	}

	public void setExtraColumns(Object[][] extraColumns) {
		this.extraColumns = extraColumns;
	}

	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void update(long newPrimaryKeyValue) throws Exception {
		update(0, null, null, newPrimaryKeyValue, null, null);
	}

	public abstract void update(
			long oldPrimaryKeyValue, Object[] oldColumnValues,
			Object[] oldExtraColumnValues, long newPrimaryKeyValue,
			Object[] newColumnValues, Object[] newExtraColumnValues)
		throws Exception;

	protected void deleteDuplicateData(String tableName, long primaryKeyValue)
		throws Exception {

		deleteDuplicateData(tableName, primaryKeyName, primaryKeyValue);
	}

	protected void deleteDuplicateData(
			String tableName, String columnName, long columnValue)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("delete from ");
			sb.append(tableName);
			sb.append(" where ");
			sb.append(columnName);
			sb.append(" = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, columnValue);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateDuplicateData(
			String tableName, long oldPrimaryKeyValue, long newPrimaryKeyValue)
		throws Exception {

		updateDuplicateData(
			tableName, primaryKeyName, oldPrimaryKeyValue, newPrimaryKeyValue);
	}

	protected void updateDuplicateData(
			String tableName, String columnName, long oldColumnValue,
			long newColumnValue)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(7);

			sb.append("update ");
			sb.append(tableName);
			sb.append(" set ");
			sb.append(columnName);
			sb.append(" = ? where ");
			sb.append(columnName);
			sb.append(" = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, newColumnValue);
			ps.setLong(2, oldColumnValue);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected Object[][] columns;
	protected Object[][] extraColumns;
	protected String primaryKeyName;
	protected String tableName;

}