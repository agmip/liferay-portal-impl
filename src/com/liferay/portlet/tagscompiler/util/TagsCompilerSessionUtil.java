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

package com.liferay.portlet.tagscompiler.util;

import com.liferay.portal.util.WebKeys;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

/**
 * @author Brian Wing Shun Chan
 */
public class TagsCompilerSessionUtil {

	public static void addEntries(
		PortletRequest portletRequest, List<String> entries) {

		Set<String> entriesSet = _getEntriesSet(portletRequest);

		entriesSet.addAll(entries);
	}

	public static void addEntry(PortletRequest portletRequest, String entry) {
		Set<String> entriesSet = _getEntriesSet(portletRequest);

		entriesSet.add(entry);
	}

	public static void clearEntries(PortletRequest portletRequest) {
		Set<String> entriesSet = _getEntriesSet(portletRequest);

		entriesSet.clear();
	}

	public static Collection<String> getEntries(PortletRequest portletRequest) {
		Set<String> entriesSet = _getEntriesSet(portletRequest);

		return entriesSet;
	}

	public static void removeEntries(
		PortletRequest portletRequest, List<String> entries) {

		Set<String> entriesSet = _getEntriesSet(portletRequest);

		entriesSet.removeAll(entries);
	}

	public static void setEntries(
		PortletRequest portletRequest, List<String> entries) {

		Set<String> entriesSet = _getEntriesSet(portletRequest);

		entriesSet.clear();

		entriesSet.addAll(entries);
	}

	private static Set<String> _getEntriesSet(PortletRequest portletRequest) {
		PortletSession portletSession = portletRequest.getPortletSession();

		Set<String> entriesSet = (Set<String>)portletSession.getAttribute(
			WebKeys.TAGS_COMPILER_ENTRIES, PortletSession.APPLICATION_SCOPE);

		if (entriesSet == null) {
			entriesSet = new TreeSet<String>();

			portletSession.setAttribute(
				WebKeys.TAGS_COMPILER_ENTRIES, entriesSet,
				PortletSession.APPLICATION_SCOPE);
		}

		return entriesSet;
	}

}