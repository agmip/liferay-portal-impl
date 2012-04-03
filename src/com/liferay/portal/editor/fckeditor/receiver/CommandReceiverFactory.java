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

import com.liferay.portal.kernel.util.InstancePool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 * @author Michael C. Han
 */
public class CommandReceiverFactory {

	public static CommandReceiver getCommandReceiver(String type) {
		CommandReceiver commandReceiver = _commandReceivers.get(type);

		if (commandReceiver == null) {
			commandReceiver = (CommandReceiver)InstancePool.get(
				"com.liferay.portal.editor.fckeditor.receiver.impl." +
					type + "CommandReceiver");

			_commandReceivers.put(type, commandReceiver);
		}

		return commandReceiver;
	}

	public void setCommandReceiver(
		String type, CommandReceiver commandReceiver) {

		_commandReceivers.put(type, commandReceiver);
	}

	public void setCommandReceivers(
		Map<String, CommandReceiver> commandReceivers) {

		_commandReceivers.putAll(commandReceivers);
	}

	private static Map<String, CommandReceiver> _commandReceivers =
		new HashMap<String, CommandReceiver>();

}