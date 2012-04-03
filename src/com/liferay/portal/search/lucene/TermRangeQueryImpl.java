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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.search.BaseQueryImpl;
import com.liferay.portal.kernel.search.TermRangeQuery;

/**
 * @author Raymond Augé
 */
public class TermRangeQueryImpl extends BaseQueryImpl
	implements TermRangeQuery {

	public TermRangeQueryImpl(
		String field, String lowerTerm, String upperTerm, boolean includesLower,
		boolean includesUpper) {

		_termRangeQuery = new org.apache.lucene.search.TermRangeQuery(
			field, lowerTerm, upperTerm, includesLower, includesUpper);
	}

	public String getField() {
		return _termRangeQuery.getField();
	}

	public String getLowerTerm() {
		return _termRangeQuery.getLowerTerm();
	}

	public Object getTermRangeQuery() {
		return _termRangeQuery;
	}

	public String getUpperTerm() {
		return _termRangeQuery.getUpperTerm();
	}

	@Override
	public Object getWrappedQuery() {
		return getTermRangeQuery();
	}

	public boolean includesLower() {
		return _termRangeQuery.includesLower();
	}

	public boolean includesUpper() {
		return _termRangeQuery.includesUpper();
	}

	@Override
	public String toString() {
		return _termRangeQuery.toString();
	}

	private org.apache.lucene.search.TermRangeQuery _termRangeQuery;

}