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

package com.liferay.portal.editor.fckeditor.receiver;

import com.liferay.portal.editor.fckeditor.command.CommandArgument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivica Cardic
 */
public interface CommandReceiver {

	public void createFolder(
		CommandArgument commandArgument, HttpServletRequest request,
		HttpServletResponse response);

	public void fileUpload(
		CommandArgument commandArgument, HttpServletRequest request,
		HttpServletResponse response);

	public void getFolders(
		CommandArgument commandArgument, HttpServletRequest request,
		HttpServletResponse response);

	public void getFoldersAndFiles(
		CommandArgument commandArgument, HttpServletRequest request,
		HttpServletResponse response);

}