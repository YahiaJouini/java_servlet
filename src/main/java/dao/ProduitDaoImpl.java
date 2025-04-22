package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import metier.SingletonConnection;
import metier.Produit;

public class ProduitDaoImpl implements IProduitDao {

    @Override
    public Produit save(Produit p) {
        String query = "INSERT INTO produit(nomP, prix) VALUES (?, ?)";
        try (Connection conn = SingletonConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNomProduit());
            ps.setDouble(2, p.getPrix());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setIdProduit(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving product", e);
        }
        return p;
    }

    @Override
    public List<Produit> searchByNom(String mc) {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM produit WHERE nomP LIKE ?";
        
        try (Connection conn = SingletonConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "%" + mc + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Produit p = new Produit();
                    p.setIdProduit(rs.getLong("idP"));
                    p.setNomProduit(rs.getString("nomP"));
                    p.setPrix(rs.getDouble("prix"));
                    produits.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving products by keyword", e);
        }
        return produits;
    }

    @Override
    public Produit getProduit(Long id) {
        Produit p = null;
        String query = "SELECT * FROM produit WHERE idP = ?";

        try (Connection conn = SingletonConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Produit();
                    p.setIdProduit(rs.getLong("idP"));
                    p.setNomProduit(rs.getString("nomP"));
                    p.setPrix(rs.getDouble("prix"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving product by ID", e);
        }
        return p;
    }

    @Override
    public Produit updateProduit(Produit p) {
        String query = "UPDATE produit SET nomP = ?, prix = ? WHERE idP = ?";
        
        try (Connection conn = SingletonConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, p.getNomProduit());
            ps.setDouble(2, p.getPrix());
            ps.setLong(3, p.getIdProduit());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating product", e);
        }
        return p;
    }

    @Override
    public void deleteProduit(Long id) {
        String query = "DELETE FROM produit WHERE idP = ?";
        
        try (Connection conn = SingletonConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product", e);
        }
    }

    public List<Produit> findAll() {
        List<Produit> produits = new ArrayList<>();
        String query = "SELECT * FROM produit";

        try (Connection conn = SingletonConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Produit p = new Produit();
                p.setIdProduit(rs.getLong("idP"));
                p.setNomProduit(rs.getString("nomP"));
                p.setPrix(rs.getDouble("prix"));
                produits.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all products", e);
        }

        return produits;
    }
}
