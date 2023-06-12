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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetProperty extends BaseServlet {

	private static final long serialVersionUID = -7185669890413181295L;

	@Override
	protected void doWork(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		String name = requestString(request, "name", "");
		if (name.length() == 0) {
			println(out, "No property name specified.");
		} else {
			boolean clear = requestBoolean(request, "clear", false);
			String val = requestString(request, "value", null);
			println(out, "Current system property value for " + name + "="
					+ System.getProperty(name));

			if (clear) {
				System.clearProperty(name);
				println(out, "Cleared system property");
			} else {
				System.setProperty(name, val);
				println(out, "New system property value set: " + val);
			}
		}
	}
}
