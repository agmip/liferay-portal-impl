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

package com.liferay.portal.upgrade.v5_1_2.util;

import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.TimeZone;

/**
 * @author     Samuel Kong
 * @deprecated
 */
public class CalEventRecurrenceUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public CalEventRecurrenceUpgradeColumnImpl(String name) {
		super(name);
	}

	public Object getNewValue(Object oldValue) throws Exception {
		if (Validator.isNull(oldValue)) {
			return StringPool.BLANK;
		}

		String recurrence = (String)oldValue;

		Object obj = Base64.stringToObject(recurrence);

		if (obj instanceof Recurrence) {
			Recurrence recurrenceObj = (Recurrence)obj;

			return serialize(recurrenceObj);
		}
		else if (obj instanceof com.liferay.util.cal.Recurrence) {
			com.liferay.util.cal.Recurrence oldRecurrence =
				(com.liferay.util.cal.Recurrence)obj;

			com.liferay.util.cal.Duration oldDuration =
				oldRecurrence.getDuration();

			Duration duration = new Duration(
				oldDuration.getDays(), oldDuration.getHours(),
				oldDuration.getMinutes(), oldDuration.getSeconds());

			duration.setWeeks(oldDuration.getWeeks());
			duration.setInterval(oldDuration.getInterval());

			Recurrence recurrenceObj = new Recurrence(
				oldRecurrence.getDtStart(), duration,
				oldRecurrence.getFrequency());

			com.liferay.util.cal.DayAndPosition[] oldDayPos =
				oldRecurrence.getByDay();

			DayAndPosition[] dayPos = null;

			if (oldDayPos != null) {
				dayPos = new DayAndPosition[oldDayPos.length];

				for (int i = 0; i < oldDayPos.length; i++) {
					dayPos[i] = new DayAndPosition(
						oldDayPos[i].getDayOfWeek(),
						oldDayPos[i].getDayPosition());
				}
			}

			recurrenceObj.setByDay(dayPos);
			recurrenceObj.setByMonth(oldRecurrence.getByMonth());
			recurrenceObj.setByMonthDay(oldRecurrence.getByMonthDay());
			recurrenceObj.setInterval(oldRecurrence.getInterval());
			recurrenceObj.setOccurrence(oldRecurrence.getOccurrence());
			recurrenceObj.setWeekStart(oldRecurrence.getWeekStart());
			recurrenceObj.setUntil(oldRecurrence.getUntil());

			return serialize(recurrenceObj);
		}
		else {
			return StringPool.BLANK;
		}
	}

	protected String serialize(Recurrence recurrence) throws JSONException {
		JSONObject recurrenceJSON = JSONFactoryUtil.createJSONObject(
			JSONFactoryUtil.serialize(recurrence));

		recurrenceJSON.put("javaClass", TZSRecurrence.class.getName());

		TimeZone timeZone = TimeZoneUtil.getTimeZone(StringPool.UTC);

		JSONObject timeZoneJSON = JSONFactoryUtil.createJSONObject(
			JSONFactoryUtil.serialize(timeZone));

		recurrenceJSON.put("timeZone", timeZoneJSON);

		return recurrenceJSON.toString();
	}

}