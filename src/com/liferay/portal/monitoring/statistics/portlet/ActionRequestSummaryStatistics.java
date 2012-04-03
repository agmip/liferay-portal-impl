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

package com.liferay.portal.monitoring.statistics.portlet;

import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.monitoring.statistics.RequestStatistics;

import java.util.Set;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class ActionRequestSummaryStatistics
	implements PortletSummaryStatistics {

	public long getAverageTime() {
		long averageTime = 0;

		long count = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			for (RequestStatistics requestStatistics :
					companyStatistics.getActionRequestStatisticsSet()) {

				averageTime += requestStatistics.getAverageTime();

				count++;
			}
		}

		return averageTime / count;
	}

	public long getAverageTimeByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getAverageTimeByCompany(companyStatistics);
	}

	public long getAverageTimeByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getAverageTimeByCompany(companyStatistics);
	}

	public long getAverageTimeByPortlet(String portletId)
		throws MonitoringException {

		long averageTime = 0;

		Set<CompanyStatistics> companyStatisticsSet =
			_serverStatistics.getCompanyStatisticsSet();

		for (CompanyStatistics companyStatistics : companyStatisticsSet) {
			RequestStatistics requestStatistics =
				companyStatistics.getActionRequestStatistics(portletId);

			averageTime += requestStatistics.getAverageTime();
		}

		return averageTime / companyStatisticsSet.size();
	}

	public long getAverageTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		RequestStatistics requestStatistics =
			companyStatistics.getActionRequestStatistics(portletId);

		return requestStatistics.getAverageTime();
	}

	public long getAverageTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		RequestStatistics requestStatistics =
			companyStatistics.getActionRequestStatistics(portletId);

		return requestStatistics.getAverageTime();
	}

	public long getErrorCount() {
		long errorCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			errorCount += getErrorCountByCompany(companyStatistics);
		}

		return errorCount;
	}

	public long getErrorCountByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getErrorCountByCompany(companyStatistics);
	}

	public long getErrorCountByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getErrorCountByCompany(companyStatistics);
	}

	public long getErrorCountByPortlet(String portletId)
		throws MonitoringException {

		long errorCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			errorCount += getErrorCountByPortlet(portletId, companyStatistics);
		}

		return errorCount;
	}

	public long getErrorCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getErrorCountByPortlet(portletId, companyStatistics);
	}

	public long getErrorCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getErrorCountByPortlet(portletId, companyStatistics);
	}

	public long getMaxTime() {
		long maxTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			for (RequestStatistics requestStatistics :
					companyStatistics.getActionRequestStatisticsSet()) {

				if (requestStatistics.getMaxTime() > maxTime) {
					maxTime = requestStatistics.getMaxTime();
				}
			}
		}

		return maxTime;
	}

	public long getMaxTimeByCompany(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return companyStatistics.getMaxTime();
	}

	public long getMaxTimeByCompany(String webId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return companyStatistics.getMaxTime();
	}

	public long getMaxTimeByPortlet(String portletId)
		throws MonitoringException {

		long maxTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			long curMaxTime = getMaxTimeByPortlet(portletId, companyStatistics);

			if (curMaxTime > maxTime) {
				maxTime = curMaxTime;
			}
		}

		return maxTime;
	}

	public long getMaxTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getMaxTimeByPortlet(portletId, companyStatistics);
	}

	public long getMaxTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getMaxTimeByPortlet(portletId, companyStatistics);
	}

	public long getMinTime() {
		long minTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			for (RequestStatistics requestStatistics :
					companyStatistics.getActionRequestStatisticsSet()) {

				if (requestStatistics.getMinTime() < minTime) {
					minTime = requestStatistics.getMinTime();
				}
			}
		}

		return minTime;
	}

	public long getMinTimeByCompany(long companyId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return companyStatistics.getMinTime();
	}

	public long getMinTimeByCompany(String webId) throws MonitoringException {
		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return companyStatistics.getMinTime();
	}

	public long getMinTimeByPortlet(String portletId)
		throws MonitoringException {

		long minTime = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			long curMinTime = getMinTimeByPortlet(portletId, companyStatistics);

			if (curMinTime < minTime) {
				minTime = curMinTime;
			}
		}

		return minTime;
	}

	public long getMinTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getMinTimeByPortlet(portletId, companyStatistics);
	}

	public long getMinTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getMinTimeByPortlet(portletId, companyStatistics);
	}

	public long getRequestCount() {
		long requestCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			requestCount += getRequestCountByCompany(companyStatistics);
		}

		return requestCount;
	}

	public long getRequestCountByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getRequestCountByCompany(companyStatistics);
	}

	public long getRequestCountByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getRequestCountByCompany(companyStatistics);
	}

	public long getRequestCountByPortlet(String portletId)
		throws MonitoringException {

		long requestCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			requestCount += getRequestCountByPortlet(
				portletId, companyStatistics);
		}

		return requestCount;
	}

	public long getRequestCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getRequestCountByPortlet(portletId, companyStatistics);
	}

	public long getRequestCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getRequestCountByPortlet(portletId, companyStatistics);
	}

	public long getSuccessCount() {
		long successCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			successCount += getSuccessCountByCompany(companyStatistics);
		}

		return successCount;
	}

	public long getSuccessCountByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getSuccessCountByCompany(companyStatistics);
	}

	public long getSuccessCountByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getSuccessCountByCompany(companyStatistics);
	}

	public long getSuccessCountByPortlet(String portletId)
		throws MonitoringException {

		long successCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			successCount += getSuccessCountByPortlet(
				portletId, companyStatistics);
		}

		return successCount;
	}

	public long getSuccessCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getSuccessCountByPortlet(portletId, companyStatistics);
	}

	public long getSuccessCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getSuccessCountByPortlet(portletId, companyStatistics);
	}

	public long getTimeoutCount() {
		long timeoutCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			timeoutCount += getTimeoutCountByCompany(companyStatistics);
		}

		return timeoutCount;
	}

	public long getTimeoutCountByCompany(long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getTimeoutCountByCompany(companyStatistics);
	}

	public long getTimeoutCountByCompany(String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getTimeoutCountByCompany(companyStatistics);
	}

	public long getTimeoutCountByPortlet(String portletId)
		throws MonitoringException {

		long timeoutCount = 0;

		for (CompanyStatistics companyStatistics :
				_serverStatistics.getCompanyStatisticsSet()) {

			timeoutCount += getTimeoutCountByPortlet(
				portletId, companyStatistics);
		}

		return timeoutCount;
	}

	public long getTimeoutCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(companyId);

		return getTimeoutCountByPortlet(portletId, companyStatistics);
	}

	public long getTimeoutCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		CompanyStatistics companyStatistics =
			_serverStatistics.getCompanyStatistics(webId);

		return getTimeoutCountByPortlet(portletId, companyStatistics);
	}

	public void setServerStatistics(ServerStatistics serverStatistics) {
		_serverStatistics = serverStatistics;
	}

	protected long getAverageTimeByCompany(
		CompanyStatistics companyStatistics) {

		long averageTime = 0;

		Set<RequestStatistics> requestStatisticsSet =
			companyStatistics.getActionRequestStatisticsSet();

		for (RequestStatistics requestStatistics : requestStatisticsSet) {
			averageTime += requestStatistics.getAverageTime();
		}

		return averageTime / requestStatisticsSet.size();
	}

	protected long getErrorCountByCompany(CompanyStatistics companyStatistics) {
		long errorCount = 0;

		for (RequestStatistics requestStatistics :
				companyStatistics.getActionRequestStatisticsSet()) {

			errorCount += requestStatistics.getErrorCount();
		}

		return errorCount;
	}

	protected long getErrorCountByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		RequestStatistics requestStatistics =
			companyStatistics.getActionRequestStatistics(portletId);

		return requestStatistics.getErrorCount();
	}

	protected long getMaxTimeByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		long maxTime = 0;

		RequestStatistics requestStatistics =
			companyStatistics.getActionRequestStatistics(portletId);

		if (requestStatistics.getMaxTime() > maxTime) {
			maxTime = requestStatistics.getMaxTime();
		}

		return maxTime;
	}

	protected long getMinTimeByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		long minTime = 0;

		RequestStatistics requestStatistics =
			companyStatistics.getActionRequestStatistics(portletId);

		if (requestStatistics.getMinTime() < minTime) {
			minTime = requestStatistics.getMinTime();
		}

		return minTime;
	}

	protected long getRequestCountByCompany(
		CompanyStatistics companyStatistics) {

		long requestCount = 0;

		for (RequestStatistics requestStatistics :
				companyStatistics.getActionRequestStatisticsSet()) {

			requestCount += requestStatistics.getRequestCount();
		}

		return requestCount;
	}

	protected long getRequestCountByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		RequestStatistics requestStatistics =
			companyStatistics.getActionRequestStatistics(portletId);

		return requestStatistics.getRequestCount();
	}

	protected long getSuccessCountByCompany(
		CompanyStatistics companyStatistics) {

		long successCount = 0;

		for (RequestStatistics requestStatistics :
				companyStatistics.getActionRequestStatisticsSet()) {

			successCount += requestStatistics.getSuccessCount();
		}

		return successCount;
	}

	protected long getSuccessCountByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		RequestStatistics requestStatistics =
			companyStatistics.getActionRequestStatistics(portletId);

		return requestStatistics.getSuccessCount();
	}

	protected long getTimeoutCountByCompany(
		CompanyStatistics companyStatistics) {

		long timeoutCount = 0;

		for (RequestStatistics requestStatistics :
				companyStatistics.getActionRequestStatisticsSet()) {

			timeoutCount += requestStatistics.getTimeoutCount();
		}

		return timeoutCount;
	}

	protected long getTimeoutCountByPortlet(
			String portletId, CompanyStatistics companyStatistics)
		throws MonitoringException {

		RequestStatistics requestStatistics =
			companyStatistics.getActionRequestStatistics(portletId);

		return requestStatistics.getTimeoutCount();
	}

	private ServerStatistics _serverStatistics;

}