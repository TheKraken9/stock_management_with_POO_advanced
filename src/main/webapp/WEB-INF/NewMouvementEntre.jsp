<%@ page import="java.util.ArrayList" %>
<%@ page import="com.project.stock2.model.Article" %>
<%@ page import="com.project.stock2.model.Magasin" %>
<%@ page import="com.project.stock2.model.UniteEquivalence" %><%--
  Created by IntelliJ IDEA.
  User: thekraken9
  Date: 2023-11-22
  Time: 01:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<Article> articless = (ArrayList<Article>) request.getAttribute("articles");
    ArrayList<Magasin> magasinss = (ArrayList<Magasin>) request.getAttribute("magasins");
    ArrayList<UniteEquivalence> uniteEquivalences = (ArrayList<UniteEquivalence>) request.getAttribute("uniteEquivalences");
%>
<html>
<head>
    <title>Nouveau mouvement</title>
</head>
<body>
<h1>votre nouvelle mouvement(entrée)</h1>
<form action="NewMouvementEntreServlet" method="post">
    <label for="date">Date</label>
    <input type="date" name="date_mouvement" id="date" required>
    <label for="libelle">Libelle</label>
    <select name="id_article" id="libelle">
        <% for (Article article : articless) { %>
        <option value="<%= article.getId() %>"><%= article.getLibelle() %>(<%= article.getId() %>)</option>
        <% } %>
    </select>
    <label for="magasin">Magasin</label>
    <select id="magasin" name="id_magasin">
        <% for (Magasin magasin : magasinss) { %>
        <option value="<%= magasin.getId() %>"><%= magasin.getLibelle() %></option>
        <% } %>
    </select>
    <label>Votre Unite</label>
    <select for="unite_equivalence" name="id_unite_equivalence">
        <% for (UniteEquivalence uniteEquivalence : uniteEquivalences) { %>
        <option value="<%= uniteEquivalence.getId() %>"><%= uniteEquivalence.getLibelle() %>(<%= uniteEquivalence.getId_article() %>)</option>
        <% } %>
    </select>
    <label for="quantite">Quantité</label>
    <input type="number" name="quantite" id="quantite" required>

    <label for="prix">Prix</label>
    <input type="number" name="prix" id="prix" required>
    <input type="submit" value="Ajouter">
</form>
</body>
</html>
