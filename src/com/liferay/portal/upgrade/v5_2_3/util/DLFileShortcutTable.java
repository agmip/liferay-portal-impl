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
public class DLFileShortcutTable {

	public static final String TABLE_NAME = "DLFileShortcut";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"fileShortcutId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"folderId", Types.BIGINT},
		{"toFolderId", Types.BIGINT},
		{"toName", Types.VARCHAR}
	};

	public static final String TABLE_SQL_CREATE = "create table DLFileShortcut (uuid_ VARCHAR(75) null,fileShortcutId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,folderId LONG,toFolderId LONG,toName VARCHAR(255) null)";

	public static final String TABLE_SQL_DROP = "drop table DLFileShortcut";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_E56EC6AD on DLFileShortcut (folderId)",
		"create index IX_CA2708A2 on DLFileShortcut (toFolderId, toName)",
		"create index IX_4831EBE4 on DLFileShortcut (uuid_)",
		"create unique index IX_FDB4A946 on DLFileShortcut (uuid_, groupId)"
	};

}