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

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.websphere.cache.DistributedObjectCache;
import com.ibm.websphere.cache.EntryInfo;

public class ManageDynacache extends BaseServlet {

	private static final long serialVersionUID = 1631851946547789014L;

	@Override
	protected void doWork(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) throws Throwable {

		String instance = requestString(request, "instance", null);
		DistributedObjectCache cache = null;
		if (instance != null) {
			InitialContext ic = new InitialContext();
			cache = (DistributedObjectCache) ic.lookup(instance);
		}

		String action = requestString(request, "action", null);
		checkNullOrBlank(action, "action");
		action = action.toLowerCase();
		if (action.equals("create")) {
			if (cache == null) {
				throw new IllegalArgumentException("Could not find cache "
						+ instance);
			}

			int iterations = requestInt(request, "iterations", 1);
			String key = requestString(request, "key", null);
			boolean simple = requestBoolean(request, "simple", true);
			checkNullOrBlank(key, "key");

			while (iterations > 0) {

				String value = requestString(request, "value", null);
				checkNullOrBlank(value, "value");
				
				if (simple) {
					Object result = cache.put(iterations == 1 ? key : key + iterations, iterations == 1 ? value : value + iterations);
					println(out, "Cache entry put. Previous value = " + result);
				} else {
					String priorityStr = requestString(request, "priority",
							Integer.MAX_VALUE / 4 + "");
					int priority = Integer.parseInt(priorityStr);
					String timeToLiveStr = requestString(request, "timetolive",
							Integer.MAX_VALUE / 4 + "");
					int timeToLive = Integer.parseInt(timeToLiveStr);
					String inactivityTimeStr = requestString(request,
							"inactivitytime", Integer.MAX_VALUE / 4 + "");
					int inactivityTime = Integer.parseInt(inactivityTimeStr);
					String sharingPolicyStr = requestString(request,
							"sharingpolicy", EntryInfo.SHARED_PUSH_PULL + "");
					int sharingPolicy = Integer.parseInt(sharingPolicyStr);
					if (sharingPolicy < 1 || sharingPolicy > 4) {
						throw new IllegalArgumentException(
								"sharingPolicy must be between 1 and 4");
					}
					Object[] dependencyIds = null;
					Object result = cache.put(iterations == 1 ? key : key + iterations, iterations == 1 ? value : value + iterations,
							priority, timeToLive, inactivityTime, sharingPolicy,
							dependencyIds);
					println(out, "Cache entry put. Previous value = " + result);
				}
				iterations--;
			}
		} else if (action.equals("get")) {
			String key = requestString(request, "key", null);
			checkNullOrBlank(key, "key");

			Object val = cache.get(key);
			println(out, "Cache value = " + val);
		} else if (action.equals("remove")) {
			String key = requestString(request, "key", null);
			checkNullOrBlank(key, "key");

			Object val = cache.remove(key);
			println(out, "Removed cache value = " + val);
		} else if (action.equals("stats")) {
			println(out, "Cache size = " + cache.size());
		} else {
			throw new IllegalArgumentException("Unknown action " + action);
		}
	}
}
