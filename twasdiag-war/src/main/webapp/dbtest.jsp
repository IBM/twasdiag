<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%@ page import="com.example.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Database Test</title>
</head>
<body>
<%
String jndiName = request.getParameter("jndiName");
if (jndiName != null && jndiName.length() > 0) {

	// Default = 0 =>  don't sleep holding connection
	String sleep = request.getParameter("sleep");
	long sleepTime = (sleep != null && sleep.length() > 0) ? Long.parseLong(sleep) : 0;

	// Default = false =>  don't accumulate
	boolean accumulateResults = "yes".equals(request.getParameter("accumulate"));

	// I like parsing LTC better than global tran even if it's a less-common term
	boolean useLTC = "yes".equals(request.getParameter("LTC"));

	String query = request.getParameter("query");
	out.println("Executing the following query on JNDI name " + jndiName + "...<p /><blockquote>" + query + "</blockquote><br />");
	out.flush();
	try {
		DatabaseTest db = new DatabaseTest(jndiName, accumulateResults, sleepTime, !useLTC);
		if ("yes".equals(request.getParameter("insert"))) {
			int count = db.executeUpdate(query);
			out.println("Rows updated: " + count);
		} else {
			String numThreads = request.getParameter("threads");
			int threads = 1;
			if (numThreads != null && numThreads.length() > 0) {
				threads = Integer.parseInt(numThreads);
			}
			int count = db.executeQueryRowCount(query, threads);
			out.println("Rows selected by query x threads: " + count);
			out.println("Issued query on # of threads = " + threads + ".  See logs for results");
		}
	} finally {
		out.println("<hr />");
	}
}
%>
<form action="dbtest.jsp" method="get">
JNDI name: <input type="text" id="jndiName" name="jndiName" /><br />
Query to execute:<br />
<textarea rows="5" cols="40" id="query" name="query"></textarea><p />
<p><input type="checkbox" name="insert" id="insert" value="yes" />&nbsp;<label for="insert">Insert, Update, or Delete</label></p>
<p><input type="checkbox" name="accumulate" id="accumulate" value="yes" />&nbsp;<label for="accumulate">For Select, accumulate into an ArrayList</label></p>
<p><input type="checkbox" name="LTC" id="LTC" value="yes" />&nbsp;<label for="LTC">Execute without global transaction (in LTC)</label></p>
<p>Number of Threads (default=1, and always use 1 for non-Select)<input type="text" name="threads" size="5"/><p/>
<p>Time to sleep holding connection after doing work (in msec)<input type="text" name="sleep" size="10"/><p/>
<input type="submit" value="Execute" />
</form>
</body>
</html>