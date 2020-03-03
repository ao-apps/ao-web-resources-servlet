/*
 * ao-web-resources-servlet - Web resource management in a Servlet environment.
 * Copyright (C) 2020  AO Industries, Inc.
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
 * along with ao-web-resources-servlet.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.web.resources.servlet;

import com.aoindustries.web.resources.registry.Registry;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Provides {@linkplain Registry web resource registries} for {@link ServletContext},
 * {@link ServletRequest}, and {@link HttpSession}.
 */
public class RegistryEE {

	private static final String ATTRIBUTE = Registry.class.getName();

	@WebListener
	public static class Initializer implements
		ServletContextListener,
		ServletRequestListener,
		HttpSessionListener
	{

		@Override
		public void contextInitialized(ServletContextEvent event) {
			get(event.getServletContext());
		}

		@Override
		public void contextDestroyed(ServletContextEvent event) {
			// Do nothing
		}

		@Override
		public void requestInitialized(ServletRequestEvent event) {
			get(
				event.getServletContext(),
				event.getServletRequest()
			);
		}

		@Override
		public void requestDestroyed(ServletRequestEvent event) {
			// Do nothing
		}

		@Override
		public void sessionCreated(HttpSessionEvent event) {
			get(event.getSession());
		}

		@Override
		public void sessionDestroyed(HttpSessionEvent event) {
			// Do nothing
		}
	}

	/**
	 * Gets the {@linkplain Registry web resource registry} for the given {@linkplain ServletContext servlet context}.
	 */
	public static Registry get(ServletContext servletContext) {
		Registry registry = (Registry)servletContext.getAttribute(ATTRIBUTE);
		if(registry == null) {
			registry = new Registry();
			servletContext.setAttribute(ATTRIBUTE, registry);
		}
		return registry;
	}

	/**
	 * Gets the {@linkplain Registry web resource registry} for the given {@linkplain ServletRequest servlet request}.
	 * <p>
	 * This defaults to a copy of {@link #get(javax.servlet.ServletContext)}.
	 * </p>
	 */
	public static Registry get(ServletContext servletContext, ServletRequest request) {
		Registry registry = (Registry)request.getAttribute(ATTRIBUTE);
		if(registry == null) {
			registry = get(servletContext).copy();
			request.setAttribute(ATTRIBUTE, registry);
		}
		return registry;
	}

	/**
	 * Gets the {@linkplain Registry web resource registry} for the given {@linkplain HttpSession session}.
	 */
	public static Registry get(HttpSession session) {
		Registry registry = (Registry)session.getAttribute(ATTRIBUTE);
		if(registry == null) {
			registry = new Registry();
			session.setAttribute(ATTRIBUTE, registry);
		}
		return registry;
	}

	private RegistryEE() {}
}
