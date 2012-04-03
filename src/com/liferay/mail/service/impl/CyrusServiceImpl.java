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

package com.liferay.mail.service.impl;

import com.liferay.mail.NoSuchCyrusUserException;
import com.liferay.mail.model.CyrusUser;
import com.liferay.mail.model.CyrusVirtual;
import com.liferay.mail.service.CyrusService;
import com.liferay.mail.service.persistence.CyrusUserUtil;
import com.liferay.mail.service.persistence.CyrusVirtualUtil;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Alexander Chow
 */
public class CyrusServiceImpl implements CyrusService, IdentifiableBean {

	public void addUser(long userId, String emailAddress, String password)
		throws SystemException {

		// User

		CyrusUser user = new CyrusUser(userId, password);

		CyrusUserUtil.update(user);

		// Virtual

		CyrusVirtual virtual = new CyrusVirtual(emailAddress, userId);

		CyrusVirtualUtil.update(virtual);
	}

	public void deleteEmailAddress(long companyId, long userId)
		throws SystemException {

		CyrusVirtualUtil.removeByUserId(userId);
	}

	public void deleteUser(long userId) throws SystemException {

		// User

		try {
			CyrusUserUtil.remove(userId);
		}
		catch (NoSuchCyrusUserException nscue) {
		}

		// Virtual

		CyrusVirtualUtil.removeByUserId(userId);
	}

	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	public void updateEmailAddress(
			long companyId, long userId, String emailAddress)
		throws SystemException {

		CyrusVirtualUtil.removeByUserId(userId);

		CyrusVirtual virtual = new CyrusVirtual(emailAddress, userId);

		CyrusVirtualUtil.update(virtual);
	}

	public void updatePassword(long companyId, long userId, String password)
		throws SystemException {

		CyrusUser user = null;

		try {
			user = CyrusUserUtil.findByPrimaryKey(userId);
		}
		catch (NoSuchCyrusUserException nscue) {
			user = new CyrusUser(userId, password);
		}

		user.setPassword(password);

		CyrusUserUtil.update(user);
	}

	private String _beanIdentifier;

}