<%@ page import="com.project.stock2.model.Mouvement" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: thekraken9
  Date: 2023-11-22
  Time: 01:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<Mouvement> demandes = (ArrayList<Mouvement>) request.getAttribute("demandes");
%>
<html>
<head>
    <title>Confirmation des sorties</title>
</head>
<body>
<h1>Confirmation des sorties</h1>
<table>
    <thead>
    <tr>
        <td>Article</td>
        <td>Quantité</td>
        <td>Date d'entré</td>
        <td>Magasin</td>
        <td>Actions</td>
    </tr>
    </thead>
    <tbody>
    <% for (Mouvement demande : demandes) { %>
    <tr>
        <td><%= demande.getArticle() %></td>
        <td><%= demande.getQuantite() %></td>
        <td><%= demande.getDate_mouvement() %></td>
        <td><%= demande.getMagasin() %></td>
        <td><a href="ProcederConfirmationServletPremier?id=<%= demande.getId() %>&action=ok">Confirmer</a> | <a href="ModifConfirmationServlet?id=<%= demande.getId() %>&action=modifier">Modifier</a></td>
    </tr>
    <% } %>
    </tbody>
</table>

</body>
</html>
