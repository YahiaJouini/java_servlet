package metier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class user {
    private String login;
    private String motdepasse;
    private String role;  // User role

    // Constructor
    public user(String login, String motdepasse) {
        this.login = login;
        this.motdepasse = motdepasse;
    }
    
    public user() {}

    // Getters and Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Verify user login credentials and retrieve role
    public boolean verifier() {
        boolean isValid = false;

        // SQL query to verify the user and get the role
        String query = "SELECT role FROM users WHERE login = ? AND password = ?";
        
        try (Connection connection = SingletonConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            // Set the parameters (login and password)
            stmt.setString(1, this.login);
            stmt.setString(2, this.motdepasse);  // Ensure the password is properly hashed in the DB

            // Execute the query
            ResultSet rs = stmt.executeQuery();
            
            // If a matching user is found, set the role and return true
            if (rs.next()) {
                this.role = rs.getString("role");  // Get the role from the result
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }
}
