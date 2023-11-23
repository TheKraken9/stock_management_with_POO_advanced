<%@ page import="com.project.stock2.model.Mouvement" %><%--
  Created by IntelliJ IDEA.
  User: thekraken9
  Date: 2023-11-22
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Mouvement mouvement = (Mouvement) request.getAttribute("mouvement");
  String message = (String) request.getAttribute("message");
%>
<html>
<head>
    <title>Modification</title>
</head>
<body>
<h1>Veuillez modifier les informations</h1>
<small><%= message %></small>
  <h4>Vos anciens informations</h4>
<form action="ModifConfirmationServlet" method="post">
  <input type="hidden" name="id" value="<%= mouvement.getId() %>">
  <input type="text" name="id_article" value="<%= mouvement.getId_article() %>">
  <input type="text" name="id_magasin" value="<%= mouvement.getId_magasin() %>">
  <input type="text" name="quantite" value="<%= mouvement.getQuantite() %>">
  <input type="date" id="date" name="date" value="<%= mouvement.getDate_mouvement() %>">
  <input type="submit" value="Modifier">
</form>
</body>
</html>
