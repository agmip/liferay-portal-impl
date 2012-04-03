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

package com.liferay.portal.upgrade.v5_1_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class BlogsEntryTable {

	public static final String TABLE_NAME = "BlogsEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"entryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"title", Types.VARCHAR},
		{"urlTitle", Types.VARCHAR},
		{"content", Types.CLOB},
		{"displayDate", Types.TIMESTAMP},
		{"draft", Types.BOOLEAN},
		{"allowTrackbacks", Types.BOOLEAN},
		{"trackbacks", Types.CLOB}
	};

	public static final String TABLE_SQL_CREATE = "create table BlogsEntry (uuid_ VARCHAR(75) null,entryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title VARCHAR(150) null,urlTitle VARCHAR(150) null,content TEXT null,displayDate DATE null,draft BOOLEAN,allowTrackbacks BOOLEAN,trackbacks TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table BlogsEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_72EF6041 on BlogsEntry (companyId)",
		"create index IX_3D3D30B0 on BlogsEntry (companyId, draft)",
		"create index IX_81A50303 on BlogsEntry (groupId)",
		"create index IX_5FF14FAE on BlogsEntry (groupId, draft)",
		"create index IX_DB780A20 on BlogsEntry (groupId, urlTitle)",
		"create index IX_C07CA83D on BlogsEntry (groupId, userId)",
		"create index IX_BC2A3534 on BlogsEntry (groupId, userId, draft)",
		"create index IX_69157A4D on BlogsEntry (uuid_)",
		"create index IX_1B1040FD on BlogsEntry (uuid_, groupId)"
	};

}