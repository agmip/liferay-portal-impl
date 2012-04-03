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
public class BookmarksEntryTable {

	public static final String TABLE_NAME = "BookmarksEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"entryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"folderId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"url", Types.VARCHAR},
		{"comments", Types.VARCHAR},
		{"visits", Types.INTEGER},
		{"priority", Types.INTEGER}
	};

	public static final String TABLE_SQL_CREATE = "create table BookmarksEntry (uuid_ VARCHAR(75) null,entryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate DATE null,modifiedDate DATE null,folderId LONG,name VARCHAR(255) null,url STRING null,comments STRING null,visits INTEGER,priority INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table BookmarksEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_443BDC38 on BookmarksEntry (folderId)",
		"create index IX_E52FF7EF on BookmarksEntry (groupId)",
		"create index IX_E2E9F129 on BookmarksEntry (groupId, userId)",
		"create index IX_B670BA39 on BookmarksEntry (uuid_)",
		"create unique index IX_EAA02A91 on BookmarksEntry (uuid_, groupId)"
	};

}