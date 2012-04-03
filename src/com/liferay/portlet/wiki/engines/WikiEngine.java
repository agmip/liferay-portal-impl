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

package com.liferay.portlet.wiki.engines;

import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.model.WikiPage;

import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Jorge Ferrer
 */
public interface WikiEngine {

	/**
	 * Convert the content of the given page to HTML using the view and edit
	 * URLs to build links.
	 *
	 * @return HTML string
	 */
	public String convert(
			WikiPage page, PortletURL viewPageURL, PortletURL editPageURL,
			String attachmentURLPrefix)
		throws PageContentException;

	/**
	 * Get a map with the links included in the given page. The key of each map
	 * entry is the title of the linked page. The value is a Boolean object that
	 * indicates if the linked page exists or not.
	 *
	 * @return a map of links
	 */
	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException;

	/**
	 * Set the configuraton to support quick links to other wikis. The format of
	 * the configuration is specific to the wiki engine.
	 */
	public void setInterWikiConfiguration(String interWikiConfiguration);

	/**
	 * Set the main wiki configuraiton as a String. The format of the
	 * configuration is specific to the wiki engine.
	 */
	public void setMainConfiguration(String mainConfiguration);

	/**
	 * Validate the content of a wiki page for this engine.
	 *
	 * @return <code>true</code> if the content is valid
	 */
	public boolean validate(long nodeId, String content);

}