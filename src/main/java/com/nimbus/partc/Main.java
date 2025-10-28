package com.nimbus.partc;

/**
 * Part C - Student Management System (MVC Architecture)
 * Main entry point for the Student Management System
 * Follows MVC pattern with:
 * - Model: Student.java (Entity class)
 * - View: StudentView.java (UI/Menu interface)
 * - Controller: StudentController.java (JDBC CRUD operations)
 * 
 * Database: nimbusdb
 * Table: Student (StudentID, Name, Department, Marks)
 * Uses PreparedStatement for all database operations
 * 
 * @author Satyam Garg
 */
public class Main {
    
    public static void main(String[] args) {
        // Display welcome message
        System.out.println("========================================");
        System.out.println("  JDBC Projects - Part C");
        System.out.println("  Student Management System");
        System.out.println("  MVC Architecture Implementation");
        System.out.println("========================================\n");
        
        // Initialize and start the Student Management System
        // StudentView handles the menu and user interactions
        StudentView view = new StudentView();
        view.displayMenu();
        
        System.out.println("\n========================================");
        System.out.println("  Application Terminated Successfully");
        System.out.println("========================================");
    }
}
