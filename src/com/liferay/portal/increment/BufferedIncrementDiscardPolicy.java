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

package com.liferay.portal.increment;

import com.liferay.portal.kernel.concurrent.BatchablePipe;
import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageRunnable;

/**
 * @author Shuyang Zhou
 */
public class BufferedIncrementDiscardPolicy
	implements RejectedExecutionHandler {

	@SuppressWarnings("rawtypes")
	public void rejectedExecution(
		Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {

		MessageRunnable messageRunnable = (MessageRunnable)runnable;

		Message message = messageRunnable.getMessage();

		BatchablePipe<String, BufferedIncreasableEntry> batchablePipe =
			(BatchablePipe<String, BufferedIncreasableEntry>)
				message.getPayload();

		for (int i = 0; i < _discardNumber; i++) {
			BufferedIncreasableEntry bufferedIncreasableEntry =
				(BufferedIncreasableEntry)batchablePipe.take();

			if (bufferedIncreasableEntry == null) {
				break;
			}
			else if (_log.isInfoEnabled()) {
				_log.info(
					"Discarding BufferedIncreasableEntry " +
						bufferedIncreasableEntry);
			}
		}
	}

	public void setDiscardNumber(int discardNumber) {
		_discardNumber = discardNumber;
	}

	private static Log _log = LogFactoryUtil.getLog(
		BufferedIncrementDiscardPolicy.class);

	private int _discardNumber = 1;

}