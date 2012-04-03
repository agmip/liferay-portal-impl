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

package com.liferay.portal.upgrade.v5_1_2;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.v5_1_2.util.CalEventRecurrenceUpgradeColumnImpl;
import com.liferay.portal.upgrade.v5_1_2.util.CalEventTable;

/**
 * @author     Samuel Kong
 * @deprecated
 */
public class UpgradeCalendar extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		UpgradeColumn recurrenceColumn =
			new CalEventRecurrenceUpgradeColumnImpl("recurrence");

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			CalEventTable.TABLE_NAME, CalEventTable.TABLE_COLUMNS,
			recurrenceColumn);

		upgradeTable.updateTable();
	}

}