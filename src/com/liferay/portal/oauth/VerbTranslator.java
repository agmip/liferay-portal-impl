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

package com.liferay.portal.oauth;

import com.liferay.portal.kernel.oauth.Verb;

/**
 * @author Brian Wing Shun Chan
 */
public class VerbTranslator {

	public static Verb translate(org.scribe.model.Verb verb) {
		if (verb == org.scribe.model.Verb.DELETE) {
			return Verb.DELETE;
		}
		else if (verb == org.scribe.model.Verb.GET) {
			return Verb.GET;
		}
		else if (verb == org.scribe.model.Verb.POST) {
			return Verb.POST;
		}
		else if (verb == org.scribe.model.Verb.PUT) {
			return Verb.PUT;
		}
		else {
			throw new IllegalArgumentException("Unknown verb " + verb);
		}
	}

	public static org.scribe.model.Verb translate(Verb verb) {
		if (verb == Verb.DELETE) {
			return org.scribe.model.Verb.DELETE;
		}
		else if (verb == Verb.GET) {
			return org.scribe.model.Verb.GET;
		}
		else if (verb == Verb.POST) {
			return org.scribe.model.Verb.POST;
		}
		else if (verb == Verb.PUT) {
			return org.scribe.model.Verb.PUT;
		}
		else {
			throw new IllegalArgumentException("Unknown verb " + verb);
		}
	}

}