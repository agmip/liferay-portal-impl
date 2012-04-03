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
public class TagsPropertyTable {

	public static final String TABLE_NAME = "TagsProperty";

	public static final Object[][] TABLE_COLUMNS = {
		{"propertyId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"entryId", Types.BIGINT},
		{"key_", Types.VARCHAR},
		{"value", Types.VARCHAR}
	};

	public static final String TABLE_SQL_CREATE = "create table TagsProperty (propertyId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,entryId LONG,key_ VARCHAR(75) null,value VARCHAR(255) null)";

	public static final String TABLE_SQL_DROP = "drop table TagsProperty";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_C134234 on TagsProperty (companyId)",
		"create index IX_EB974D08 on TagsProperty (companyId, key_)",
		"create index IX_5200A629 on TagsProperty (entryId)",
		"create unique index IX_F505253D on TagsProperty (entryId, key_)"
	};

}