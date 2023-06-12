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

public class ControlJVMTrace extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doWork(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		if (requestBoolean(request, "suspend", false)) {
			println(out, "Calling Trace.suspend...");
			Utilities.callStatic("com.ibm.jvm.Trace", "suspend");
			println(out, "Call returned.");
		}

		if (requestBoolean(request, "resume", false)) {
			println(out, "Calling Trace.resume...");
			Utilities.callStatic("com.ibm.jvm.Trace", "resume");
			println(out, "Call returned.");
		}

		String set = request.getParameter("set");

		// Examples:
		// print=mt
		// print=!mt

		if (set != null && set.length() > 0) {
			println(out, "Calling Trace.set... (Note that not all -Xtrace options may be changed dynamically.)");
			Utilities.callStatic1String("com.ibm.jvm.Trace", "set", set);
			println(out, "Call returned.");
		}

		if (requestBoolean(request, "snap", false)) {
			println(out, "Calling Trace.snap...");
			Utilities.callStatic("com.ibm.jvm.Trace", "snap");
			println(out, "Call returned.");
		}

		if (requestBoolean(request, "suspendThis", false)) {
			println(out, "Calling Trace.suspendThis...");
			Utilities.callStatic("com.ibm.jvm.Trace", "suspendThis");
			println(out, "Call returned.");
		}

		if (requestBoolean(request, "resumeThis", false)) {
			println(out, "Calling Trace.resumeThis...");
			Utilities.callStatic("com.ibm.jvm.Trace", "resumeThis");
			println(out, "Call returned.");
		}

		if (requestBoolean(request, "traceCustom", false)) {
			println(out, "Calling custom trace...");
			String[] templates = new String[5];
			templates[0] = Utilities.getStaticFieldString("com.ibm.jvm.Trace", "ENTRY") + "Entering %s";
			templates[1] = Utilities.getStaticFieldString("com.ibm.jvm.Trace", "EXIT") + "Exiting %s";
			templates[2] = Utilities.getStaticFieldString("com.ibm.jvm.Trace", "EVENT") + "Event id %d, text = %s";
			templates[3] = Utilities.getStaticFieldString("com.ibm.jvm.Trace", "EXCEPTION") + "Exception: %s";
			templates[4] = Utilities.getStaticFieldString("com.ibm.jvm.Trace", "EXCEPTION_EXIT")
					+ "Exception exit from %s";

			try {
				Class<?> c = Utilities.findClass("com.ibm.jvm.Trace");

				// Register a trace application called HelloWorld
				int handle = (Integer) c.getMethod("registerApplication", String.class, String[].class).invoke(null,
						"HelloWorld", templates);
				Utilities.callStatic1String("com.ibm.jvm.Trace", "set", "print=HelloWorld");
				c.getMethod("trace", int.class, int.class, int.class, String.class).invoke(null, handle, 2, 1,
						"Trace test");
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}

			println(out, "Call returned.");
		}
	}
}
