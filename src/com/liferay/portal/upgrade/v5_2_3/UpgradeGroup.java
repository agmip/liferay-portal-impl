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

package com.liferay.portal.upgrade.v5_2_3;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.Group;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		long classNameId = PortalUtil.getClassNameId(Group.class.getName());

		runSQL(
			"update Group_ set classNameId = " + classNameId +
				", classPK = groupId where classNameId = 0");
	}

}