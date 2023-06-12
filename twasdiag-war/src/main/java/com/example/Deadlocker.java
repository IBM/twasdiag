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

import com.example.philosophers.Chopstick;
import com.example.philosophers.Philosopher;

public class Deadlocker extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doWork(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		Chopstick c1 = new Chopstick("chopstick 1");
		Chopstick c2 = new Chopstick("chopstick 2");
		Chopstick c3 = new Chopstick("chopstick 3");
		Philosopher p1 = new Philosopher(out, "Socrates priority5", c1, c2, 5);
		Philosopher p2 = new Philosopher(out, "Plato priority9", c3, c1, 9);
		Philosopher p3 = new Philosopher(out, "Aristotle priority5", c2, c3, 5);
		p1.start();
		p2.start();
		p3.start();

		// SET BREAKPOINT or parameterize this if you want to customize this behavior.
		try {
			Thread.sleep(100000);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return;

		/*
		 * This is the original as coded.  It will hang since none of these threads ever finish.
		 * Seems kind of pointless, so commenting out.
		 */

		/*
		try {
			p1.join();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		try {
			p2.join();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		try {
			p3.join();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		*/
	}
}
