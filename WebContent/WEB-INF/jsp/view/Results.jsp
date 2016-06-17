<%@page import="com.mkwillis.mailserver.beans.SendResult, java.io.PrintWriter, java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Results</title>
</head>
<body>

Result: 

<%
SendResult result = (SendResult)request.getAttribute("result");

if (result.isSuccess())
	out.println("Success!");
else
	out.println("Failure :-(");
%>

<br />
<br />

<%
for (String msg: result.getMessages()){
	out.println("\t" + msg);
%>
<br />
<%
}
%>

<br />
<br />
<a href="/MailServer/mail">Send another mail</a>
</body>
</html>