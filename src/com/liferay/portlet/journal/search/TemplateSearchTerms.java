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

package com.liferay.portlet.journal.search;

import com.liferay.portal.kernel.dao.search.DAOParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class TemplateSearchTerms extends TemplateDisplayTerms {

	public TemplateSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		description = DAOParamUtil.getString(portletRequest, DESCRIPTION);
		groupIds = setGroupIds(portletRequest);
		name = DAOParamUtil.getString(portletRequest, NAME);
		structureId = DAOParamUtil.getString(portletRequest, STRUCTURE_ID);
		templateId = DAOParamUtil.getString(portletRequest, TEMPLATE_ID);
	}

	public String getStructureIdComparator() {
		return structureIdComparator;
	}

	public void setGroupIds(long[] groupIds) {
		this.groupIds = groupIds;
	}

	public void setStructureId(String structureId) {
		this.structureId = structureId;
	}

	public void setStructureIdComparator(String structureIdComparator) {
		this.structureIdComparator = structureIdComparator;
	}

	protected String structureIdComparator;

}