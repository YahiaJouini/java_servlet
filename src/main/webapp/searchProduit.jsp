
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Résultats de la Recherche</title>
    <style>
        /* Généralités */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Arial', sans-serif;
        }

        body {
            background-color: #f4f4f9;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            text-align: center;
            padding: 20px;
        }

        h2 {
            font-size: 2rem;
            color: #333;
            margin-bottom: 20px;
            font-weight: 600;
        }

        p {
            font-size: 1.2rem;
            color: #888;
            margin-top: 20px;
        }

        /* Conteneur de la recherche */
        .search-container {
            width: 100%;
            max-width: 600px;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 40px;
        }

        .search-container form {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .search-container input {
            width: 80%;
            padding: 10px;
            font-size: 1rem;
            border: 2px solid #ddd;
            border-radius: 4px;
            outline: none;
        }

        .search-container input:focus {
            border-color: #007bff;
        }

        .search-container button {
            padding: 10px 20px;
            font-size: 1rem;
            color: white;
            background-color: #007bff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .search-container button:hover {
            background-color: #0056b3;
        }

        /* Liste des produits */
        ul {
            list-style: none;
            padding: 0;
        }

        ul li {
            background-color: #fff;
            padding: 15px;
            margin: 10px 0;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            font-size: 1.2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        ul li:hover {
            background-color: #f1f1f1;
        }

        /* Message quand aucun produit n'est trouvé */
        .no-results p {
            color: #e74c3c;
            font-weight: bold;
        }
    </style>
</head>
<body>

<!-- Conteneur de la recherche -->
<div class="search-container">
    <form action="acceuil" method="get">
        <input type="text" name="searchTerm" placeholder="Rechercher par nom de produit" value="${param.searchTerm}">
        <button type="submit">Rechercher</button>
    </form>
</div>

<!-- Affichage des résultats de recherche -->
<!-- Affichage des résultats de recherche -->
<h2>Résultats de la recherche</h2>
<%@ page import="java.util.List" %>
<%@ page import="metier.Produit" %>

<%-- Vérification des résultats de la recherche --%>
<%
    Object obj = request.getAttribute("produits");
    if (obj instanceof List) {
        List<Produit> produits = (List<Produit>) obj;
        if (produits != null && !produits.isEmpty()) {
%>
            <ul>
                <% for (Produit produit : produits) { %>
                    <li><%= produit.getNomProduit() %> - <%= produit.getPrix() %>€</li>
                <% } %>
            </ul>
<%
        } else {
%>
            <div class="no-results">
                <p>Aucun produit trouvé pour la recherche "<%= request.getParameter("searchTerm") %>".</p>
            </div>
<%
        }
    }
%>


</body>
</html>



