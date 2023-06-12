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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AllocateObjects extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	private static List holder = new ArrayList();
	public static int DefaultSize = 1024 * 1024 * 100;
	public static int DefaultIterations = 64;
	public static int DefaultWaitTime = 100;
	public static int DefaultOOMLimit = 1;
	public static boolean DefaultRetainData = false;
	public static boolean DefaultRetainDataGc = true;

	@SuppressWarnings("unchecked")
	@Override
	protected void doWork(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {

		int size = requestInt(request, "size", DefaultSize);
		int iterations = requestInt(request, "iterations", DefaultIterations);
		int waittime = requestInt(request, "waittime", DefaultWaitTime);
		int oomlimit = requestInt(request, "oomlimit", DefaultOOMLimit);
		int sleep = requestInt(request, "sleep", 0);
		boolean retainData = requestBoolean(request, "retainData",
				DefaultRetainData);
		boolean retainDataGc = requestBoolean(request, "retainDataGc",
				DefaultRetainDataGc);
		int oomcount = 0;

		println(out, "Starting " + iterations + " iterations of " + size
				+ " byte allocations with " + waittime + "ms delay...");

		try {
			for (int i = 1; i <= iterations; i++) {
				try {
					println(out, "Iteration " + i
							+ ": Allocating byte array of " + size
							+ " bytes... [" + holder.size() + "]", true, false);
					byte[] data = new byte[size];
					holder.add(data);
					println(out, "allocation complete of " + data.length
							+ " bytes.");

					if (waittime > 0) {
						try {
							Thread.sleep(waittime);
						} catch (InterruptedException e) {
							e.printStackTrace();
							println(out, "Interrupted in Thread.sleep");
							break;
						}
					}
				} catch (OutOfMemoryError oom) {
					oom.printStackTrace();
					println(out, "<b>Caught OOM</b>");
					oomcount++;
					if (oomlimit > 0 && oomcount >= oomlimit) {
						break;
					}
				}
			}
		} finally {
			if (retainData) {

			} else {
				holder.clear();
				println(out, "Allocated objects cleared.");

				if (retainDataGc) {
					System.gc();
					println(out, "System.gc() called.");
				}
			}
		}

		// This is different from waittime.
		if (sleep > 0) {
			println(out, "Sleeping " + sleep + "ms...");
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
