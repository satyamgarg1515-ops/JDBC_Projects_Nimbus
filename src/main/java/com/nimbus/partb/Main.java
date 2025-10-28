package com.nimbus.partb;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Part B – CRUD Operations on Product Table
 * Menu-driven JDBC program using PreparedStatement with transaction handling.
 * Database: nimbusdb (MySQL)
 * Table: Product(ProductID INT PK, ProductName VARCHAR, Price DECIMAL(10,2), Quantity INT)
 */
public class Main {

    // DB Config
    private static final String URL = "jdbc:mysql://localhost:3306/nimbusdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // SQL Statements (all Prepared)
    private static final String INSERT_SQL =
            "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL =
            "SELECT ProductID, ProductName, Price, Quantity FROM Product ORDER BY ProductID";
    private static final String UPDATE_SQL =
            "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
    private static final String DELETE_SQL =
            "DELETE FROM Product WHERE ProductID = ?";

    public static void main(String[] args) {
        printHeader();
        try (Scanner sc = new Scanner(System.in)) {
            // Load MySQL JDBC Driver (optional for newer JDBC but kept explicit)
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found. Ensure mysql-connector-j is on classpath.");
                return;
            }

            try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
                // Transaction handling for each operation group
                con.setAutoCommit(false);
                boolean exit = false;
                while (!exit) {
                    showMenu();
                    int choice = readInt(sc, "Enter your choice: ");
                    try {
                        switch (choice) {
                            case 1 -> createProduct(con, sc);
                            case 2 -> listProducts(con);
                            case 3 -> updateProduct(con, sc);
                            case 4 -> deleteProduct(con, sc);
                            case 5 -> {
                                // Manual commit point for batched ops
                                con.commit();
                                System.out.println("Transaction committed successfully.✅");
                            }
                            case 6 -> {
                                con.rollback();
                                System.out.println("Transaction rolled back.↩️");
                            }
                            case 0 -> {
                                exit = true;
                                // On exit, try committing pending changes
                                try {
                                    con.commit();
                                    System.out.println("Final commit done. Goodbye!");
                                } catch (SQLException e) {
                                    System.out.println("Commit failed on exit, attempting rollback...");
                                    con.rollback();
                                }
                            }
                            default -> System.out.println("Invalid option. Please try again.");
                        }
                        // If operation reached here without selecting 5 or 6, do not auto-commit.
                        // User controls commit/rollback explicitly via menu options.
                    } catch (SQLException ex) {
                        System.out.println("Operation failed: " + ex.getMessage());
                        try {
                            con.rollback();
                            System.out.println("Rolled back due to error.");
                        } catch (SQLException rb) {
                            System.out.println("Rollback failed: " + rb.getMessage());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("================= Product CRUD Menu =================");
        System.out.println("1. Create Product");
        System.out.println("2. View All Products");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. Commit Transaction");
        System.out.println("6. Rollback Transaction");
        System.out.println("0. Exit");
        System.out.println("=====================================================");
    }

    private static void printHeader() {
        System.out.println("=====================================================");
        System.out.println(" Part B – JDBC CRUD for Product (PreparedStatement + TX)");
        System.out.println(" Database: nimbusdb | Table: Product");
        System.out.println("=====================================================");
    }

    // CREATE
    private static void createProduct(Connection con, Scanner sc) throws SQLException {
        System.out.println("-- Create Product --");
        int id = readInt(sc, "Enter ProductID (int): ");
        System.out.print("Enter ProductName: ");
        String name = sc.nextLine();
        double price = readDouble(sc, "Enter Price (decimal): ");
        int qty = readInt(sc, "Enter Quantity (int): ");

        try (PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(4, qty);
            int rows = ps.executeUpdate();
            System.out.println(rows + " product(s) inserted. Pending commit.");
        }
    }

    // READ
    private static void listProducts(Connection con) throws SQLException {
        System.out.println("-- Product List --");
        try (PreparedStatement ps = con.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {
            System.out.printf("%-10s %-25s %-10s %-10s%n", "ProductID", "ProductName", "Price", "Quantity");
            System.out.println("-----------------------------------------------------");
            boolean any = false;
            while (rs.next()) {
                any = true;
                System.out.printf("%-10d %-25s %-10.2f %-10d%n",
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"));
            }
            if (!any) System.out.println("No products found.");
        }
    }

    // UPDATE
    private static void updateProduct(Connection con, Scanner sc) throws SQLException {
        System.out.println("-- Update Product --");
        int id = readInt(sc, "Enter ProductID to update: ");
        System.out.print("Enter new ProductName: ");
        String name = sc.nextLine();
        double price = readDouble(sc, "Enter new Price: ");
        int qty = readInt(sc, "Enter new Quantity: ");

        try (PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, qty);
            ps.setInt(4, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println(rows + " product(s) updated. Pending commit.");
            else
                System.out.println("No product found with ProductID: " + id);
        }
    }

    // DELETE
    private static void deleteProduct(Connection con, Scanner sc) throws SQLException {
        System.out.println("-- Delete Product --");
        int id = readInt(sc, "Enter ProductID to delete: ");
        try (PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println(rows + " product(s) deleted. Pending commit.");
            else
                System.out.println("No product found with ProductID: " + id);
        }
    }

    // Helpers for safe numeric input
    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Invalid integer. Try again.");
            }
        }
    }

    private static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }
}
