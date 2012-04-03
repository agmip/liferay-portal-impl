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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Shepherd Ching
 * @author Jian Cao
 */
public class DB2Dialect extends org.hibernate.dialect.DB2Dialect {

	@Override
	public String getLimitString(String sql, boolean hasOffset) {
		if (!sql.startsWith("(")) {
			return super.getLimitString(sql, hasOffset);
		}

		StringBundler sb = new StringBundler(5);

		sb.append("select cursor1.* from (");
		sb.append("select rownumber() over() as rownumber_, cursor2.* from (");
		sb.append(sql);
		sb.append(") as cursor2) as cursor1 where rownumber_");

		if (hasOffset) {
			sb.append(" between ? + 1 and ?");
		}
		else {
			sb.append(" <= ?");
		}

		return sb.toString();
	}

}