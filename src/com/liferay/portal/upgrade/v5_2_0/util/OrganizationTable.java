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

package com.liferay.portal.upgrade.v5_2_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class OrganizationTable {

	public static final String TABLE_NAME = "Organization_";

	public static final Object[][] TABLE_COLUMNS = {
		{"organizationId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"parentOrganizationId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"type_", Types.VARCHAR},
		{"recursable", Types.BOOLEAN},
		{"regionId", Types.BIGINT},
		{"countryId", Types.BIGINT},
		{"statusId", Types.INTEGER},
		{"comments", Types.VARCHAR}
	};

	public static final String TABLE_SQL_CREATE = "create table Organization_ (organizationId LONG not null primary key,companyId LONG,parentOrganizationId LONG,name VARCHAR(100) null,type_ VARCHAR(75) null,recursable BOOLEAN,regionId LONG,countryId LONG,statusId INTEGER,comments STRING null)";

	public static final String TABLE_SQL_DROP = "drop table Organization_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_834BCEB6 on Organization_ (companyId)",
		"create index IX_E301BDF5 on Organization_ (companyId, name)",
		"create index IX_418E4522 on Organization_ (companyId, parentOrganizationId)"
	};

}