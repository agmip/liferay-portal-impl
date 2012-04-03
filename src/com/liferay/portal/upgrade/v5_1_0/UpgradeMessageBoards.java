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

package com.liferay.portal.upgrade.v5_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeMessageBoards extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		// LEP-5761

		while (getMessageIdsCount() > 0) {
			updateMessage();
		}
	}

	protected long getMessageIdsCount() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(7);

			sb.append("select count(*) from ");
			sb.append("MBMessage childMessage ");
			sb.append("inner join MBMessage parentMessage on ");
			sb.append("childMessage.parentMessageId = ");
			sb.append("parentMessage.messageId where ");
			sb.append("parentMessage.categoryId != childMessage.categoryId ");
			sb.append("or parentMessage.threadId != childMessage.threadId");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getLong(1);
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateMessage() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(8);

			sb.append("select childMessage.messageId, ");
			sb.append("parentMessage.categoryId, parentMessage.threadId ");
			sb.append("from MBMessage childMessage ");
			sb.append("inner join MBMessage parentMessage on ");
			sb.append("childMessage.parentMessageId = ");
			sb.append("parentMessage.messageId where ");
			sb.append("parentMessage.categoryId != childMessage.categoryId ");
			sb.append("or parentMessage.threadId != childMessage.threadId");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long messageId = rs.getLong(1);
				long categoryId = rs.getLong(2);
				long threadId = rs.getLong(3);

				runSQL(
					"update MBMessage set categoryId = " + categoryId +
						", threadId = " + threadId + " where messageId = " +
							messageId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}