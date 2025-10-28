package com.nimbus.partc;

import java.util.List;
import java.util.Scanner;

/**
 * View Class: StudentView
 * Provides menu-based user interface for Student Management System
 * Part of MVC Architecture - VIEW layer
 */
public class StudentView {
    private StudentController controller;
    private Scanner scanner;

    // Constructor
    public StudentView() {
        this.controller = new StudentController();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display the main menu and handle user input
     */
    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n========================================");
            System.out.println("  STUDENT MANAGEMENT SYSTEM - Part C");
            System.out.println("========================================");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    viewStudentById();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    System.out.println("\nExiting Student Management System...");
                    System.out.println("Thank you for using the application!");
                    break;
                default:
                    System.out.println("\nInvalid choice! Please try again.");
            }
        } while (choice != 6);
        
        scanner.close();
    }

    /**
     * Add a new student to the database
     */
    private void addStudent() {
        System.out.println("\n--- Add New Student ---");
        
        System.out.print("Enter Student ID: ");
        int studentID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        
        System.out.print("Enter Marks: ");
        double marks = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        Student student = new Student(studentID, name, department, marks);
        
        if (controller.addStudent(student)) {
            System.out.println("\n✓ Student added successfully!");
        } else {
            System.out.println("\n✗ Failed to add student. Please check if Student ID already exists.");
        }
    }

    /**
     * View all students from the database
     */
    private void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        
        List<Student> students = controller.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found in the database.");
        } else {
            System.out.println("\n" + String.format("%-12s %-25s %-20s %-10s", 
                "Student ID", "Name", "Department", "Marks"));
            System.out.println("------------------------------------------------------------------------");
            
            for (Student student : students) {
                System.out.println(String.format("%-12d %-25s %-20s %-10.2f",
                    student.getStudentID(),
                    student.getName(),
                    student.getDepartment(),
                    student.getMarks()));
            }
            
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Total Students: " + students.size());
        }
    }

    /**
     * View a specific student by ID
     */
    private void viewStudentById() {
        System.out.println("\n--- View Student by ID ---");
        
        System.out.print("Enter Student ID: ");
        int studentID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        Student student = controller.getStudentById(studentID);
        
        if (student != null) {
            System.out.println("\nStudent Details:");
            System.out.println("----------------------------");
            System.out.println("Student ID  : " + student.getStudentID());
            System.out.println("Name        : " + student.getName());
            System.out.println("Department  : " + student.getDepartment());
            System.out.println("Marks       : " + student.getMarks());
            System.out.println("----------------------------");
        } else {
            System.out.println("\n✗ Student not found with ID: " + studentID);
        }
    }

    /**
     * Update an existing student's information
     */
    private void updateStudent() {
        System.out.println("\n--- Update Student ---");
        
        System.out.print("Enter Student ID to update: ");
        int studentID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        // Check if student exists
        Student existingStudent = controller.getStudentById(studentID);
        if (existingStudent == null) {
            System.out.println("\n✗ Student not found with ID: " + studentID);
            return;
        }
        
        System.out.println("\nCurrent Details: " + existingStudent);
        System.out.println("\nEnter new details:");
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        
        System.out.print("Enter Marks: ");
        double marks = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        Student updatedStudent = new Student(studentID, name, department, marks);
        
        if (controller.updateStudent(updatedStudent)) {
            System.out.println("\n✓ Student updated successfully!");
        } else {
            System.out.println("\n✗ Failed to update student.");
        }
    }

    /**
     * Delete a student from the database
     */
    private void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        
        System.out.print("Enter Student ID to delete: ");
        int studentID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        // Check if student exists
        Student student = controller.getStudentById(studentID);
        if (student == null) {
            System.out.println("\n✗ Student not found with ID: " + studentID);
            return;
        }
        
        System.out.println("\nStudent Details: " + student);
        System.out.print("Are you sure you want to delete this student? (yes/no): ");
        String confirmation = scanner.nextLine();
        
        if (confirmation.equalsIgnoreCase("yes")) {
            if (controller.deleteStudent(studentID)) {
                System.out.println("\n✓ Student deleted successfully!");
            } else {
                System.out.println("\n✗ Failed to delete student.");
            }
        } else {
            System.out.println("\nDeletion cancelled.");
        }
    }

    /**
     * Main method to run the Student Management System
     */
    public static void main(String[] args) {
        StudentView view = new StudentView();
        view.displayMenu();
    }
}
