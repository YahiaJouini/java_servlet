package web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ProduitDaoImpl;
import dao.UserDao;
import metier.Produit;
import metier.user;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "controlleur", urlPatterns = {"/login", "/register", "/editProduit", "/deleteProduit", "/addProduit", "/logout", "/acceuil", "/users"})
public class Controlleur extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/login":
                handleLogin(request, response);
                break;

            case "/register":
                handleRegister(request, response);
                break;

            case "/addProduit":
                handleAddProduit(request, response);
                break;

            case "/editProduit":
                doPostEdit(request, response);
                break;

            case "/deleteProduit":
                doPostDelete(request, response);
                break;

            case "/logout":
                request.getSession().invalidate();
                response.sendRedirect("login.jsp");
                break;
            case "/acceuil":
                String user = (String) request.getSession().getAttribute("user");
                if (user == null) {
                    response.sendRedirect("login.jsp");
                    return;
                }
                
                // Load products and forward to acceuil.jsp
                ProduitDaoImpl dao = new ProduitDaoImpl();
                List<Produit> produits = dao.searchByNom("");
                request.setAttribute("produits", produits);
                this.getServletContext().getRequestDispatcher("/acceuil.jsp").forward(request, response);
                break;
                
            case "/users":
                handleUsersPage(request, response);
                break;
                
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/login":
                ProduitDaoImpl dao = new ProduitDaoImpl();
                String searchTerm = request.getParameter("searchTerm");
                List<Produit> produits = dao.searchByNom(searchTerm != null ? searchTerm : "");
                request.setAttribute("produits", produits);
                this.getServletContext().getRequestDispatcher("/acceuil.jsp").forward(request, response);
                break;

            case "/editProduit":
                doGetEdit(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void handleUsersPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userRole = (String) request.getSession().getAttribute("role");

        // Ensure the user is an admin
        if (userRole == null || !"admin".equals(userRole)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String emailFilter = request.getParameter("email");  // Get email filter from request parameter
        UserDao userDao = new UserDao();

        // Fetch users filtered by email
        List<user> users;
        if (emailFilter != null && !emailFilter.isEmpty()) {
            users = userDao.searchUsersByEmail(emailFilter);  // Assuming searchUsersByEmail is a method you can add in UserDao
        } else {
            users = userDao.getAllUsers();  // Assuming getAllUsers fetches all users
        }

        request.setAttribute("users", users);
        this.getServletContext().getRequestDispatcher("/users.jsp").forward(request, response);
    }
    
    private String getUserRole(String login) {
        String role = null;
        try {
            String query = "SELECT role FROM users WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                role = rs.getString("role");  // Assume role column exists in users table
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }


    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String l = request.getParameter("login");
        String p = request.getParameter("password");

        UserDao userDao = new UserDao();
        user user1 = userDao.verifyUser(l, p);  // Verify the user by login and password

        if (user1 != null) {
            // Set the session attributes for user and role
            request.getSession().setAttribute("user", l);
            request.getSession().setAttribute("role", user1.getRole());

            // Redirect to the appropriate page based on user role
            if ("admin".equals(user1.getRole())) {
                response.sendRedirect(request.getContextPath() + "/view.jsp");  // Admin is redirected to view.jsp
            } else {
                response.sendRedirect(request.getContextPath() + "/acceuil.jsp");  // Normal user is redirected to acceuil.jsp
            }
        } else {
            // If login fails, redirect back to login.jsp with an error message
            request.setAttribute("error", "Invalid login or password!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }



    
    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String l = request.getParameter("login");
        String p = request.getParameter("password");

        // Create a new user object
        user newUser = new user(l, p);

        // Set default role
        newUser.setRole("user");  // Default role is "user"

        UserDao userDao = new UserDao();

        // Check if email already exists
        if (userDao.isEmailExists(l)) {
            // Email already exists, show error message
            request.setAttribute("error", "Email already exists!");
            this.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Save the new user
        userDao.save(newUser);

        // After registration, redirect to the login page with a success message
        request.setAttribute("message", "Registration successful! Please login.");
        this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }


    private void handleAddProduit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomProduit = request.getParameter("nomProduit");
        double prix = Double.parseDouble(request.getParameter("prix"));

        Produit produit = new Produit();
        produit.setNomProduit(nomProduit);
        produit.setPrix(prix);

        ProduitDaoImpl daoAdd = new ProduitDaoImpl();
        daoAdd.save(produit);

        List<Produit> produits = daoAdd.searchByNom("");
        request.setAttribute("produits", produits);
        this.getServletContext().getRequestDispatcher("/view.jsp").forward(request, response);
    }

    protected void doGetEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long idProduit = Long.parseLong(request.getParameter("id"));
        ProduitDaoImpl dao = new ProduitDaoImpl();
        Produit produit = dao.getProduit(idProduit);

        request.setAttribute("produit", produit);
        this.getServletContext().getRequestDispatcher("/editProduit.jsp").forward(request, response);
    }

    protected void doPostEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long idProduit = Long.parseLong(request.getParameter("id"));
        String nomProduit = request.getParameter("nomProduit");
        double prix = Double.parseDouble(request.getParameter("prix"));

        ProduitDaoImpl dao = new ProduitDaoImpl();
        Produit produit = dao.getProduit(idProduit);
        produit.setNomProduit(nomProduit);
        produit.setPrix(prix);
        dao.updateProduit(produit);

        List<Produit> produits = dao.searchByNom("");
        request.setAttribute("produits", produits);
        this.getServletContext().getRequestDispatcher("/view.jsp").forward(request, response);
    }

    protected void doPostDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long idProduit = Long.parseLong(request.getParameter("id"));
        ProduitDaoImpl dao = new ProduitDaoImpl();
        dao.deleteProduit(idProduit);

        List<Produit> produits = dao.searchByNom("");
        request.setAttribute("produits", produits);
        this.getServletContext().getRequestDispatcher("/view.jsp").forward(request, response);
    }
}
