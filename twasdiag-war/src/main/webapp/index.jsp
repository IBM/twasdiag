<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.example.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>twasdiag</title>
<style>
body {
	padding: 0;
	margin: 0;
}

#content {
	padding: 10px;
}

#content table {
	border: 1px solid #000000;
}

#content th {
	background-color: #000000;
	color: #FFFFFF;
	font-weight: bold;
	font-style: italic;
}

#content td {
	vertical-align: top;
	border-bottom: 1px solid #000000;
	border-right: 1px solid #000000;
}

.GMW-QY4DOF {
	background: url("images/bg.png") repeat-x scroll 0 0 transparent;
	height: 100%;
	overflow: hidden;
	padding-bottom: 1px;
}

.GMW-QY4DLF {
	background: url("images/index.png") no-repeat scroll 220px 0 transparent;
	height: 100%;
	overflow: hidden;
	width: 100%;
}

.GMW-QY4DMF {
	color: white;
	display: block;
	float: left;
	font-family: Helvetica, Verdana, Tahoma, Arial, sans-serif;
	font-size: 1.7em;
	margin-left: 18px;
	margin-right: 10px;
	vertical-align: middle;
}

.GMW-QY4DIF {
	color: white;
	display: block;
	font-size: 1.4em;
}

.GMW-QY4DJF {
	background: url("images/sm.png") no-repeat scroll 0 0 transparent;
	height: 16px;
	overflow: hidden;
	width: 42px;
}
</style>
</head>
<body>
	<div style="overflow: hidden; height: 37px;">
		<div class="GMW-QY4DOF">
			<div class="GMW-QY4DLF">
				<table width="100%" height="100%" cellspacing="0" cellpadding="0"
					style="border-collapse: collapse;">
					<tr>
						<td style="width: 100%; vertical-align: middle;"><span
							class="GMW-QY4DMF">twasdiag</span> <span class="GMW-QY4DIF">&nbsp;</span>
						</td>
						<td>
							<div class="GMW-QY4DJF"></div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div id="content">
		<p>Select an activity. Clicking any link will open it in a new
			window/tab. Servlets are preferred over JSPs to enhance source code
			line number correlation.</p>
		<p>
			The options column lists available options for the link and their
			default values. Options are specified by appending them to the link
			with a <a href="http://en.wikipedia.org/wiki/Query_string"
				target="_blank">query string</a>. A query string starts with a
			question mark (?) and is followed by name/value pairs. The name is
			case-sensitive. The name is separated from the value with an equal
			sign (=), and pairs are separated with an ampersand (&amp;). For
			booleans, use true and false as the value. Example:
			Link?option1=value&amp;option2=value
		</p>
		<p>
			Contact Kevin Grigorenko (<a
				href="mailto:kevin.grigorenko@us.ibm.com">kevin.grigorenko@us.ibm.com</a>)
			with comments or questions.
		</p>
		<table border="0" cellspacing="0" cellpadding="5">
			<tr>
				<th>Activity</th>
				<th>Link</th>
				<th>Options</th>
				<th>Description</th>
			</tr>
			<tr>
				<td>Sleep Servlet</td>
				<td><a href="Sleep" target="_blank">/Sleep</a>
				</td>
				<td>
					<ul>
						<li>duration: The time to sleep, in milliseconds. Default=<%=Sleep.DEFAULT_DURATION%></li>
					</ul></td>
				<td>Sleeps the executing Servlet thread with a call to <a
					href="http://java.sun.com/j2se/1.5.0/docs/api/java/lang/Thread.html#sleep(long)"
					target="_blank">java.lang.Thread.sleep()</a>.
					<p />
					<a href="Sleep?duration=900000" target="_blank">Pop hung
						detection</a>
				</td>
			</tr>
			<tr>
				<td>Sleep JSP</td>
				<td><a href="sleep.jsp" target="_blank">/sleep.jsp</a>
				</td>
				<td>
					<ul>
						<li>duration: The time to sleep, in milliseconds. Default=<%=Sleep.DEFAULT_DURATION%></li>
					</ul></td>
				<td>Exactly the same as the Sleep Servlet but simply in JSP
					form, just to have a JSP in this EAR (e.g. to test JSP
					compilation).</td>
			</tr>
			<tr>
				<td>Initiate a javacore/javadump (IBM JVM only)</td>
				<td><a href="IBMJavaDump" target="_blank">/IBMJavaDump</a>
				</td>
				<td>None</td>
				<td>Calls the IBM JVM-only method <a
					href="http://publib.boulder.ibm.com/infocenter/javasdk/v5r0/topic/com.ibm.java.doc.diagnostics.50/diag/tools/javadump_trigger.html"
					target="_blank">com.ibm.jvm.Dump.JavaDump()</a>. For more
					information on javadumps, see <a
					href="http://publib.boulder.ibm.com/infocenter/javasdk/v5r0/topic/com.ibm.java.doc.diagnostics.50/diag/tools/javadump.html"
					target="_blank">Using Javadumps</a>.</td>
			</tr>
			<tr>
				<td>Initiate a heapdump (IBM JVM only)</td>
				<td><a href="IBMHeapDump" target="_blank">/IBMHeapDump</a>
				</td>
				<td>None</td>
				<td>Calls the IBM JVM-only method <a
					href="http://publib.boulder.ibm.com/infocenter/javasdk/v5r0/topic/com.ibm.java.doc.diagnostics.50/diag/tools/heapdump_enable.html"
					target="_blank">com.ibm.jvm.Dump.HeapDump()</a>. For more
					information on heapdumps, see <a
					href="http://publib.boulder.ibm.com/infocenter/javasdk/v5r0/topic/com.ibm.java.doc.diagnostics.50/diag/tools/heapdump.html"
					target="_blank">Using Heapdump</a>.</td>
			</tr>
			<tr>
				<td>Initiate a system dump (IBM JVM only)</td>
				<td><a href="IBMSystemDump" target="_blank">/IBMSystemDump</a>
				</td>
				<td>None</td>
				<td>Calls the IBM JVM-only method <a
					href="http://publib.boulder.ibm.com/infocenter/javasdk/v5r0/topic/com.ibm.java.doc.diagnostics.50/diag/tools/diagnostics_summary.html"
					target="_blank">com.ibm.jvm.Dump.SystemDump()</a>. For more
					information on system dumps, see <a
					href="http://publib.boulder.ibm.com/infocenter/javasdk/v5r0/topic/com.ibm.java.doc.diagnostics.50/diag/tools/dump_viewer.html"
					target="_blank">Using system dumps and the dump viewer</a>.</td>
			</tr>
			<tr>
				<td>Force a garbage collection using System.gc()</td>
				<td><a href="GarbageCollect" target="_blank">/GarbageCollect</a>
				</td>
				<td>None</td>
				<td>Call <a
					href="http://java.sun.com/j2se/1.5.0/docs/api/java/lang/System.html#gc()"
					target="_blank">System.gc()</a>. If the JVM is running with generic
					JVM argument -Xdisableexplicitgc (IBM JVM) or
					-XX:+DisableExplicitGC (Sun JVM), then calling System.gc() has no
					effect. You can determine if an IBM JVM has this option enabled by
					taking a javadump and consulting the 1CIUSERARGS section.</td>
			</tr>
			<tr>
				<td>Allocate Objects</td>
				<td><a href="AllocateObject" target="_blank">/AllocateObject</a>
				</td>
				<td>
					<ul>
						<li>size: The size of each object to allocate, in bytes.
							Default=<%=AllocateObjects.DefaultSize%></li>
						<li>iterations: The number of objects to allocate. Default=<%=AllocateObjects.DefaultIterations%></li>
						<li>waittime: The time to wait between each allocation, in
							milliseconds. Use 0 for no wait time. Default=<%=AllocateObjects.DefaultWaitTime%></li>
						<li>oomlimit: After catching this many OutOfMemoryError
							exceptions, stop processing. Use 0 for no limit on the number of
							OOMs. Default=<%=AllocateObjects.DefaultOOMLimit%></li>
						<li>retainData: Boolean. If true, then do not release
							allocated objects at the end of the servlet. Default=<%=AllocateObjects.DefaultRetainData%></li>
						<li>retainDataGc: Boolean. If retainData=false and this value
							is true, then perform a <a
							href="http://java.sun.com/j2se/1.5.0/docs/api/java/lang/System.html#gc()"
							target="_blank">System.gc()</a> at the end of the servlet.
							Default=<%=AllocateObjects.DefaultRetainDataGc%></li>
					</ul></td>
				<td>Allocate [iterations] objects of [size] bytes each,
					sleeping [waittime] between each allocation. After catching
					[oomlimit] OutOfMemoryError exceptions, abort. The objects are
					allocated by calling "new byte[size]" and then adding that to a
					List.</td>
			</tr>
			<tr>
				<td>Hang a thread using Object.wait</td>
				<td><a href="Hang" target="_blank">/Hang</a>
				</td>
				<td>None</td>
				<td>Hangs the servlet thread infinitely by calling
					Object.wait() on a synchronized variable. <b>WARNING</b>: You will
					lose this thread until the JVM is restarted!</td>
			</tr>
			<tr>
				<td>Deadlocker (Dining Philosophers)</td>
				<td><a href="Deadlocker" target="_blank">/Deadlocker</a>
				</td>
				<td>None</td>
				<td>Attempt to create a deadlock with an algorithm that
					emulates the <a
					href="http://en.wikipedia.org/wiki/Dining_philosophers_problem"
					target="_blank">Dining Philosophers problem</a>. You will know a
					deadlock has occurred if messages stop being written to the HTML
					output. To confirm if a deadlock has occurred, take a javadump and
					search for "Deadlock" (note the capitalization). It is possible, based on CPU availability,
					OS timing, and thread dispatching that a true deadlock will not
					occur.</td>
			</tr>
			<tr>
				<td>Database test</td>
				<td><a href="dbtest.jsp" target="_blank">/dbtest.jsp</a>
				</td>
				<td>See JSP</td>
				<td>Opens a JSP which lets you test a database connection and
					execute an arbitrary SQL SELECT query and return its row count.</td>
			</tr>
			<tr>
				<td>HTTP POST JSP that Sleeps</td>
				<td><a href="post_sleep.jsp" target="_blank">/post_sleep.jsp</a>
				</td>
				<td>See JSP</td>
				<td>Opens a JSP which lets you execute a form with an HTTP
					POST.</td>
			</tr>
			<tr>
				<td>DoComplicatedStuff</td>
				<td><a href="DoComplicatedStuff" target="_blank">/DoComplicatedStuff</a>
				</td>
				<td>
					<ul>
						<li>iterations: The number of times to run the complicated
							work. Default=<%=DoComplicatedStuff.DEFAULT_ITERATIONS%></li>
						<li>morework: Boolean. Create some dates and Strings and put
							them in a Hashtable. May be useful for profiling as the objects
							are kept around. Default=<%=DoComplicatedStuff.DEFAULT_MOREWORK%></li>
						<li>moreiterations: If morework is true, the number of times
							to run the extra work. Default=<%=DoComplicatedStuff.DEFAULT_MOREWORK_ITERATIONS%></li>
					</ul></td>
				<td>Generate various CPU usage such as compiling regular
					expressions, creating BigDecimals, creating long Strings, and
					calculating PI.</td>
			</tr>
			<tr>
				<td>Trace</td>
				<td><a href="Trace" target="_blank">/Trace</a>
				</td>
				<td>
					<ul>
						<li>newvalue: New trace specification.</li>
					</ul></td>
				<td>Equivalent to AdminControl.setAttribute(AdminControl.completeObjectName("type=TraceService,process=THISSERVER,*"), "traceSpecification", "newvalue").</td>
			</tr>
			<tr>
				<td>PrintInformation</td>
				<td><a href="PrintInformation" target="_blank">/PrintInformation</a>
				</td>
				<td>
					<ul>
						<li>type: The object to query for the information. For <a
							href="http://java.sun.com/j2se/1.5.0/docs/api/java/lang/System.html#getProperty(java.lang.String)"
							target="_blank">System.getProperty()</a>, use SystemProperty. For env vars, use EnvVar.
							Default=<%=PrintInformation.DEFAULT_TYPE%></li>
						<li>name: The name of the object to query.</li>
					</ul></td>
				<td>Prints information from the JVM. The object queried is
					based on [type]. For example, use [name]=was.install.root to get
					that system property, or [name]=user.dir to get the current working
					directory.</td>
			</tr>
			<tr>
				<td>Infinite Loop Servlet</td>
				<td><a href="InfiniteLoop" target="_blank">/InfiniteLoop</a>
				</td>
				<td>
					<ul>
						<li>threshold: After threshold/2 iterations, break. Maximum
							value=<%=Integer.MAX_VALUE%></li>
					</ul></td>
				<td>Simply loops infinitely on a servlet thread in a while loop
					with some trivial math operations. <b>WARNING</b>: You will lose
					this thread until the JVM is restarted and it will consume 100% of
					1 CPU/core!</td>
			</tr>
			<tr>
				<td>LoopWithIntervals</td>
				<td><a href="LoopWithIntervals" target="_blank">/LoopWithIntervals</a>
				</td>
				<td>None</td>
				<td>Loop through some methods and hold references to create
					some object graphs.</td>
			</tr>
			<tr>
				<td>ControlJVMTrace</td>
				<td><a href="ControlJVMTrace" target="_blank">/ControlJVMTrace</a>
				</td>
				<td>
					<ul>
						<li>suspend: Boolean. If true, call
							com.ibm.jvm.Trace.suspend().</li>
						<li>resume: Boolean. If true, call
							com.ibm.jvm.Trace.resume().</li>
						<li>set: If not blank, call com.ibm.jvm.Trace.set() with the
							specified value.</li>
						<li>snap: Boolean. If true, call com.ibm.jvm.Trace.snap().</li>
						<li>suspendThis: Boolean. If true, call
							com.ibm.jvm.Trace.suspendThis().</li>
						<li>resumeThis: Boolean. If true, call
							com.ibm.jvm.Trace.resumeThis().</li>
						<li>traceCustom: Boolean. If true, create a custom trace with
							com.ibm.jvm.Trace.registerApplication() as per the <a
							href="http://publib.boulder.ibm.com/infocenter/javasdk/v5r0/topic/com.ibm.java.doc.diagnostics.50/diag/tools/trace_app_imp_example.html"
							target="_blank">Example HelloWorld with application trace</a>.</li>
					</ul></td>
				<td>Depending on the options, makes various calls to <a
					href="http://publib.boulder.ibm.com/infocenter/javasdk/v5r0/topic/com.ibm.java.doc.diagnostics.50/diag/tools/trace_app_api.html"
					target="_blank">com.ibm.jvm.Trace.*</a>
				</td>
			</tr>
			<tr>
				<td>Simple</td>
				<td><a href="Simple" target="_blank">/Simple</a>
				</td>
				<td>
					<ul>
						<li>sessionname: Finds all cookies for the current request
							with this in their name, and prints their values. Default=<%=Simple.DEFAULT_SESSIONID%></li>
					</ul></td>
				<td>Prints some basic WAS information such as this request's
					session ID, the JVM's current working directory, operating system,
					etc.</td>
			</tr>
			<tr>
				<td>Exit JVM</td>
				<td><a href="SystemExit" target="_blank">/SystemExit</a>
				</td>
				<td>
					<ul>
						<li>code: The return code to pass to System.exit(). Defaults
							to 0.</li>
						<li>dohalt: Boolean. If true, call Runtime.halt(). If false,
							call System.exit(). Runtime.halt() skips any runtime shutdown
							hooks. Default true.</li>
					</ul></td>
				<td>Calls <a
					href="http://java.sun.com/javase/6/docs/api/java/lang/Runtime.html#halt(int)"
					target="_blank">Runtime.halt()</a> or <a
					href="http://java.sun.com/javase/6/docs/api/java/lang/System.html#exit(int)"
					target="_blank">System.exit()</a>. <b>WARNING:</b> This will
					terminate the JVM process (unless the call fails due to security)!</td>
			</tr>
			<tr>
				<td>JVM Time</td>
				<td><a href="SystemTime" target="_blank">/SystemTime</a>
				</td>
				<td>None</td>
				<td>Calls <a
					href="http://java.sun.com/javase/6/docs/api/java/lang/System.html#currentTimeMillis()"
					target="_blank">System.currentTimeMillis()</a>, <a
					href="http://java.sun.com/javase/6/docs/api/java/lang/System.html#nanoTime()"
					target="_blank">System.nanoTime()</a>, <a
					href="http://java.sun.com/j2se/1.4.2/docs/api/java/util/TimeZone.html#getDefault()"
					target="_blank">TimeZone.getDefault().toString()</a>, and <a
					href="http://java.sun.com/j2se/1.4.2/docs/api/java/util/Date.html"
					target="_blank">new Date(<br />System.currentTimeMillis()<br />).toString()</a>.</td>
			</tr>
			<tr>
				<td>Run Finalization</td>
				<td><a href="RunFinalization" target="_blank">/RunFinalization</a>
				</td>
				<td>None</td>
				<td>Calls <a
					href="http://java.sun.com/javase/6/docs/api/java/lang/Runtime.html#runFinalization()"
					target="_blank">Runtime.runFinalization()</a>.</td>
			</tr>
			<tr>
				<td>Sessions</td>
				<td><a href="Sessions" target="_blank">/Sessions</a>
				</td>
				<td><ul>
						<li>num: The number of attributes to set. Defaults to 0.</li>
						<li>prefix: The prefix of the attribute names.</li>
					</ul>
				</td>
				<td>Creates a session and adds some attributes.</td>
			</tr>
			<tr>
				<td>Other</td>
				<td><a href="Other" target="_blank">/Other</a>
				</td>
				<td>
					<ul>
						<li>action:
							<ul>
								<li>traceInstructions: Calls <a
									href="http://java.sun.com/javase/6/docs/api/java/lang/Runtime.html#traceInstructions(boolean)"
									target="_blank">Runtime.traceInstructions()</a>. Options:
									<ul>
										<li>value: Boolean to pass to the method.</li>
									</ul></li>
								<li>traceMethodCalls: Calls <a
									href="http://java.sun.com/javase/6/docs/api/java/lang/Runtime.html#traceMethodCalls(boolean)"
									target="_blank">Runtime.traceMethodCalls()</a>. Options:
									<ul>
										<li>value: Boolean to pass to the method.</li>
									</ul></li>
							</ul></li>
					</ul></td>
				<td>See Options.</td>
			</tr>
			<tr>
				<td>ZOSInfo</td>
				<td><a href="ZOSInfo" target="_blank">/ZOSInfo</a>
				</td>
				<td>None</td>
				<td>Calls com.ibm.websphere.runtime<br />.ServerName.getAsid()</td>
			</tr>
			<tr>
				<td>SetProperty</td>
				<td><a href="SetProperty" target="_blank">/SetProperty</a>
				</td>
				<td>
					<ul>
						<li>name: The name of the system property.</li>
						<li>value: The new value to set.</li>
					</ul></td>
				<td>Calls <a
					href="http://download.oracle.com/docs/cd/E17476_01/javase/1.5.0/docs/api/java/lang/System.html#setProperty(java.lang.String,%20java.lang.String)"
					target="_blank">System.setProperty</a>
				</td>
			</tr>
			<tr>
				<td>NativeLibraries</td>
				<td><a href="NativeLibraries" target="_blank">/NativeLibraries</a>
				</td>
				<td>
					<ul>
						<li>name: The name of the native library to load.</li>
						<li>system: Boolean. If true, call <a
							href="http://download.oracle.com/docs/cd/E17476_01/javase/1.5.0/docs/api/java/lang/System.html#loadLibrary(java.lang.String)"
							target="_blank">System.loadLibrary()</a>; otherwise, call <a
							href="http://download.oracle.com/docs/cd/E17476_01/javase/1.5.0/docs/api/java/lang/System.html#load(java.lang.String)"
							target="_blank">System.load()</a>.</li>
					</ul></td>
				<td>None</td>
			</tr>
			<tr>
				<td>Exceptions</td>
				<td><a href="Exceptions" target="_blank">/Exceptions</a>
				</td>
				<td>
					<ul>
						<li>throwconstructor: The type of constructor to call on the
							Exception. Either blank, message, inner, or bothinner</li>
						<li>throwmessage: The message to include as the Exception
							text if using a constructor that takes a message.</li>
						<li>throwAllTheWay: Boolean. If true, do not catch it but let
							it go all the way to the container. Default false.</li>
					</ul></td>
				<td>Test throwing exceptions. The types involved are <%=com.example.util.TestException.class.getCanonicalName()%>,
					<%=com.example.util.AnotherException.class.getCanonicalName()%>, and
					<%=com.example.util.SkipCatchException.class.getCanonicalName()%></td>
			</tr>
			<tr>
				<td>Allocate Native Memory</td>
				<td><a href="AllocateNativeMemory" target="_blank">/AllocateNative<br />Memory</a>
				</td>
				<td>
					<ul>
						<li>capacity: How many bytes to allocate. This may be rounded
							up to the page size.</li>
						<li>save: Boolean. If true, keep a static reference to the
							object so that it can't be GCed. Otherwise, make it elligible for
							GC immediately.</li>
						<li>clear: Boolean. If true, clear the list of saved
							allocations.</li>
					</ul></td>
				<td>Allocates a certain amount of native memory.</td>
			</tr>
			<tr>
				<td>Classloader Leak</td>
				<td><a href="ClassloaderLeak" target="_blank">/ClassloaderLeak<br />Memory</a>
				</td>
				<td>None</td>
				<td>Spawns a thread so that when the application is stopped,
					the application classloader will not be able to be cleaned up (i.e.
					it will be "leaked").</td>
			</tr>
			<tr>
				<td>Manage Dynacache</td>
				<td><a href="ManageDynacache" target="_blank">/ManageDynacache</a>
				</td>
				<td><ul>
						<li>action: create to create a new entry.</li>
						<li>instance: JNDI name of the cache.</li>
						<li>key</li>
						<li>value</li>
						<li>priority</li>
						<li>timetolive</li>
						<li>sharingpolicy</li>
					</ul></td>
				<td>N/A</td>
			</tr>
			<tr>
				<td>Large Response</td>
				<td><a href="LargeResponse" target="_blank">/LargeResponse</a>
				</td>
				<td><ul>
						<li>pattern: The string pattern to send back.</li>
						<li>count: How many times to send back the pattern.</li>
					</ul></td>
				<td>N/A</td>
			</tr>
			<tr>
				<td>Throw NullPointerException</td>
				<td><a href="ThrowNPE" target="_blank">/ThrowNPE</a>
				</td>
				<td>
					<ul>
						<li>None</li>
					</ul></td>
				<td>Throw a NullPointerException</td>
			</tr>
			<!--<tr>
				<td>Put MDB Message</td>
				<td><a href="PutMDBMessage" target="_blank">/PutMDBMessage</a>
				</td>
				<td><ul>
						<li>message: The string message. Defaults to Hello World plus the time</li>
					</ul></td>
				<td>twasdiag.ear comes with an MDB that listens to JMS queue at the
				JNDI name jms/testqueue1. It also needs a queue connection factory
				at JNDI name jms/testqcf1 to be able to put a message to this queue
				through this servlet. Finally, create an activation specification
				at JNDI name jms/testactivationspec1 to allow the MDB to receive the message.</td>
			</tr>-->
		</table>
		<p>While IBM welcomes any comments or suggestions that might help
			to improve this utility, it is not supported by IBM and is provided
			on an "as-is" basis. IBM may make updates if needed and as time
			permits.</p>
		<p>
			IBM, the IBM logo and ibm.com are trademarks of International
			Business Machines Corp., registered in many jurisdictions worldwide.
			Other product and service names might be trademarks of IBM or other
			companies. A current list of IBM trademarks is available on the Web
			at "Copyright and trademark information" at <a
				href="http://www.ibm.com/legal/copytrade.shtml" target="_blank">http://www.ibm.com/legal/copytrade.shtml</a>.
		</p>
	</div>
</body>
</html>
