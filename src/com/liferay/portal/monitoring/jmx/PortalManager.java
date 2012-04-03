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

package com.liferay.portal.monitoring.jmx;

import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.monitoring.statistics.SummaryStatistics;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.monitoring.statistics.portal.ServerStatistics;

import java.util.Set;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortalManager implements PortalManagerMBean {

	public long getAverageTime() throws MonitoringException {
		return _summaryStatistics.getAverageTime();
	}

	public long getAverageTimeByCompany(long companyId)
		throws MonitoringException {

		return _summaryStatistics.getAverageTimeByCompany(companyId);
	}

	public long getAverageTimeByCompany(String webId)
		throws MonitoringException {

		return _summaryStatistics.getAverageTimeByCompany(webId);
	}

	public long[] getCompanyIds() {
		Set<Long> companyIds = _serverStatistics.getCompanyIds();

		return ArrayUtil.toArray(
			companyIds.toArray(new Long[companyIds.size()]));
	}

	public long getErrorCount() throws MonitoringException {
		return _summaryStatistics.getErrorCount();
	}

	public long getErrorCountByCompany(long companyId)
		throws MonitoringException {

		return _summaryStatistics.getErrorCountByCompany(companyId);
	}

	public long getErrorCountByCompany(String webId)
		throws MonitoringException {

		return _summaryStatistics.getErrorCountByCompany(webId);
	}

	public long getMaxTime() throws MonitoringException {
		return _summaryStatistics.getMaxTime();
	}

	public long getMaxTimeByCompany(long companyId) throws MonitoringException {
		return _summaryStatistics.getMaxTimeByCompany(companyId);
	}

	public long getMaxTimeByCompany(String webId) throws MonitoringException {
		return _summaryStatistics.getMaxTimeByCompany(webId);
	}

	public long getMinTime() throws MonitoringException {
		return _summaryStatistics.getMinTime();
	}

	public long getMinTimeByCompany(long companyId) throws MonitoringException {
		return _summaryStatistics.getMinTimeByCompany(companyId);
	}

	public long getMinTimeByCompany(String webId) throws MonitoringException {
		return _summaryStatistics.getMinTimeByCompany(webId);
	}

	public long getRequestCount() throws MonitoringException {
		return _summaryStatistics.getRequestCount();
	}

	public long getRequestCountByCompany(long companyId)
		throws MonitoringException {

		return _summaryStatistics.getRequestCountByCompany(companyId);
	}

	public long getRequestCountByCompany(String webId)
		throws MonitoringException {

		return _summaryStatistics.getRequestCountByCompany(webId);
	}

	public long getStartTime(long companyId) throws MonitoringException {
		return _serverStatistics.getCompanyStatistics(companyId).getStartTime();
	}

	public long getStartTime(String webId) throws MonitoringException {
		return _serverStatistics.getCompanyStatistics(webId).getStartTime();
	}

	public long getSuccessCount() throws MonitoringException {
		return _summaryStatistics.getSuccessCount();
	}

	public long getSuccessCountByCompany(long companyId)
		throws MonitoringException {

		return _summaryStatistics.getSuccessCountByCompany(companyId);
	}

	public long getSuccessCountByCompany(String webId)
		throws MonitoringException {

		return _summaryStatistics.getSuccessCountByCompany(webId);
	}

	public long getTimeoutCount() throws MonitoringException {
		return _summaryStatistics.getTimeoutCount();
	}

	public long getTimeoutCountByCompany(long companyId)
		throws MonitoringException {

		return _summaryStatistics.getTimeoutCountByCompany(companyId);
	}

	public long getTimeoutCountByCompany(String webId)
		throws MonitoringException {

		return _summaryStatistics.getTimeoutCountByCompany(webId);
	}

	public long getUptime(long companyId) throws MonitoringException {
		return _serverStatistics.getCompanyStatistics(companyId).getUptime();
	}

	public long getUptime(String webId) throws MonitoringException {
		return _serverStatistics.getCompanyStatistics(webId).getUptime();
	}

	public String[] getWebIds() {
		Set<String> webIds = _serverStatistics.getWebIds();

		return webIds.toArray(new String[webIds.size()]);
	}

	public void reset() {
		_serverStatistics.reset();
	}

	public void reset(long companyId) {
		_serverStatistics.reset(companyId);
	}

	public void reset(String webId) {
		_serverStatistics.reset(webId);
	}

	public void setServerStatistics(ServerStatistics serverStatistics) {
		_serverStatistics = serverStatistics;
	}

	public void setSummaryStatistics(SummaryStatistics summaryStatistics) {
		_summaryStatistics = summaryStatistics;
	}

	private ServerStatistics _serverStatistics;
	private SummaryStatistics _summaryStatistics;

}