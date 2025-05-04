<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="metier.Produit" %>
<%@ page import="dao.ProduitDaoImpl" %>

<%-- Authentication Check --%>
<%
String user = (String) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accueil</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .product { border: 1px solid #ddd; padding: 10px; margin-bottom: 10px; }
        .logout { float: right; }
        .logout-btn {
            background: none;
            border: none;
            color: blue;
            text-decoration: underline;
            cursor: pointer;
            padding: 0;
        }
    </style>
</head>
<body>
    <div class="logout">
        <form action="logout" method="post">
            <button type="submit" class="logout-btn">Logout</button>
        </form>
    </div>
    
    <h1>Welcome <%= user %></h1>
    
    <h2>Product List</h2>
    
    <% 
    // Check if the 'produits' attribute exists in the request, if not, fetch products
    List<Produit> produits = (List<Produit>) request.getAttribute("produits");
    
    // If no products are set in the request, fetch them from database
    if (produits == null || produits.isEmpty()) {
        ProduitDaoImpl produitDao = new ProduitDaoImpl();
        produits = produitDao.findAll();
        request.setAttribute("produits", produits);
    }
    
    if (produits != null && !produits.isEmpty()) { 
    %>
        <% for (Produit produit : produits) { %>
            <div class="product">
                <h3><%= produit.getNomProduit() %></h3>
                <p>Price: <%= produit.getPrix() %> €</p>
            </div>
        <% } %>
    <% } else { %>
        <p>No products available.</p>
    <% } %>
</body>
</html>