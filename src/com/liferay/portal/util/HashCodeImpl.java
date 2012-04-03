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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.HashCode;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Brian Wing Shun Chan
 */
public class HashCodeImpl implements HashCode {

	public HashCodeImpl() {
		_hashCodeBuilder = new HashCodeBuilder();
	}

	public HashCodeImpl(
		int initialNonZeroOddNumber, int multiplierNonZeroOddNumber) {

		_hashCodeBuilder = new HashCodeBuilder(
			initialNonZeroOddNumber, multiplierNonZeroOddNumber);
	}

	public HashCode append(boolean value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(boolean[] value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(byte value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(byte[] value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(char value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(char[] value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(double value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(double[] value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(float value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(float[] value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(int value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(int[] value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(long value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(long[] value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(Object value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(Object[] value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(short value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode append(short[] value) {
		_hashCodeBuilder.append(value);

		return this;
	}

	public HashCode appendSuper(int superHashCode) {
		_hashCodeBuilder.appendSuper(superHashCode);

		return this;
	}

	public int toHashCode() {
		return _hashCodeBuilder.toHashCode();
	}

	private HashCodeBuilder _hashCodeBuilder;

}