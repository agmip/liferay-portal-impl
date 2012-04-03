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

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.upgrade.v5_2_0.util.OrganizationTable;
import com.liferay.portal.upgrade.v5_2_0.util.OrganizationTypeUpgradeColumnImpl;
import com.liferay.portal.util.PortalInstances;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

/**
 * @author Jorge Ferrer
 * @author Edward Shin
 */
public class UpgradeOrganization extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		UpgradeColumn locationColumn = new TempUpgradeColumnImpl(
			"location", new Integer(Types.BOOLEAN));

		UpgradeColumn typeColumn = new OrganizationTypeUpgradeColumnImpl(
			locationColumn);

		Object[][] organizationColumns1 =
			{{"location", new Integer(Types.BOOLEAN)}};
		Object[][] organizationColumns2 =
			OrganizationTable.TABLE_COLUMNS.clone();

		Object[][] organizationColumns = ArrayUtil.append(
			organizationColumns1, organizationColumns2);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			OrganizationTable.TABLE_NAME, organizationColumns, locationColumn,
			typeColumn);

		upgradeTable.updateTable();

		updateLocationResources();
	}

	protected long getCodeId(long companyId, String name, int scope)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select codeId from ResourceCode where companyId = ? and " +
					"name = ? and scope = ?");

			ps.setLong(1, companyId);
			ps.setString(2, name);
			ps.setInt(3, scope);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("codeId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateCodeId(long companyId, int scope) throws Exception {
		long oldCodeId = getCodeId(
			companyId, "com.liferay.portal.model.Location", scope);
		long newCodeId = getCodeId(
			companyId, "com.liferay.portal.model.Organization", scope);

		runSQL(
			"update Resource_ set codeId = " + newCodeId + " where codeId = " +
				oldCodeId);

		runSQL("delete from ResourceCode where codeId = " + oldCodeId);
	}

	protected void updateLocationResources() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			for (int scope : ResourceConstants.SCOPES) {
				updateCodeId(companyId, scope);
			}
		}
	}

}