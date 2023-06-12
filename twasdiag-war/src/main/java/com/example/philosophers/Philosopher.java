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
package com.example.philosophers;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static com.example.DoComplicatedStuff.computePi;

public class Philosopher extends Thread {
	Chopstick leftChopstick, rightChopstick;
	String name = null;
	int howLong = 0, t = 0;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	PrintWriter out_;
	
	public Philosopher(PrintWriter out, String name, Chopstick left, Chopstick right,
			int priority) {
		this.name = name;
		this.leftChopstick = left;
		this.rightChopstick = right;
		out_ = out;
		setPriority(priority);
		setName(name);
	}

	public String getPhilosopherName() {
		return name;
	}

	public void run() {
		while (true) {
			synchronized (leftChopstick) {

				// 1.  Sleep between obtaining locks.   
				// This gives a good chance of hitting a deadlock almost immediately.
				//randomSleep(2000);

				synchronized (rightChopstick) {
					out_.println("At " + dateFormat.format(new Date()) + ", " + getPhilosopherName() + " is eating with " + leftChopstick.getName() + " and " + rightChopstick.getName() + ".<br />");
					out_.flush(); 

					//eatLight();
					eatModerate();
					//eatHeavy();

					// 2.  Sleep after eating, while holding locks.     
					// This gives a good chance of running for a bit, then hitting a deadlock.
					// It's not that sleeping vs. doing work a few lines above effects the likelihood of deadlock,
					// but doing the "eat" work just generates more data for the dump(s), or profiling etc.
					randomSleep(2000);
				}
			}

			// 3.  Sleep after giving up locks.   
			// This gives a good chance of NOT hitting a deadlock 
			// randomSleep(2000);
		}
	}
	
	@SuppressWarnings("unused")
    private void eatLight() {
		computePi(10); 
	}

	private void eatModerate() {
		computePi(1000); 
	}

	@SuppressWarnings("unused")
    private void eatHeavy() {
		computePi(1000000); 
	}

	private void randomSleep(long maxSleepMillis) {

		double sleepTime = Math.random() * maxSleepMillis;

		try {
			sleep(Math.round(sleepTime));
		} catch (InterruptedException ie) { }

	}
}
