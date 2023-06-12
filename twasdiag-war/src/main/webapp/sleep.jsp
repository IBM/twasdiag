<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.example.Sleep"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sleep</title>
</head>
<body>
Running...
<%
int duration = Sleep.doSleep(request);
out.println("Finished sleeping for " + duration + "ms");
%>
</body>
</html>