<%--
  Created by IntelliJ IDEA.
  User: thekraken9
  Date: 2023-11-23
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String message = (String) request.getAttribute("message");
%>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1><%= message %></h1>
</body>
</html>
