package web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ProduitDaoImpl;
import metier.Produit;
import metier.user;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "controlleur", urlPatterns = {"/acceuil", "/editProduit", "/deleteProduit", "/addProduit"})
public class Controlleur extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/acceuil":
                // Login
                String l = request.getParameter("login");
                String p = request.getParameter("password");

                user user1 = new user(l, p);

                if (user1.verifier()) {
                    ProduitDaoImpl dao = new ProduitDaoImpl();
                    List<Produit> produits = dao.searchByNom("");

                    request.setAttribute("produits", produits);
                    request.setAttribute("user", l);
                    this.getServletContext().getRequestDispatcher("/view.jsp").forward(request, response);
                } else {
                    this.getServletContext().getRequestDispatcher("/acceuil.html").forward(request, response);
                }
                break;

            case "/deleteProduit":
                doPostDelete(request, response);
                break;

            case "/editProduit":
                doPostEdit(request, response);
                break;
            case "/addProduit":
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
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
        case "/acceuil":
            ProduitDaoImpl dao = new ProduitDaoImpl();
            String searchTerm = request.getParameter("searchTerm"); // Récupérer le terme de recherche
            List<Produit> produits = dao.searchByNom(searchTerm != null ? searchTerm : ""); // Recherche avec ou sans terme
            request.setAttribute("produits", produits);
            this.getServletContext().getRequestDispatcher("/searchProduit.jsp").forward(request, response);
            break;


            case "/editProduit":
                doGetEdit(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
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
