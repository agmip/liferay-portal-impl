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

package com.liferay.portlet.passwordpoliciesadmin.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void getPasswordPolicy(HttpServletRequest request)
		throws Exception {

		long passwordPolicyId = ParamUtil.getLong(request, "passwordPolicyId");

		PasswordPolicy passwordPolicy = null;

		if (passwordPolicyId > 0) {
			passwordPolicy = PasswordPolicyLocalServiceUtil.getPasswordPolicy(
				passwordPolicyId);
		}

		request.setAttribute(WebKeys.PASSWORD_POLICY, passwordPolicy);
	}

	public static void getPasswordPolicy(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		getPasswordPolicy(request);
	}

}