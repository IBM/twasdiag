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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClassloaderLeak extends BaseServlet
{
    private static final long serialVersionUID = 4939343353800091698L;
    
    public static List<Class<?>> leaked = new ArrayList<Class<?>>();

    public static final String JAVA_SURGERY_JAR_FILE = System.getProperty("JAVA_SURGERY_JAR_FILE");
    
    public static final Boolean LEAK_TO_LIST = Boolean.getBoolean("LEAK_TO_LIST");

    @Override
    protected void doWork(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
    {
        CustomClassLoader ccl = new CustomClassLoader(ClassloaderLeak.class.getClassLoader());
        try
        {
            Class<?> c = ccl.loadClass(JAVA_SURGERY_JAR_FILE == null ? LeakedThread.class.getName() : "Surgery");
            if (LEAK_TO_LIST) {
                leaked.add(c);
            } else {
                Constructor<?> constructor = (JAVA_SURGERY_JAR_FILE == null ? c : LeakedThread.class).getConstructors()[0];
                Thread thread = (Thread) constructor.newInstance(JAVA_SURGERY_JAR_FILE == null ? ccl : c);
                thread.setDaemon(true);
                thread.setName(LeakedThread.class.getSimpleName() + "0x"
                                + Long.toHexString(System.identityHashCode(thread)));
                thread.start();
                println(out, "Spawned LeakedThread 0x" + Long.toHexString(thread.getId()));
            }
        }
        catch (Throwable t)
        {
            throw new RuntimeException(t);
        }
    }

    public class CustomClassLoader extends ClassLoader
    {
        ClassLoader parent;

        public CustomClassLoader(ClassLoader parent)
        {
            this.parent = parent;
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException
        {
            if (JAVA_SURGERY_JAR_FILE != null)
            {
                try
                {
                    URLClassLoader ucl = null;
                    try
                    {
                        ucl = new URLClassLoader(new URL[] { new File(JAVA_SURGERY_JAR_FILE).toURI().toURL() });
                        return ucl.loadClass(name);
                    }
                    finally
                    {
                        if (ucl != null)
                        {
                            ucl.close();
                        }
                    }
                }
                catch (MalformedURLException e)
                {
                    throw new ClassNotFoundException("Could not load class", e);
                }
                catch (IOException e)
                {
                    throw new ClassNotFoundException("Could not load class", e);
                }
            }
            else
            {
                Class<?> result = parent.loadClass(name);
                if (result == null)
                {
                    result = super.loadClass(name);
                }
                return result;
            }
        }
    }

    public static class LeakedThread extends Thread
    {
        @SuppressWarnings("unused")
        private Object obj;

        public LeakedThread(Object obj)
        {
            this.obj = obj;
        }

        @Override
        public void run()
        {
            try
            {
                Object o = new Object();
                synchronized (o)
                {
                    o.wait();
                }
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
        }
    }
}
