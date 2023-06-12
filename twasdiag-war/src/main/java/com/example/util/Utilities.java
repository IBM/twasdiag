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
package com.example.util;

import java.util.concurrent.ConcurrentHashMap;

public class Utilities {
	
	public static ConcurrentHashMap<String, Class<?>> LOADED_CLASSES = new ConcurrentHashMap<String, Class<?>>();
	
	public static void callStatic(String className, String methodName) {
		try {
			Class<?> c = findClass(className);
			c.getMethod(methodName).invoke(null);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
	public static void callStatic1String(String className, String methodName, String arg1) {
		try {
			Class<?> c = findClass(className);
			c.getMethod(methodName, String.class).invoke(null, arg1);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static String getStaticFieldString(String className, String fieldName) {
		try {
			Class<?> c = findClass(className);
			return (String) c.getField(fieldName).get(null);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
	public static Class<?> findClass(String className) throws ClassNotFoundException {
		Class<?> c = LOADED_CLASSES.get(className);
		if (c == null) {
			c = Class.forName(className);
			LOADED_CLASSES.put(className, c);
		}
		return c;
	}
}
