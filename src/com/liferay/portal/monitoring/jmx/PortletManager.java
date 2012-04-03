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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.monitoring.statistics.portlet.PortletSummaryStatistics;
import com.liferay.portal.monitoring.statistics.portlet.ServerStatistics;

import java.util.Set;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortletManager implements PortletManagerMBean {

	public long getAverageTime() throws MonitoringException {
		return _portletSummaryStatistics.getAverageTime();
	}

	public long getAverageTimeByCompany(long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getAverageTimeByCompany(companyId);
	}

	public long getAverageTimeByCompany(String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getAverageTimeByCompany(webId);
	}

	public long getAverageTimeByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getAverageTimeByPortlet(portletId);
	}

	public long getAverageTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getAverageTimeByPortlet(
			portletId, companyId);
	}

	public long getAverageTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getAverageTimeByPortlet(
			portletId, webId);
	}

	public long[] getCompanyIds() {
		Set<Long> companyIds = _serverStatistics.getCompanyIds();

		return ArrayUtil.toArray(
			companyIds.toArray(new Long[companyIds.size()]));
	}

	public long getErrorCount() throws MonitoringException {
		return _portletSummaryStatistics.getErrorCount();
	}

	public long getErrorCountByCompany(long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getErrorCountByCompany(companyId);
	}

	public long getErrorCountByCompany(String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getErrorCountByCompany(webId);
	}

	public long getErrorCountByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getErrorCountByPortlet(portletId);
	}

	public long getErrorCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getErrorCountByPortlet(
			portletId, companyId);
	}

	public long getErrorCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getErrorCountByPortlet(
			portletId, webId);
	}

	public long getMaxTime() throws MonitoringException {
		return _portletSummaryStatistics.getMaxTime();
	}

	public long getMaxTimeByCompany(long companyId) throws MonitoringException {
		return _portletSummaryStatistics.getMaxTimeByCompany(companyId);
	}

	public long getMaxTimeByCompany(String webId) throws MonitoringException {
		return _portletSummaryStatistics.getMaxTimeByCompany(webId);
	}

	public long getMaxTimeByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getMaxTimeByPortlet(portletId);
	}

	public long getMaxTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getMaxTimeByPortlet(
			portletId, companyId);
	}

	public long getMaxTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getMaxTimeByPortlet(portletId, webId);
	}

	public long getMinTime() throws MonitoringException {
		return _portletSummaryStatistics.getMinTime();
	}

	public long getMinTimeByCompany(long companyId) throws MonitoringException {
		return _portletSummaryStatistics.getMinTimeByCompany(companyId);
	}

	public long getMinTimeByCompany(String webId) throws MonitoringException {
		return _portletSummaryStatistics.getMinTimeByCompany(webId);
	}

	public long getMinTimeByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getMinTimeByPortlet(portletId);
	}

	public long getMinTimeByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getMinTimeByPortlet(
			portletId, companyId);
	}

	public long getMinTimeByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getMinTimeByPortlet(portletId, webId);
	}

	public String[] getPortletIds() {
		Set<String> portletIds = _serverStatistics.getPortletIds();

		return portletIds.toArray(new String[portletIds.size()]);
	}

	public long getRequestCount() throws MonitoringException {
		return _portletSummaryStatistics.getRequestCount();
	}

	public long getRequestCountByCompany(long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getRequestCountByCompany(companyId);
	}

	public long getRequestCountByCompany(String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getRequestCountByCompany(webId);
	}

	public long getRequestCountByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getRequestCountByPortlet(portletId);
	}

	public long getRequestCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getRequestCountByPortlet(
			portletId, companyId);
	}

	public long getRequestCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getRequestCountByPortlet(
			portletId, webId);
	}

	public long getSuccessCount() throws MonitoringException {
		return _portletSummaryStatistics.getSuccessCount();
	}

	public long getSuccessCountByCompany(long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getSuccessCountByCompany(companyId);
	}

	public long getSuccessCountByCompany(String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getSuccessCountByCompany(webId);
	}

	public long getSuccessCountByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getSuccessCountByPortlet(portletId);
	}

	public long getSuccessCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getSuccessCountByPortlet(
			portletId, companyId);
	}

	public long getSuccessCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getSuccessCountByPortlet(
			portletId, webId);
	}

	public long getTimeoutCount() throws MonitoringException {
		return _portletSummaryStatistics.getTimeoutCount();
	}

	public long getTimeoutCountByCompany(long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getTimeoutCountByCompany(companyId);
	}

	public long getTimeoutCountByCompany(String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getTimeoutCountByCompany(webId);
	}

	public long getTimeoutCountByPortlet(String portletId)
		throws MonitoringException {

		return _portletSummaryStatistics.getTimeoutCountByPortlet(portletId);
	}

	public long getTimeoutCountByPortlet(String portletId, long companyId)
		throws MonitoringException {

		return _portletSummaryStatistics.getTimeoutCountByPortlet(
			portletId, companyId);
	}

	public long getTimeoutCountByPortlet(String portletId, String webId)
		throws MonitoringException {

		return _portletSummaryStatistics.getTimeoutCountByPortlet(
			portletId, webId);
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

	public void setPortletSummaryStatistics(
		PortletSummaryStatistics portletSummaryStatistics) {

		_portletSummaryStatistics = portletSummaryStatistics;
	}

	public void setServerStatistics(ServerStatistics serverStatistics) {
		_serverStatistics = serverStatistics;
	}

	private PortletSummaryStatistics _portletSummaryStatistics;
	private ServerStatistics _serverStatistics;

}