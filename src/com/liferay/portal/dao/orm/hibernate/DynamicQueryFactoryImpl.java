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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicQueryFactoryImpl implements DynamicQueryFactory {

	public DynamicQuery forClass(Class<?> clazz) {
		clazz = getImplClass(clazz);

		return new DynamicQueryImpl(DetachedCriteria.forClass(clazz));
	}

	public DynamicQuery forClass(Class<?> clazz, ClassLoader classLoader) {
		clazz = getImplClass(clazz, classLoader);

		return new DynamicQueryImpl(DetachedCriteria.forClass(clazz));
	}

	public DynamicQuery forClass(Class<?> clazz, String alias) {
		clazz = getImplClass(clazz);

		return new DynamicQueryImpl(DetachedCriteria.forClass(clazz, alias));
	}

	public DynamicQuery forClass(
		Class<?> clazz, String alias, ClassLoader classLoader) {

		clazz = getImplClass(clazz, classLoader);

		return new DynamicQueryImpl(DetachedCriteria.forClass(clazz, alias));
	}

	protected Class<?> getImplClass(Class<?> clazz) {
		return getImplClass(clazz, null);
	}

	protected Class<?> getImplClass(Class<?> clazz, ClassLoader classLoader) {
		if (!clazz.getName().endsWith("Impl")) {
			String implClassName =
				clazz.getPackage().getName() + ".impl." +
					clazz.getSimpleName() + "Impl";

			clazz = _classMap.get(implClassName);

			if (clazz == null) {
				try {
					if (classLoader == null) {
						Thread currentThread = Thread.currentThread();

						classLoader = currentThread.getContextClassLoader();
					}

					clazz = classLoader.loadClass(implClassName);

					_classMap.put(implClassName, clazz);
				}
				catch (Exception e) {
					_log.error("Unable find model " + implClassName, e);
				}
			}
		}

		return clazz;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DynamicQueryFactoryImpl.class);

	private Map<String, Class<?>> _classMap = new HashMap<String, Class<?>>();

}