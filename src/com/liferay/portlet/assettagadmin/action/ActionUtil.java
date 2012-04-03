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

package com.liferay.portlet.assettagadmin.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagServiceUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class ActionUtil {

	public static void getTag(HttpServletRequest request) throws Exception {
		long tagId = ParamUtil.getLong(request, "tagId");

		AssetTag tag = null;

		if (tagId > 0) {
			tag = AssetTagServiceUtil.getTag(tagId);
		}

		request.setAttribute(WebKeys.ASSET_TAG, tag);
	}

	public static void getTag(PortletRequest portletRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getTag(request);
	}

}