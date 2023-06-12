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
import java.util.Dictionary;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Sessions extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Override
	protected void doWork(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		String prefix = requestString(request, "prefix", "session_attribute");
		int numAttributes = requestInt(request, "num", 0);

		int count = requestInt(request, "count", 0);
		int size = requestInt(request, "size", 1048576);

		HttpSession session = request.getSession();

		if (session.isNew()) {
			println(out, "This is a new session.");
		}

		if (requestBoolean(request, "clear", false)) {
			Enumeration e = session.getAttributeNames();
			int removed = 0;
			while (e.hasMoreElements()) {
				String attributeName = (String) e.nextElement();
				session.removeAttribute(attributeName);
				removed++;
			}
			println(out, removed + " sessions were removed. Elements left = "
					+ session.getAttributeNames().hasMoreElements());
		}

		if (requestBoolean(request, "clear", false)) {

		}

		for (int i = 1; i <= numAttributes; i++) {
			session.setAttribute(prefix + i, new SimpleHashtable(prefix, i));
		}

		if (count > 0) {
			for (int i = 0; i < count; i++) {
				byte[] data = new byte[size];
				session.setAttribute(prefix + i, data);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public class SimpleHashtable extends Dictionary {

		private transient HashtableEntry table[];

		public SimpleHashtable(String prefix, int i) {
			table = new HashtableEntry[1];
			table[0] = new HashtableEntry();
			table[0].key = prefix + i;
			table[0].value = i;
		}

		@Override
		public Enumeration elements() {
			return null;
		}

		@Override
		public Object get(Object key) {
			return null;
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public Enumeration keys() {
			return null;
		}

		@Override
		public Object put(Object key, Object value) {
			return null;
		}

		@Override
		public Object remove(Object key) {
			return null;
		}

		@Override
		public int size() {
			return 0;
		}

	}

	public class HashtableEntry {
		int hash;
		Object key;
		Object value;
		HashtableEntry next;
	}
}
