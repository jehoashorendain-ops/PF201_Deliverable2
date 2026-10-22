package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:stockwise.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("❌ Database connection error: " + e.getMessage());
        }
        return conn;
    }

    public static void initializeDatabase() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "full_name TEXT NOT NULL, " +
                "role TEXT NOT NULL, " +
                "account_status TEXT NOT NULL);";

        String createProductsTable = "CREATE TABLE IF NOT EXISTS Products (" +
                "product_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "product_name TEXT NOT NULL, " +
                "category TEXT NOT NULL, " +
                "price REAL NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "reorder_level INTEGER NOT NULL);";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createProductsTable);

            // Insert default Admin if none exists
            String insertDefaultAdmin = "INSERT OR IGNORE INTO Users (user_id, username, password, full_name, role, account_status) " +
                    "VALUES (1, 'admin', 'admin123', 'Edmer Orendain', 'System Administrator', 'ACTIVE');";
            stmt.execute(insertDefaultAdmin);

            System.out.println("✅ Database tables initialized successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error initializing tables: " + e.getMessage());
        }
    }
}