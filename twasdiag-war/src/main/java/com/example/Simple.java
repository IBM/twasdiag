/*
 * Copyright 2023 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.example;

import java.io.PrintWriter;
import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Simple extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_SESSIONID = "JSESSIONID";

	@Override
	protected void doWork(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		String searchCookie = request.getParameter("sessionname");
		if (searchCookie == null || searchCookie.length() == 0) {
			searchCookie = DEFAULT_SESSIONID;
		}

		for (Cookie cookie : request.getCookies()) {
			String name = cookie.getName();
			if (name != null && name.contains(searchCookie)) {
				println(out, encode(name) + "=" + encode(cookie.getValue()));
			}
		}

		out.println("<h2>All Cookies</h2>");
		for (Cookie cookie : request.getCookies()) {
			String name = cookie.getName();
			println(out, encode(name) + "=" + encode(cookie.getValue()));
		}

		out.println("<h2>System Properties</h2>");
		Properties props = System.getProperties();
		for (Entry<Object, Object> prop : props.entrySet()) {
			String key = (String) prop.getKey();
			String val = (String) prop.getValue();
			println(out, encode(key) + "=" + encode(val));
		}

		out.println("<br /><h2>System Environment</h2>");
		for (Entry<String, String> mapv : System.getenv().entrySet()) {
			String key = (String) mapv.getKey();
			String val = (String) mapv.getValue();
			println(out, encode(key) + "=" + encode(val));
		}

		out.println("<br /><h2>Runtime</h2>");
		Runtime r = Runtime.getRuntime();
		println(out, "availableProcessors=" + encode(r.availableProcessors()));
		println(out, "freeMemory=" + encode(r.freeMemory()));
		println(out, "maxMemory=" + encode(r.maxMemory()));
		println(out, "totalMemory=" + encode(r.totalMemory()));
	}
}