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

public class LoopWithIntervals extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doWork(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		int count = requestInt(request, "count", 50000);
		int interval = requestInt(request, "interval", 1000);
		int sleep = requestInt(request, "sleep", 100);
		String dummy = request.getParameter("dummy");

		String str = "Looping " + count + " times with a sleep of " + sleep
				+ "ms every " + interval + "times...";

		println(out, str);

		DummyClass dummyClass = new DummyClass(dummy == null ? str : dummy,
				true);

		for (int i = 0; i < count; i++) {
			if ((i % interval) == 0) {
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					Helper.printThrowableToStream(out, e);
				}
			}
			uselessMethod(i, dummyClass);
		}
	}

	public void uselessMethod(int iteration, Object trash) {
		// Perform some useless logic
		String s = new Integer(iteration).toString();
		if (trash != null && trash instanceof DummyClass) {
			DummyClass dummy = (DummyClass) trash;
			if (s.equals(dummy.getDummyString())) {
				dummy.setDummyBoolean(true);
			}
		}
	}
}
