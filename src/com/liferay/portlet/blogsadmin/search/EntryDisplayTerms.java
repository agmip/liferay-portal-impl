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

package com.liferay.portlet.blogsadmin.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Juan Fernández
 */
public class EntryDisplayTerms extends DisplayTerms {

	public static final String AUTHOR = "author";

	public static final String STATUS = "status";

	public static final String TITLE = "title";

	public EntryDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		author = ParamUtil.getString(portletRequest, AUTHOR);
		status = ParamUtil.getString(portletRequest, STATUS);
		title = ParamUtil.getString(portletRequest, TITLE);
	}

	public String getAuthor() {
		return author;
	}

	public String getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}

	protected String author;
	protected String status;
	protected String title;

}