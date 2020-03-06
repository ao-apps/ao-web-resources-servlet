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
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;

/**
 * Provides {@linkplain Registry web resource registries} for {@link ServletContext},
 * {@link ServletRequest}, and {@link HttpSession}.
 */
final public class RegistryEE {

	// Make no instances
	private RegistryEE() {}

	/**
	 * The application-scope {@linkplain Registry web resource registries} are always available.
	 */
	final public static class Application {

		// Make no instances
		private Application() {}

		/**
		 * The name of the application-scope attribute that contains the current application registry.
		 */
		public static final String APPLICATION_ATTRIBUTE = Application.class.getName();

		/**
		 * Gets the application-scope {@linkplain Registry web resource registry} for the given {@linkplain ServletContext servlet context}.
		 */
		public static Registry get(ServletContext servletContext) {
			Registry registry = (Registry)servletContext.getAttribute(APPLICATION_ATTRIBUTE);
			if(registry == null) {
				registry = new Registry();
				servletContext.setAttribute(APPLICATION_ATTRIBUTE, registry);
			}
			return registry;
		}
	}

	/**
	 * The request-scope {@linkplain Registry web resource registries} are always available.
	 */
	final public static class Request {

		// Make no instances
		private Request() {}

		/**
		 * The name of the request-scope attribute that contains the current request registry.
		 */
		public static final String REQUEST_ATTRIBUTE = Request.class.getName();

		/**
		 * Gets the request-scope {@linkplain Registry web resource registry} for the given {@linkplain ServletRequest servlet request}.
		 * <p>
		 * This defaults to a copy of {@link Application#get(javax.servlet.ServletContext)}.
		 * </p>
		 */
		public static Registry get(ServletContext servletContext, ServletRequest request) {
			Registry registry = (Registry)request.getAttribute(REQUEST_ATTRIBUTE);
			if(registry == null) {
				registry = Application.get(servletContext).copy();
				request.setAttribute(REQUEST_ATTRIBUTE, registry);
			}
			return registry;
		}
	}

	/**
	 * The session-scope {@linkplain Registry web resource registries} are only available
	 * when a session has been created and is active.
	 */
	final public static class Session {

		// Make no instances
		private Session() {}

		/**
		 * The name of the session-scope attribute that contains the current session registry.
		 */
		public static final String SESSION_ATTRIBUTE = Session.class.getName();

		/**
		 * Gets the session-scope {@linkplain Registry web resource registry} for the given {@linkplain HttpSession session}.
		 * <p>
		 * Note: With the current implementation, the session registry may add to the request registry, but cannot remove from it
		 * or suppress anything in it.
		 * </p>
		 * <p>
		 * TODO: Is the session registry meaningful?  The idea is that it could be used for per-person theme selection via sessions.
		 * </p>
		 *
		 * @return  The registry or {@code null} when {@code session == null}.
		 */
		public static Registry get(HttpSession session) {
			if(session == null) {
				return null;
			} else {
				Registry registry = (Registry)session.getAttribute(SESSION_ATTRIBUTE);
				if(registry == null) {
					registry = new Registry();
					session.setAttribute(SESSION_ATTRIBUTE, registry);
				}
				return registry;
			}
		}
	}

	/**
	 * Page-scope {@linkplain Registry web resource registries} are not always
	 * available.  Their availability depends on the registry having been set
	 * by the application or framework.
	 * <p>
	 * We have integrated this into our frameworks and tools, and this should
	 * be easily added to additional frameworks.  In a framework where the
	 * request and page are the same, it would be sufficient to unconditionally
	 * add a page-scope registry via a {@link ServletRequestListener}.
	 * </p>
	 */
	final public static class Page {

		// Make no instances
		private Page() {}

		/**
		 * The name of the request-scope attribute that contains the current page context.
		 */
		public static final String REQUEST_ATTRIBUTE = Page.class.getName();

		/**
		 * Gets the page-scope {@linkplain Registry web resource registry} for the given {@linkplain ServletRequest servlet request}.
		 *
		 * @return  The registry or {@code null} of there is not any current page-scope registry.
		 */
		public static Registry get(ServletRequest request) {
			return (Registry)request.getAttribute(REQUEST_ATTRIBUTE);
		}

		/**
		 * Sets the page-scope {@linkplain Registry web resource registry} in the given {@linkplain ServletRequest servlet request}.
		 */
		public static void set(ServletRequest request, Registry pageRegistry) {
			request.setAttribute(REQUEST_ATTRIBUTE, pageRegistry);
		}
	}
}
