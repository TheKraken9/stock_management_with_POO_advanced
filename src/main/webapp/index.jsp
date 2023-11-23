<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Etat de stock</title>
</head>
<body>
<h1>Etat de stock</h1>
<br/>
<a href="NewMouvementServlet">Ajouter une nouvelle sortie</a><br>
<a href="NewMouvementEntreServlet">Ajouter une nouvelle entrée</a><br>
<a href="ListVraiServlet">Liste des vrais mouvement</a><br>
<a href="ConfirmationServlet">Confirmer une sortie</a>
<form method="get" action="EtatDeStockServlet">
    <p>Le :<input type="date" name="date1">
    Au :<input type="date" name="date2"></p>
    <input type="text" name="id_article" placeholder="id article">
    <input type="text" name="id_magasin" placeholder="id magasin">
    <input type="submit" value="Etat de stock">
</form>
</body>
</html>