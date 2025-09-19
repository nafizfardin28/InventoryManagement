package com.ecommerce.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {

    private static DatabaseService instance;
    private Connection conn;

    private static final String DB_URL = "jdbc:sqlite:/home/fardin1528/Desktop/InventoryManagement/shop_management.db";

    // Private constructor
    private DatabaseService() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to SQLite database successfully.");
            initializeTables(); // ensure tables exist
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Thread-safe singleton instance
    public static DatabaseService getInstance() {
        if (instance == null) {
            synchronized (DatabaseService.class) {
                if (instance == null) {
                    instance = new DatabaseService();
                }
            }
        }
        return instance;
    }

    // Get connection
    public Connection getConnection() throws SQLException{

        return DriverManager.getConnection(DB_URL);
    }

    // Close connection
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    // Ensure tables exist (without inserting any sample data)
    private void initializeTables() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, " +
                "is_admin INTEGER NOT NULL)";

        String createCustomersTable = "CREATE TABLE IF NOT EXISTS customers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "customer_name TEXT NOT NULL, " +
                "phone TEXT NOT NULL)";

        String createProductsTable = "CREATE TABLE IF NOT EXISTS products (" +
                "product_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "product_name TEXT NOT NULL, " +
                "price REAL NOT NULL, " +
                "available_pieces INTEGER NOT NULL DEFAULT 0)";

        String createOrdersTable = "CREATE TABLE IF NOT EXISTS orders (" +
                "order_id TEXT PRIMARY KEY, " +
                "customer_id INTEGER NOT NULL, " +
                "product_id INTEGER NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "total_price REAL NOT NULL, " +
                "order_date TEXT NOT NULL DEFAULT (datetime('now')), " +
                "FOREIGN KEY(customer_id) REFERENCES customers(id), " +
                "FOREIGN KEY(product_id) REFERENCES products(product_id))";



        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createCustomersTable);
            stmt.execute(createProductsTable);
            stmt.execute(createOrdersTable);
            System.out.println("Tables checked/created successfully.");
        } catch (SQLException e) {
            System.err.println("Error initializing tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
