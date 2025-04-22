<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="metier.Produit" %>
<%
Produit produit = (Produit) request.getAttribute("produit");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>Modifier Produit</title>
<style>
body {
  font-family: 'Helvetica Neue', sans-serif;
  background-color: #ffffff;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.container {
  background-color: white;
  padding: 40px;
  width: 100%;
  max-width: 500px;
  border: 3px solid #000;
}

h2 {
  text-align: center;
  color: #000;
  margin-bottom: 30px;
  text-transform: uppercase;
  letter-spacing: 2px;
  font-size: 24px;
}

label {
  display: block;
  font-size: 14px;
  margin-bottom: 10px;
  color: #000;
  font-weight: bold;
  text-transform: uppercase;
}

input[type="text"], input[type="number"] {
  width: 100%;
  padding: 12px;
  margin: 0 0 25px 0;
  border: 2px solid #000;
  font-size: 16px;
  box-sizing: border-box;
  background-color: #fff;
}

input[type="text"]:focus, input[type="number"]:focus {
  border-color: #555;
  outline: none;
}

button {
  background-color: #000;
  color: white;
  border: 2px solid #000;
  padding: 14px 20px;
  font-size: 16px;
  cursor: pointer;
  width: 100%;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: bold;
  transition: background-color 0.3s, color 0.3s;
}

button:hover {
  background-color: #fff;
  color: #000;
}
</style>
</head>
<body>
<div class="container">
<h2>Modifier Produit</h2>
<form action="editProduit" method="post">
<input type="hidden" name="id" value="<%= produit.getIdProduit() %>">
<label>Nom du produit:</label>
<input type="text" name="nomProduit" value="<%= produit.getNomProduit() %>" required>

<label>Prix:</label>
<input type="number" step="0.01" name="prix" value="<%= produit.getPrix() %>" required>

<button type="submit">Enregistrer</button>
</form>
</div>
</body>
</html>