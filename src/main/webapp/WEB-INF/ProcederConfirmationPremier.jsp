<%@ page import="com.project.stock2.model.Mouvement" %><%--
  Created by IntelliJ IDEA.
  User: thekraken9
  Date: 2023-11-23
  Time: 09:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Mouvement mouvement = (Mouvement) request.getAttribute("mvt");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form method="get" action="ProcederConfirmationServlet">
        <input type="hidden" name="id" value="<%= mouvement.getId() %>">
        <input type="hidden" name="action" value="ok">
        <input type="date" name="date">
        <input type="submit" value="Confirmer">
    </form>
</body>
</html>
