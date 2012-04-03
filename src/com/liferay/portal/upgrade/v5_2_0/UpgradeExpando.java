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

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.util.PortalInstances;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeExpando extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		if (companyIds.length == 0) {
			return;
		}

		long companyId = companyIds[0];

		runSQL("update ExpandoColumn set companyId = " + companyId);

		runSQL("update ExpandoRow set companyId = " + companyId);

		runSQL("update ExpandoTable set companyId = " + companyId);

		runSQL("update ExpandoValue set companyId = " + companyId);
	}

}