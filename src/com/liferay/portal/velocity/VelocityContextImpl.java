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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.velocity.VelocityContext;

/**
 * @author Raymond Augé
 */
public class VelocityContextImpl implements VelocityContext {

	public VelocityContextImpl() {
		_velocityContext = new org.apache.velocity.VelocityContext();
	}

	public VelocityContextImpl(
		org.apache.velocity.VelocityContext velocityContext) {

		_velocityContext = new org.apache.velocity.VelocityContext(
			velocityContext);
	}

	public Object get(String key) {
		return _velocityContext.get(key);
	}

	public org.apache.velocity.VelocityContext getWrappedVelocityContext() {
		return _velocityContext;
	}

	public void put(String key, Object value) {
		_velocityContext.put(key, value);
	}

	private org.apache.velocity.VelocityContext _velocityContext;

}