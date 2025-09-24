package com.ecommerce.service;

import com.ecommerce.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//import static com.ecommerce.service.DatabaseService;

public class DataService {
    public static User findUserbyName(String username) throws SQLException {
        DatabaseService dbService = DatabaseService.getInstance();
        System.out.println("Connecting "+dbService.getConnection());
        Connection conn = dbService.getConnection(); // Do NOT close this connection
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            System.out.println("Executing query to find user: " + username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("is_admin")
                );
                System.out.println("User found: " + user.getUsername());
                return user;
            } else {
                System.out.println("No user found with username: " + username);
            }
        } catch (SQLException e) {
            System.err.println("Error finding user: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public static User findUser(String username, String password) throws SQLException {
        DatabaseService dbService = DatabaseService.getInstance();
        System.out.println("Connecting "+dbService.getConnection());
        Connection conn = dbService.getConnection(); // Do NOT close this connection
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            System.out.println("Executing query to find user: " + username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("is_admin")
                );
                System.out.println("User found: " + user.getUsername());
                return user;
            } else {
                System.out.println("No user found with username: " + username);
            }
        } catch (SQLException e) {
            System.err.println("Error finding user: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public static boolean updatePassword(String username, String newPassword) throws SQLException {
        DatabaseService dbService = DatabaseService.getInstance();
        System.out.println("Connecting "+dbService.getConnection());
        Connection conn = dbService.getConnection();
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // true if at least one row was updated
        }
    }
    public static Customer findCustomer(String customer_name, String phone) throws SQLException {
        DatabaseService dbService = DatabaseService.getInstance();
        System.out.println("Connecting "+dbService.getConnection());
        Connection conn = dbService.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM customers WHERE customer_name = ? AND phone = ?")) {
            stmt.setString(1, customer_name);
            stmt.setString(2, phone);
            System.out.println("Executing query to find user: " + customer_name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer user = new Customer(
                        rs.getString("id"),
                        rs.getString("customer_name"),
                        rs.getString("phone")
                );
                System.out.println("User found: " + user.getName());
                return user;
            } else {
                System.out.println("No user found with username: " + customer_name);
            }
        } catch (SQLException e) {
            System.err.println("Error finding user: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void addProduct(Product product) {
        DatabaseService dbService = DatabaseService.getInstance();
        try (Connection conn = dbService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO products (product_id, product_name, price, available_pieces) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getAvailablePieces());

            stmt.executeUpdate();
            System.out.println("Product added successfully: " + product.getName());

        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public static List<Product> getProducts() {
        DatabaseService dbService = DatabaseService.getInstance();
        List<Product> products = new ArrayList<>();
        try (Connection conn = dbService.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getInt("available_pieces")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Hey Error retrieving products: " + e.getMessage());
        }
        return products;
    }
    public static List<Order> getOrdersByCustomer(String customerName) {
        DatabaseService dbService = DatabaseService.getInstance();
        List<Order> orders = new ArrayList<>();

        String sql = """
        SELECT o.order_id, o.customer_id, o.product_id, o.quantity,
               o.total_price, o.order_date
        FROM orders o
        JOIN customers c ON o.customer_id = c.id
        WHERE c.customer_name = ?
    """;

        try (Connection conn = dbService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(new Order(
                        rs.getString("order_id"),
                        rs.getInt("customer_id"),
                        rs.getString("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("order_date")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public static List<Order> getOrdersByCustomerName(String customerName, String phoneNumber) {
        DatabaseService dbService = DatabaseService.getInstance();
        List<Order> orders = new ArrayList<>();

        String sql = """
        SELECT o.order_id, o.customer_id, o.product_id, o.quantity,
               o.total_price, o.order_date
        FROM orders o
        JOIN customers c ON o.customer_id = c.id
        WHERE c.customer_name = ? and c.phone = ?
    """;

        try (Connection conn = dbService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerName);
            stmt.setString(2, phoneNumber);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(new Order(
                        rs.getString("order_id"),
                        rs.getInt("customer_id"),
                        rs.getString("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("order_date")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }



    public static Product getProductById(String productId) {
        DatabaseService dbService = DatabaseService.getInstance();
        try (Connection conn = dbService.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products WHERE product_id = ?")) {
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        rs.getInt("available_pieces")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting product by ID: " + e.getMessage());
        }
        return null;
    }
    public static boolean processOrder(String productId, int quantity, String customerName, String phoneNumber) {
        try (Connection conn = DatabaseService.getInstance().getConnection()) {
            System.out.println("Processing order for product ID: " + productId + ", quantity: " + quantity);
            conn.setAutoCommit(false); // Start transaction

            int customerId = -1;
            PreparedStatement findCustomer = conn.prepareStatement(
                    "SELECT id FROM customers WHERE customer_name = ? AND phone = ?");
            findCustomer.setString(1, customerName);
            findCustomer.setString(2, phoneNumber);
            ResultSet customerRs = findCustomer.executeQuery();

            if (customerRs.next()) {
                customerId = customerRs.getInt("id");
            } else {
                PreparedStatement insertCustomer = conn.prepareStatement(
                        "INSERT INTO customers (customer_name, phone) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                insertCustomer.setString(1, customerName);
                insertCustomer.setString(2, phoneNumber);
                insertCustomer.executeUpdate();

                ResultSet generatedKeys = insertCustomer.getGeneratedKeys();
                if (generatedKeys.next()) {
                    customerId = generatedKeys.getInt(1);
                } else {
                    System.err.println("Failed to insert or retrieve customer ID.");
                    conn.rollback();
                    return false;
                }
            }

            // 2️⃣ Check product availability
            PreparedStatement selectStmt = conn.prepareStatement(
                    "SELECT available_pieces, price FROM products WHERE product_id = ?");
            selectStmt.setString(1, productId);
            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                System.err.println("Product not found: " + productId);
                conn.rollback();
                return false;
            }

            int availablePieces = rs.getInt("available_pieces");
            double price = rs.getDouble("price");

            if (availablePieces < quantity) {
                System.err.println("Not enough stock for product ID: " + productId);
                conn.rollback();
                return false;
            }

            // 3️⃣ Update stock
            PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE products SET available_pieces = available_pieces - ? WHERE product_id = ?");
            updateStmt.setInt(1, quantity);
            updateStmt.setString(2, productId);
            updateStmt.executeUpdate();

            // 4️⃣ Insert into orders
            String orderId = "ORD" + System.currentTimeMillis();
            PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO orders (order_id, customer_id, product_id, quantity, total_price, order_date) VALUES (?, ?, ?, ?, ?, ?)");
            insertStmt.setString(1, orderId);
            insertStmt.setInt(2, customerId);
            insertStmt.setString(3, productId);
            insertStmt.setInt(4, quantity);
            insertStmt.setDouble(5, price * quantity);
            LocalDate today = LocalDate.now();
            insertStmt.setString(6, today.toString());
            insertStmt.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Draft> getDrafts() {
        List<Draft> drafts = new ArrayList<>();

        try (Connection conn = DatabaseService.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM draft_orders");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                drafts.add(new Draft(
                        rs.getString("draft_id"),
                        rs.getString("customer_name"),
                        rs.getString("phone"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching drafts: " + e.getMessage());
        }

        return drafts;
    }




    public static List<Customer> getCustomersFromOrders() {
        DatabaseService dbService = DatabaseService.getInstance();
        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT DISTINCT s.id AS customer_id, s.customer_name, s.phone " +
                "FROM customers s " +
                "JOIN orders o ON s.id = o.customer_id";

        try (Connection conn = dbService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("customer_id");
                String name = rs.getString("customer_name");
                String phone = rs.getString("phone");
                customers.add(new Customer(id, name, phone));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
        }
        return customers;
    }


    public static boolean removeOrderQuantity(Order order, int quantityToRemove) {
        DatabaseService dbService = DatabaseService.getInstance();
        try (Connection conn = dbService.getConnection()) {
            conn.setAutoCommit(false); // Begin transaction

            // 1. Get current quantity from the order
            PreparedStatement orderStmt = conn.prepareStatement(
                    "SELECT quantity FROM orders WHERE order_id = ?"
            );
            orderStmt.setString(1, order.getOrderId());
            ResultSet rs = orderStmt.executeQuery();

            if (!rs.next()) {
                conn.rollback();
                return false;
            }

            int currentQty = rs.getInt("quantity");

            // 2. Decide whether to update or delete the order
            if (quantityToRemove >= currentQty) {
                // Delete order
                PreparedStatement deleteStmt = conn.prepareStatement(
                        "DELETE FROM orders WHERE order_id = ?"
                );
                deleteStmt.setString(1, order.getOrderId());
                deleteStmt.executeUpdate();
            } else {
                // Update order with new quantity
                PreparedStatement updateOrderStmt = conn.prepareStatement(
                        "UPDATE orders SET quantity = quantity - ? WHERE order_id = ?"
                );
                updateOrderStmt.setInt(1, quantityToRemove);
                updateOrderStmt.setString(2, order.getOrderId());
                updateOrderStmt.executeUpdate();
            }

            // 3. Increase available stock in products table
            PreparedStatement updateProductStmt = conn.prepareStatement(
                    "UPDATE products SET available_pieces = available_pieces + ? WHERE product_id = ?"
            );
            updateProductStmt.setInt(1, quantityToRemove);
            updateProductStmt.setString(2, order.getProductId());
            updateProductStmt.executeUpdate();

            conn.commit(); // Success
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<OrderDetails> getAllOrderDetailsinARange(String from,String to) {
        DatabaseService dbService = DatabaseService.getInstance();
        List<OrderDetails> list = new ArrayList<>();

       // String query = ;

        try (Connection conn = dbService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT o.order_id, c.customer_name, c.phone AS phone_number, " +
                             "o.product_id, p.product_name AS product_name, o.quantity, " +
                             "o.total_price AS total_bill, o.order_date " +
                             "FROM orders o " +
                             "JOIN customers c ON c.id = o.customer_id " +
                             "JOIN products p ON o.product_id = p.product_id " +
                             "WHERE o.order_date between ? and ?")) {

            stmt.setString(1, from);
            stmt.setString(2, to);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new OrderDetails(
                            rs.getString("order_id"),
                            rs.getString("customer_name"),
                            rs.getString("phone_number"),
                            rs.getString("product_id"),
                            rs.getString("product_name"),
                            rs.getInt("quantity"),
                            rs.getDouble("total_bill"),
                            rs.getString("order_date")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching order details: " + e.getMessage());
        }

        return list;
    }

    public static List<OrderDetails> getAllOrderDetails() {
        DatabaseService dbService = DatabaseService.getInstance();
        List<OrderDetails> list = new ArrayList<>();

        String query = "SELECT o.order_id, c.customer_name, c.phone AS phone_number, " +
                "o.product_id, p.product_name AS product_name, o.quantity, " +
                "o.total_price AS total_bill, o.order_date " +
                "FROM orders o " +
                "JOIN customers c ON c.id = o.customer_id " +
                "JOIN products p ON o.product_id = p.product_id";
        try (Connection conn = dbService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new OrderDetails(
                        rs.getString("order_id"),
                        rs.getString("customer_name"),
                        rs.getString("phone_number"),
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_bill"),
                        rs.getString("order_date")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching order details: " + e.getMessage());
        }
        return list;
    }

    public static void updateProductAvailablePieces(String productId, int newAvailablePieces) {
        DatabaseService dbService = DatabaseService.getInstance();
        String sql = "UPDATE products SET available_pieces = ? WHERE product_id = ?";
        try (Connection conn = dbService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newAvailablePieces);
            stmt.setString(2, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean saveDraft(String username, String productId, int quantity, String customerName, String phoneNumber)
    {   DatabaseService dbService = DatabaseService.getInstance();
        try (Connection conn = dbService.getConnection()) {
            String draftId = "DRAFT" + System.currentTimeMillis();

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO draft_orders (draft_id, customer_name, phone ,product_id,quantity) "
                            + "VALUES (?, ?, ?, ?, ?)");

            stmt.setString(1, draftId);
            stmt.setString(2, customerName);
            stmt.setString(3, phoneNumber);
            stmt.setInt(4, Integer.parseInt(productId));
            stmt.setString(5, String.valueOf(quantity));


            return stmt.executeUpdate() > 0;
        }
        catch(SQLException e) {
            System.err.println("Error saving draft: " + e.getMessage());
            return false;
        }
    }
    public static boolean deleteDraft(String draftId) {
        try (Connection conn = DatabaseService.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM draft_orders WHERE draft_id=?")) {
            stmt.setString(1, draftId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static int getOrCreateCustomer(String name, String phone, Connection conn) throws SQLException {
        // 1. Check if customer already exists
        PreparedStatement check = conn.prepareStatement(
                "SELECT id FROM customers WHERE customer_name = ? AND phone = ?");
        check.setString(1, name);
        check.setString(2, phone);
        ResultSet rs = check.executeQuery();
        if (rs.next()) {
            return rs.getInt("id"); // existing customer ID
        }

        // 2. Insert new customer
        PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO customers (customer_name, phone) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        insert.setString(1, name);
        insert.setString(2, phone);
        insert.executeUpdate();

        ResultSet keys = insert.getGeneratedKeys();
        keys.next();
        return keys.getInt(1); // new customer ID
    }
    public static double getProductPrice(int productId, Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT price FROM products WHERE product_id = ?");
        stmt.setInt(1, productId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble("price");
        }
        throw new SQLException("Product not found: " + productId);
    }


    public static boolean confirmDraft(String draftId) {
        DatabaseService dbService = DatabaseService.getInstance();

        try (Connection conn = dbService.getConnection()) {

            // 1. Get the draft row
            PreparedStatement getDraft = conn.prepareStatement(
                    "SELECT customer_name, phone, product_id, quantity FROM draft_orders WHERE draft_id = ?");
            getDraft.setString(1, draftId);
            ResultSet rs = getDraft.executeQuery();

            if (!rs.next()) {
                // no draft found
                return false;
            }

            String customerName = rs.getString("customer_name");
            String phone = rs.getString("phone");
            int productId = rs.getInt("product_id");
            int quantity = rs.getInt("quantity");

            // 2. Get or create customer ID
            int customerId = getOrCreateCustomer(customerName, phone, conn);

            // 3. Get product price
            double price = getProductPrice(productId, conn);
            double totalPrice = price * quantity;

            // 4. Insert into orders table
            String orderId = "ORD" + System.currentTimeMillis();
            PreparedStatement insertOrder = conn.prepareStatement(
                    "INSERT INTO orders (order_id, customer_id, product_id, quantity, total_price, order_date) " +
                            "VALUES (?, ?, ?, ?, ?, datetime('now'))"
            );
            insertOrder.setString(1, orderId);
            insertOrder.setInt(2, customerId);
            insertOrder.setInt(3, productId);
            insertOrder.setInt(4, quantity);
            insertOrder.setDouble(5, totalPrice);

            insertOrder.executeUpdate();
            PreparedStatement updateStock = conn.prepareStatement(
                    "UPDATE products SET available_pieces = available_pieces - ? WHERE product_id = ?"
            );
            updateStock.setInt(1, quantity);
            updateStock.setInt(2, productId);
            updateStock.executeUpdate();

            // 5. Delete the draft
            PreparedStatement deleteDraft = conn.prepareStatement(
                    "DELETE FROM draft_orders WHERE draft_id = ?");
            deleteDraft.setString(1, draftId);
            deleteDraft.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println("Error converting draft to order: " + e.getMessage());
            return false;
        }
    }

    // Returns true if a product with the given id OR name already exists
    public static boolean productExists(String id, String name) {
        DatabaseService dbService = DatabaseService.getInstance();
        try (Connection conn = dbService.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM products WHERE product_id = ? OR product_name = ?")) {
            stmt.setString(1, id);
            stmt.setString(2, name);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Error checking product existence: " + e.getMessage());
            return true; // fail-safe: treat as existing
        }
    }



}