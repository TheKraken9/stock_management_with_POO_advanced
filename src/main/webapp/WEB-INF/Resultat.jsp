<%@ page import="java.util.ArrayList" %>
<%@ page import="com.project.stock2.model.Mouvement" %><%--
  Created by IntelliJ IDEA.
  User: thekraken9
  Date: 2023-11-23
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<Mouvement> mouvements = (ArrayList<Mouvement>) request.getAttribute("vrai");
%>
<html>
<head>
    <title>Resultat</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <td>Article</td>
        <td>Quantité</td>
        <td>Date d'entré</td>
        <td>Magasin</td>
        <td>Prix</td>
    </tr>
    </thead>
    <tbody>
    <% for (Mouvement demande : mouvements) { %>
    <tr>
        <td><%= demande.getId_article() %></td>
        <td><%= demande.getQuantite() %></td>
        <td><%= demande.getDate_mouvement() %></td>
        <td><%= demande.getId_magasin() %></td>
        <td><%= demande.getPrix_unitaire() %></td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>
