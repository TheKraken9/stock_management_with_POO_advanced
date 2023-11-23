<%@ page import="java.util.ArrayList" %>
<%@ page import="com.project.stock2.model.EtatDeStock" %>
<%@ page import="java.sql.Date" %><%--
  Created by IntelliJ IDEA.
  User: thekraken9
  Date: 2023-11-22
  Time: 08:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<EtatDeStock> etatDeStocks1 = (ArrayList<EtatDeStock>) request.getAttribute("etatDeStocks1");
    ArrayList<EtatDeStock> etatDeStocks2 = (ArrayList<EtatDeStock>) request.getAttribute("etatDeStocks2");
    String date1 = (String) request.getAttribute("date1");
    String date2 = (String) request.getAttribute("date2");
%>
<html>
<head>
    <title>Etat de stock</title>
</head>
<body>
<h1>Etat de stock du <%= date1 %></h1>
<table>
    <thead>
    <tr>
        <td>Produit</td>
        <td>Magasin</td>
        <td>Reste</td>
        <td>Unite</td>
        <td>PUMP</td>
        <td>Total</td>
    </tr>
    </thead>
    <tbody>
    <% for (EtatDeStock ett1 : etatDeStocks1) { %>
        <tr>
            <td><%= ett1.getArticle() %></td>
            <td><%= ett1.getMagasin() %></td>
            <td><%= ett1.getQuantite() %></td>
            <td><%= ett1.getUnite() %></td>
            <td><%= ett1.getPrix() %></td>
            <td><%= ett1.getTotal() %></td>
        </tr>
    <% } %>
    </tbody>
</table>

<h1>Etat de stock <%= date2 %></h1>
<table>
    <thead>
    <tr>
        <td>Produit</td>
        <td>Magasin</td>
        <td>Reste</td>
        <td>Unite</td>
        <td>PUMP</td>
        <td>Total</td>
    </tr>
    </thead>
    <tbody>
    <% for (EtatDeStock ett2 : etatDeStocks2) { %>
        <tr>
            <td><%= ett2.getArticle() %></td>
            <td><%= ett2.getMagasin() %></td>
            <td><%= ett2.getQuantite() %></td>
            <td><%= ett2.getUnite() %></td>
            <td><%= ett2.getPrix() %></td>
            <td><%= ett2.getTotal() %></td>
        </tr>
    <% } %>
    </tbody>
</table>

</body>
</html>
