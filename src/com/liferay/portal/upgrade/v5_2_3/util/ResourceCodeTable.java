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

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ResourceCodeTable {

	public static final String TABLE_NAME = "ResourceCode";

	public static final Object[][] TABLE_COLUMNS = {
		{"codeId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"scope", Types.INTEGER}
	};

	public static final String TABLE_SQL_CREATE = "create table ResourceCode (codeId LONG not null primary key,companyId LONG,name VARCHAR(255) null,scope INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table ResourceCode";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_717FDD47 on ResourceCode (companyId)",
		"create unique index IX_A32C097E on ResourceCode (companyId, name, scope)",
		"create index IX_AACAFF40 on ResourceCode (name)"
	};

}