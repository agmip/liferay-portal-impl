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
 * @author Amos Fong
 */
public class MBDiscussionDependencyManager extends DependencyManager {

	@Override
	public void update(
			long oldPrimaryKeyValue, Object[] oldColumnValues,
			Object[] oldExtraColumnValues, long newPrimaryKeyValue,
			Object[] newColumnValues, Object[] newExtraColumnValues)
		throws Exception {

		long threadId = 0;

		for (int i = 0; i < columns.length; i++) {
			if (columns[i][0].equals("threadId")) {
				threadId = (Long)newColumnValues[i];
			}
		}

		if ((threadId == 0) && (extraColumns != null)) {
			for (int i = 0; i < extraColumns.length; i++) {
				if (extraColumns[i][0].equals("threadId")) {
					threadId = (Long)newExtraColumnValues[i];
				}
			}
		}

		if (isDuplicateThread(threadId)) {
			deleteDuplicateData("MBMessage", "threadId", threadId);
			deleteDuplicateData("MBMessageFlag", "threadId", threadId);
			deleteDuplicateData("MBThread", "threadId", threadId);
		}
	}

	protected boolean isDuplicateThread(long threadId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select count(*) from MBDiscussion where threadId = ?");

			ps.setLong(1, threadId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long count = rs.getLong(1);

				if (count > 0) {
					return false;
				}
			}

			return true;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}