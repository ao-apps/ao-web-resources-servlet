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

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class Initializer implements
  ServletContextListener,
  ServletRequestListener,
  HttpSessionListener
{

  @Override
  public void contextInitialized(ServletContextEvent event) {
    RegistryEE.Application.get(event.getServletContext());
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    // Do nothing
  }

  @Override
  public void requestInitialized(ServletRequestEvent event) {
    RegistryEE.Request.get(
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
    RegistryEE.Session.get(event.getSession());
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent event) {
    // Do nothing
  }
}
