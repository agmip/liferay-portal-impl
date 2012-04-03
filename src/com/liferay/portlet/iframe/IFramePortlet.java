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

package com.liferay.portlet.iframe;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.StrutsPortlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

/**
 * @author Brian Wing Shun Chan
 */
public class IFramePortlet extends StrutsPortlet {

	public static final String DEFAULT_EDIT_ACTION = "/iframe/edit";

	public static final String DEFAULT_VIEW_ACTION = "/iframe/view";

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		if (Validator.isNull(editAction)) {
			editAction = DEFAULT_EDIT_ACTION;
		}

		if (Validator.isNull(viewAction)) {
			viewAction = DEFAULT_VIEW_ACTION;
		}
	}

}