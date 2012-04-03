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
public class DLFileVersionTable {

	public static final String TABLE_NAME = "DLFileVersion";

	public static final Object[][] TABLE_COLUMNS = {
		{"fileVersionId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"folderId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"version", Types.DOUBLE},
		{"size_", Types.INTEGER}
	};

	public static final String TABLE_SQL_CREATE = "create table DLFileVersion (fileVersionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,folderId LONG,name VARCHAR(255) null,version DOUBLE,size_ INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table DLFileVersion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_9CD91DB6 on DLFileVersion (folderId, name)",
		"create unique index IX_6C5E6512 on DLFileVersion (folderId, name, version)"
	};

}