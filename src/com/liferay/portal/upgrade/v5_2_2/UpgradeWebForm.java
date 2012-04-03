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

package com.liferay.portal.upgrade.v5_2_2;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Julio Camarero
 */
public class UpgradeWebForm extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		long oldClassNameId = getClassNameId(_OLD_WEBFORM_CLASS_NAME);

		if (oldClassNameId == 0) {
			return;
		}

		long newClassNameId = getClassNameId(_NEW_WEBFORM_CLASS_NAME);

		if (newClassNameId == 0) {
			runSQL(
				"update ClassName_ set value = '" +
					_NEW_WEBFORM_CLASS_NAME + "' where value = '" +
						_OLD_WEBFORM_CLASS_NAME + "'");
		}
		else {
			runSQL(
				"update ExpandoTable set classNameId = '" + newClassNameId +
					"' where classNameId = '" + oldClassNameId + "'");

			runSQL(
				"update ExpandoValue set classNameId = '" + newClassNameId +
					"' where classNameId = '" + oldClassNameId + "'");

			runSQL(
				"delete from ClassName_ where value = '" +
					_OLD_WEBFORM_CLASS_NAME + "'");
		}
	}

	protected long getClassNameId(String className) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select classNameId from ClassName_ where value = ?");

			ps.setString(1, className);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("classNameId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _NEW_WEBFORM_CLASS_NAME =
		"com.liferay.webform.util.WebFormUtil";

	private static final String _OLD_WEBFORM_CLASS_NAME =
		"com.liferay.portlet.webform.util.WebFormUtil";

}