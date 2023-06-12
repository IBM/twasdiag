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

import com.example.util.Utilities;

public class IBMSystemDump extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doWork(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		println(out, "Calling com.ibm.jvm.Dump.triggerDump(\"system:request=exclusive+prepwalk\")...");
		try {
			Class<?> j9Dump = Class.forName("com.ibm.jvm.Dump");
			java.lang.reflect.Method j9triggerDump = j9Dump.getMethod("triggerDump", new Class<?>[] { String.class });
			String result = (String) j9triggerDump.invoke(null, new Object[] { "system:request=exclusive+prepwalk" });
			println(out, "Result: " + result);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
}
