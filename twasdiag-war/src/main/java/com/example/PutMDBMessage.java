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
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.ibm.jms.IMDBPutStatelessLocal;

public class PutMDBMessage extends BaseServlet {

	private static final long serialVersionUID = 8681951835527197909L;

	@Override
	protected void doWork(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		@SuppressWarnings("unused")
		String message = requestString(request, "message", "Hello World "
				+ new Date());
		println(out, "Putting message...");
//		IMDBPutStatelessLocal ejb;
//
//		try {
//			ejb = (IMDBPutStatelessLocal) new InitialContext()
//					.lookup("ejblocal:com.ibm.jms.IMDBPutStatelessLocal");
//			ejb.doPut(message);
//		} catch (Throwable t) {
//			Helper.printThrowableToStream(out, t);
//		}

		println(out, "Message put into queue.");
	}
}