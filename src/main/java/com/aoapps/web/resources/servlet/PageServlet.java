/*
 * ao-web-resources-servlet - Web resource management in a Servlet environment.
 * Copyright (C) 2020, 2021, 2022  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of ao-web-resources-servlet.
 *
 * ao-web-resources-servlet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ao-web-resources-servlet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ao-web-resources-servlet.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.aoapps.web.resources.servlet;

import com.aoapps.web.resources.registry.Registry;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

/**
 * A servlet that creates a {@link RegistryEE.Page page-scope web resource registry},
 * if not already present.
 */
public class PageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates the page-scope registry, if not already present, then invokes
	 * {@link HttpServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)}.
	 * Any registry added by this method is removed before returning.
	 */
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		Registry oldPageRegistry = RegistryEE.Page.get(request);
		if(oldPageRegistry == null) {
			// Create a new page-scope registry
			RegistryEE.Page.set(request, new Registry());
		}
		try {
			super.service(request, response);
		} finally {
			if(oldPageRegistry == null) {
				RegistryEE.Page.set(request, null);
			}
		}
	}
}
