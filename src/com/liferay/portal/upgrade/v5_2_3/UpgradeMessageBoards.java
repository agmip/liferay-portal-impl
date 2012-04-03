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
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeMessageBoards extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateGroupId();
		updateMessageClassNameId();
		updateMessageFlagThreadId();
		updateMessagePriority();
	}

	protected void updateGroupId() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("update MBCategory set threadCount = (select count(*) from ");
		sb.append("MBThread where MBThread.categoryId = ");
		sb.append("MBCategory.categoryId)");

		runSQL(sb.toString());

		sb.setIndex(0);

		sb.append("update MBCategory set messageCount = (select count(*) ");
		sb.append("from MBMessage where MBMessage.categoryId = ");
		sb.append("MBCategory.categoryId)");

		runSQL(sb.toString());

		sb.setIndex(0);

		sb.append("update MBMessage set groupId = (select groupId from ");
		sb.append("MBCategory where MBCategory.categoryId = ");
		sb.append("MBMessage.categoryId)");

		runSQL(sb.toString());

		sb.setIndex(0);

		sb.append("update MBThread set groupId = (select groupId from ");
		sb.append("MBCategory where MBCategory.categoryId = ");
		sb.append("MBThread.categoryId)");

		runSQL(sb.toString());
	}

	protected void updateMessageClassNameId() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("update MBMessage set classNameId = (select classNameId ");
		sb.append("from MBDiscussion where MBDiscussion.threadId = ");
		sb.append("MBMessage.threadId), classPK = (select classPK from ");
		sb.append("MBDiscussion where MBDiscussion.threadId = ");
		sb.append("MBMessage.threadId)");

		runSQL(sb.toString());
	}

	protected void updateMessageFlagThreadId() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("update MBMessageFlag set threadId = (select threadId from ");
		sb.append("MBMessage where MBMessage.messageId = ");
		sb.append("MBMessageFlag.messageId)");

		runSQL(sb.toString());
	}

	protected void updateMessagePriority() throws Exception {
		StringBundler sb = new StringBundler(2);

		sb.append("update MBMessage set priority = (select priority from ");
		sb.append("MBThread where MBThread.threadId = MBMessage.threadId)");

		runSQL(sb.toString());
	}

}