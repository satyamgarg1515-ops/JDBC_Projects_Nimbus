package com.nimbus.parta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Part A - JDBC Connection and Fetching Data
 * This class demonstrates basic JDBC connectivity to MySQL
 * and fetches all records from the Employee table.
 * 
 * Database Details:
 * - Database Name: nimbusdb
 * - Table: Employee (EmpID, Name, Salary)
 * - Username: root
 * - Password: 1234
 * 
 * @author Satyam Garg
 */
public class Main {
    
    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/nimbusdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";
    
    public static void main(String[] args) {
        
        // Display program header
        System.out.println("=".repeat(60));
        System.out.println("Part A - JDBC Connection and Fetching Employee Data");
        System.out.println("=".repeat(60));
        System.out.println();
        
        // Connection, Statement, and ResultSet objects
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            // Step 1: Load MySQL JDBC Driver (Optional for JDBC 4.0+)
            System.out.println("[INFO] Loading MySQL JDBC Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[SUCCESS] MySQL JDBC Driver loaded successfully!");
            System.out.println();
            
            // Step 2: Establish connection to the database
            System.out.println("[INFO] Connecting to database: nimbusdb...");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("[SUCCESS] Connected to nimbusdb successfully!");
            System.out.println();
            
            // Step 3: Create a Statement object to execute SQL queries
            statement = connection.createStatement();
            
            // Step 4: Execute SQL query to fetch all Employee records
            String sqlQuery = "SELECT EmpID, Name, Salary FROM Employee";
            System.out.println("[INFO] Executing SQL Query: " + sqlQuery);
            resultSet = statement.executeQuery(sqlQuery);
            System.out.println("[SUCCESS] Query executed successfully!");
            System.out.println();
            
            // Step 5: Display the fetched data
            System.out.println("=".repeat(60));
            System.out.println("EMPLOYEE RECORDS FROM DATABASE");
            System.out.println("=".repeat(60));
            System.out.printf("%-10s %-25s %-15s%n", "EmpID", "Name", "Salary");
            System.out.println("-".repeat(60));
            
            // Iterate through the ResultSet and display each row
            int recordCount = 0;
            while (resultSet.next()) {
                int empId = resultSet.getInt("EmpID");
                String name = resultSet.getString("Name");
                double salary = resultSet.getDouble("Salary");
                
                System.out.printf("%-10d %-25s %-15.2f%n", empId, name, salary);
                recordCount++;
            }
            
            System.out.println("-".repeat(60));
            System.out.println("Total Records Fetched: " + recordCount);
            System.out.println("=".repeat(60));
            
        } catch (ClassNotFoundException e) {
            // Handle JDBC Driver not found exception
            System.err.println("[ERROR] MySQL JDBC Driver not found!");
            System.err.println("Error Details: " + e.getMessage());
            e.printStackTrace();
            
        } catch (SQLException e) {
            // Handle SQL-related exceptions
            System.err.println("[ERROR] Database error occurred!");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Error Message: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
            // Step 6: Close all database resources in reverse order
            try {
                if (resultSet != null) {
                    resultSet.close();
                    System.out.println();
                    System.out.println("[INFO] ResultSet closed successfully.");
                }
                if (statement != null) {
                    statement.close();
                    System.out.println("[INFO] Statement closed successfully.");
                }
                if (connection != null) {
                    connection.close();
                    System.out.println("[INFO] Database connection closed successfully.");
                }
            } catch (SQLException e) {
                System.err.println("[ERROR] Error while closing database resources!");
                e.printStackTrace();
            }
        }
        
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("Program Execution Completed");
        System.out.println("=".repeat(60));
    }
}
