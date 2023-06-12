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

public class SmallRoots extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doWork(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		int size = requestInt(request, "size", 111);
		int iterations = requestInt(request, "iterations", 100000);
		int waittime = requestInt(request, "waittime", 0);
		int oomlimit = requestInt(request, "oomlimit", 1);
		int sleep = requestInt(request, "sleep", 20000);

		println(out, "Starting " + iterations + " iterations of " + size
				+ " byte allocations with " + waittime + "ms delay...");

		alloc(out, 0, iterations, size, waittime, oomlimit, sleep, 0);
	}

	private int alloc(PrintWriter out, int i, int count, int size,
			int waittime, int oomlimit, int sleep, int oomcount) {
		if (i > count) {

			if (sleep > 0) {
				println(out, "Sleeping " + sleep + "ms...");
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return oomcount;
		} else {

			try {
				out.println("<br />i" + i + ": " + size + "b...");
				out.flush();
				byte[] data = new byte[size];
				out.println("done. " + data.length);
				if (waittime > 0) {
					try {
						Thread.sleep(waittime);
					} catch (InterruptedException e) {
						e.printStackTrace();
						out.println("Interrupted in Thread.sleep");
						return oomcount;
					}
				}

				oomcount = alloc(out, ++i, count, size, waittime, oomlimit,
						sleep, oomcount);

			} catch (OutOfMemoryError oom) {
				oom.printStackTrace();
				println(out, "<b>Caught OOM</b>");
				oomcount++;
				if (oomcount >= oomlimit) {
					return oomcount;
				}
			}
		}
		return oomcount;
	}
}
