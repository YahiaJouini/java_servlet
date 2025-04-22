package metier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
    private static Connection connection;

    static {
        try {
            // Charger le driver JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Créer la connexion à la base de données
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db_j2ee", "root", "");

            // Tester la connexion (optionnel)
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection à la base de données réussie.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de connexion à la base de données", e);
        }
    }

    // Retourner la connexion unique
    public static Connection getConnection() {
        try {
            // Vérifier si la connexion est fermée et la recréer si nécessaire
            if (connection == null || connection.isClosed()) {
                // Si la connexion est fermée, la recréer
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_j2ee", "root", "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la vérification de la connexion", e);
        }
        return connection;
    }

    // Optionnel : Méthode pour fermer la connexion
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
