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

package com.liferay.portal.upgrade.v5_0_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alexander Chow
 */
public class IGImageNameColumnImpl extends BaseUpgradeColumnImpl {

	public IGImageNameColumnImpl(UpgradeColumn imageIdColumn) {
		super("name");

		_imageIdColumn = imageIdColumn;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String oldString = (String)oldValue;

		if (Validator.isNull(oldString)) {
			return _imageIdColumn.getOldValue();
		}
		else {
			return oldString;
		}
	}

	private UpgradeColumn _imageIdColumn;

}