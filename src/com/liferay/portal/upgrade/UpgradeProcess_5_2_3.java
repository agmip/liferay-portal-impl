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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v5_2_3.UpgradeBookmarks;
import com.liferay.portal.upgrade.v5_2_3.UpgradeCalendar;
import com.liferay.portal.upgrade.v5_2_3.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v5_2_3.UpgradeDuplicates;
import com.liferay.portal.upgrade.v5_2_3.UpgradeGroup;
import com.liferay.portal.upgrade.v5_2_3.UpgradeImageGallery;
import com.liferay.portal.upgrade.v5_2_3.UpgradeLayout;
import com.liferay.portal.upgrade.v5_2_3.UpgradeMessageBoards;
import com.liferay.portal.upgrade.v5_2_3.UpgradeResource;
import com.liferay.portal.upgrade.v5_2_3.UpgradeResourceCode;
import com.liferay.portal.upgrade.v5_2_3.UpgradeRole;
import com.liferay.portal.upgrade.v5_2_3.UpgradeSchema;
import com.liferay.portal.upgrade.v5_2_3.UpgradeSoftwareCatalog;
import com.liferay.portal.upgrade.v5_2_3.UpgradeTags;
import com.liferay.portal.upgrade.v5_2_3.UpgradeUser;
import com.liferay.portal.upgrade.v5_2_3.UpgradeWiki;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeProcess_5_2_3 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_5_2_3_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgrade(UpgradeSchema.class);
		upgrade(UpgradeBookmarks.class);
		upgrade(UpgradeCalendar.class);
		upgrade(UpgradeDocumentLibrary.class);
		upgrade(UpgradeGroup.class);
		upgrade(UpgradeImageGallery.class);
		upgrade(UpgradeLayout.class);
		upgrade(UpgradeResource.class);
		upgrade(UpgradeResourceCode.class);
		upgrade(UpgradeRole.class);
		upgrade(UpgradeSoftwareCatalog.class);
		upgrade(UpgradeTags.class);
		upgrade(UpgradeUser.class);
		upgrade(UpgradeWiki.class);
		upgrade(UpgradeDuplicates.class);
		upgrade(UpgradeMessageBoards.class);
		upgrade(DropIndexes.class);
	}

}