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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;

/**
 * @author Bruno Basto
 * @author Brian Wing Shun Chan
 */
public class DDMXMLImpl implements DDMXML {

	public String formatXML(Document document) throws IOException {
		return document.formattedString(_XML_INDENT);
	}

	public String formatXML(String xml) throws DocumentException, IOException {

		// This is only supposed to format your xml, however, it will also
		// unwantingly change &#169; and other characters like it into their
		// respective readable versions

		xml = StringUtil.replace(xml, "&#", "[$SPECIAL_CHARACTER$]");

		try {
			xml = XMLFormatter.toString(xml, _XML_INDENT);
		}
		catch (org.dom4j.DocumentException de) {
			throw new DocumentException(de.getMessage());
		}

		xml = StringUtil.replace(xml, "[$SPECIAL_CHARACTER$]", "&#");

		return xml;
	}

	private static final String _XML_INDENT = "  ";

}