package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import metier.SingletonConnection;
import metier.user;

public class UserDao {

    private Connection connection;

    public UserDao() {
        this.connection = SingletonConnection.getConnection();
    }

    // Save new user
    public void save(user u) {
        try {
            String sql = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getMotdepasse());
            ps.setString(3, u.getRole());  // Include role when saving
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Verify user credentials and retrieve role
    public user verifyUser(String login, String password) {
        user verifiedUser = null;
        try {
            String query = "SELECT email, password, role FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, login);
            stmt.setString(2, password);  // Make sure passwords are stored securely (hashed)
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                String role = rs.getString("role");
                verifiedUser = new user(email, password);
                verifiedUser.setRole(role);
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return verifiedUser;
    }

    
    // Check if email exists
    public boolean isEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Returns true if the email exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Returns false if the email does not exist
    }

    // Fetch all users (optional for your admin panel)
    public List<user> getAllUsers() {
        List<user> users = new ArrayList<>();
        String query = "SELECT email, role FROM users";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                user u = new user();
                u.setLogin(rs.getString("email"));
                u.setRole(rs.getString("role"));
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Fetch users filtered by email
    public List<user> searchUsersByEmail(String emailFilter) {
        List<user> users = new ArrayList<>();
        String query = "SELECT email, role FROM users WHERE email LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + emailFilter + "%");  // Use LIKE for partial matches
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                user u = new user();
                u.setLogin(rs.getString("email"));
                u.setRole(rs.getString("role"));
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
