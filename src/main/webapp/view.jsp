<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="metier.Produit" %>

<%
String user = (String) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}
%>


<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Accueil</title>
  <style>
    body {
      font-family: 'Helvetica Neue', sans-serif;
      background-color: #ffffff;
      margin: 0;
      padding: 40px 20px;
    }

    .container {
      max-width: 800px;
      margin: 0 auto;
      padding: 30px;
      background-color: #ffffff;
      border: 3px solid #000;
    }

    h1, h2 {
      text-align: center;
      text-transform: uppercase;
      letter-spacing: 2px;
      font-size: 24px;
      margin-bottom: 30px;
    }

    h2 {
      font-size: 20px;
      margin-top: 40px;
    }

    form {
      text-align: center;
      margin-bottom: 40px;
    }

    input[type="text"], input[type="number"] {
      padding: 12px;
      margin: 8px;
      width: 200px;
      border: 2px solid #000;
      font-size: 16px;
      background-color: #fff;
    }

    input[type="text"]:focus, input[type="number"]:focus {
      outline: none;
      border-color: #555;
    }

    label {
      font-weight: bold;
      text-transform: uppercase;
      font-size: 14px;
      margin-right: 5px;
    }

    .btn {
      padding: 12px 20px;
      border: 2px solid #000;
      background-color: #000;
      color: white;
      cursor: pointer;
      font-weight: bold;
      text-transform: uppercase;
      letter-spacing: 1px;
      transition: background-color 0.3s, color 0.3s;
    }

    .btn:hover {
      background-color: #fff;
      color: #000;
    }

    .edit-btn {
      background-color: #000;
      border-color: #000;
    }

    .delete-btn {
      background-color: #000;
      border-color: #000;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 30px;
      border: 2px solid #000;
    }

    th, td {
      border: 2px solid #000;
      padding: 15px;
      text-align: center;
    }

    th {
      background-color: #000;
      color: #fff;
      text-transform: uppercase;
      font-weight: bold;
      letter-spacing: 1px;
    }

    .search-container {
      text-align: center;
      margin-bottom: 40px;
      border-bottom: 2px solid #000;
      padding-bottom: 30px;
    }

    .search-container input[type="text"] {
      padding: 12px;
      width: 300px;
      border: 2px solid #000;
    }
    
    .search-container button {
      padding: 12px 20px;
      border: 2px solid #000;
      background-color: #000;
      color: white;
      cursor: pointer;
      text-transform: uppercase;
      letter-spacing: 1px;
      font-weight: bold;
      transition: background-color 0.3s, color 0.3s;
    }
    
    .search-container button:hover {
      background-color: #fff;
      color: #000;
    }
  </style>
</head>
<body>
  <div class="container">
    <h1>Liste des Produits</h1>

    <!-- Formulaire de recherche -->
    <div class="search-container">
      <form action="searchProduit" method="get">
        <input type="text" name="nomProduit" placeholder="Rechercher un produit par nom" required>
        <button type="submit" class="btn">Rechercher</button>
      </form>
    </div>

    <h2>Ajouter un Produit</h2>
    <form action="addProduit" method="post">
      <label for="nomProduit">Nom :</label>
      <input type="text" name="nomProduit" id="nomProduit" required>

      <label for="prix">Prix :</label>
      <input type="number" step="0.01" name="prix" id="prix" required>

      <button type="submit" class="btn">Ajouter</button>
    </form>

    <table>
      <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prix</th>
        <th>Actions</th>
      </tr>

      <%
        // Récupérer la liste de produits depuis la requête
        List<Produit> produits = (List<Produit>) request.getAttribute("produits");
        if (produits == null) {
          produits = new ArrayList<>();
        }

        if (!produits.isEmpty()) {
          for (Produit p : produits) {
      %>
      <tr>
        <td><%= p.getIdProduit() %></td>
        <td><%= p.getNomProduit() %></td>
        <td><%= p.getPrix() %></td>
        <td>
          <form action="editProduit" method="get" style="display:inline; margin: 0 5px;">
            <input type="hidden" name="id" value="<%= p.getIdProduit() %>">
            <button type="submit" class="btn edit-btn">Éditer</button>
          </form>
          <form action="deleteProduit" method="post" style="display:inline; margin: 0 5px;">
            <input type="hidden" name="id" value="<%= p.getIdProduit() %>">
            <button type="submit" class="btn delete-btn" onclick="return confirm('Confirmer la suppression ?')">Supprimer</button>
          </form>
        </td>
      </tr>
      <% 
          }
        } else {
      %>
      <tr>
        <td colspan="4">Aucun produit trouvé.</td>
      </tr>
      <% } %>
    </table>
  </div>
</body>
</html>