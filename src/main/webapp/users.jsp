<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users Management</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .search-box { margin-bottom: 20px; }
        .logout { float: right; }
    </style>
</head>
<body>
    <div class="logout">
        <form action="logout" method="post">
            <button type="submit">Logout</button>
        </form>
    </div>
    
    <h1>Users Management</h1>
    
    <div class="search-box">
        <form action="users" method="get">
            <input type="text" name="email" placeholder="Search by email..." value="${param.email}">
            <button type="submit">Search</button>
            <a href="users">Show All</a>
        </form>
    </div>
    
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Email</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                    <td>
                        <a href="editUser?id=${user.id}">Edit</a>
                        <a href="deleteUser?id=${user.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <div style="margin-top: 20px;">
        <a href="addUser.jsp">Add New User</a>
        <a href="acceuil" style="margin-left: 20px;">Back to Products</a>
    </div>
</body>
</html>