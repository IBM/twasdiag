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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoComplicatedStuff extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_ITERATIONS = 50;
	public static final boolean DEFAULT_MOREWORK = false;
	public static final int DEFAULT_MOREWORK_ITERATIONS = 2000;

	@Override
	protected void doWork(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		int iterations = requestInt(request, "iterations", DEFAULT_ITERATIONS);
		boolean morework = requestBoolean(request, "morework", DEFAULT_MOREWORK);

		for (int i = 0; i < iterations; i++) {
			println(out, "Iteration #" + i);
			Pattern p = Pattern
					.compile(
							"(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?",
							Pattern.CASE_INSENSITIVE | Pattern.DOTALL
									| Pattern.MULTILINE);
			Matcher m = p.matcher("<a href='test.html'>");
			while (m.find()) {
				String href = m.group();
				println(out, "href = " + href);
			}

			BigDecimal pi = computePi(1000);
			pi.add(new BigDecimal("1000"));
			println(out, "pi + 1000: " + pi.toString());

			String data = "a";
			int j = i * 100;
			while (j-- > 0) {
				data += "a";
			}
			println(out, "Created string length = " + data.length());
		}

		if (morework) {
			morework(request, out);
		}
	}

	private void morework(HttpServletRequest request, PrintWriter out) {
		Hashtable<Date, String> dates = new Hashtable<Date, String>();
		int iterations = requestInt(request, "moreiterations",
				DEFAULT_MOREWORK_ITERATIONS) + 1;
		for (int i = 1; i < iterations; i++) {
			try {
				Thread.sleep((int) (Math.random() * 300));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (0 == i % 5) {
				// Generate a random Date and index by its text representation
				for (int j = 0; j < 9; j++) {
					Date date = new Date((long) (Math.random() * 100000000));
					String dateString = date.toString();
					dates.put(date, dateString);
				}
				out.print("9");
			} else {
				String lPif = new String("Pif!");
				Integer lInt = new Integer(28);
				out.print(".(" + lPif + lInt + ")");
			}
			if (0 == i % 50) {
				out.print("\n<br />liveTest: ");
			}
		}
	}

	/** constants used in pi computation */
	private static final BigDecimal FOUR = BigDecimal.valueOf(4);

	/** rounding mode to use during pi computation */
	private static final int roundingMode = BigDecimal.ROUND_HALF_EVEN;

	/**
	 * Compute the value of pi to the specified number of digits after the
	 * decimal point. The value is computed using Machin's formula:
	 * 
	 * pi/4 = 4*arctan(1/5) - arctan(1/239)
	 * 
	 * and a power series expansion of arctan(x) to sufficient precision.
	 */
	public static BigDecimal computePi(int digits) {
		int scale = digits + 5;
		BigDecimal arctan1_5 = arctan(5, scale);
		BigDecimal arctan1_239 = arctan(239, scale);
		BigDecimal pi = arctan1_5.multiply(FOUR).subtract(arctan1_239)
				.multiply(FOUR);
		return pi.setScale(digits, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * Compute the value, in radians, of the arctangent of the inverse of the
	 * supplied integer to the specified number of digits after the decimal
	 * point. The value is computed using the power series expansion for the arc
	 * tangent:
	 * 
	 * arctan(x) = x - (x^3)/3 + (x^5)/5 - (x^7)/7 + (x^9)/9 ...
	 */
	public static BigDecimal arctan(int inverseX, int scale) {
		BigDecimal result, numer, term;
		BigDecimal invX = BigDecimal.valueOf(inverseX);
		BigDecimal invX2 = BigDecimal.valueOf(inverseX * inverseX);

		numer = BigDecimal.ONE.divide(invX, scale, roundingMode);

		result = numer;
		int i = 1;
		do {
			numer = numer.divide(invX2, scale, roundingMode);
			int denom = 2 * i + 1;
			term = numer.divide(BigDecimal.valueOf(denom), scale, roundingMode);
			if ((i % 2) != 0) {
				result = result.subtract(term);
			} else {
				result = result.add(term);
			}
			i++;
		} while (term.compareTo(BigDecimal.ZERO) != 0);
		return result;
	}

}
