package com.nimbus.partc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller Class: StudentController
 * Handles all JDBC database operations for Student Management System
 * Part of MVC Architecture - CONTROLLER layer
 * Uses PreparedStatement for all CRUD operations
 */
public class StudentController {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/nimbusdb";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // Method to establish database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * CREATE Operation: Add a new student to the database
     * @param student Student object to be inserted
     * @return true if insertion is successful, false otherwise
     */
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Set parameters using PreparedStatement
            pstmt.setInt(1, student.getStudentID());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getDepartment());
            pstmt.setDouble(4, student.getMarks());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    /**
     * READ Operation: Get a student by StudentID
     * @param studentID ID of the student to retrieve
     * @return Student object if found, null otherwise
     */
    public Student getStudentById(int studentID) {
        String sql = "SELECT * FROM Student WHERE StudentID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentID);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Student(
                    rs.getInt("StudentID"),
                    rs.getString("Name"),
                    rs.getString("Department"),
                    rs.getDouble("Marks")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving student: " + e.getMessage());
        }
        return null;
    }

    /**
     * READ Operation: Get all students from the database
     * @return List of all Student objects
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("StudentID"),
                    rs.getString("Name"),
                    rs.getString("Department"),
                    rs.getDouble("Marks")
                );
                students.add(student);
            }
            
        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
        return students;
    }

    /**
     * UPDATE Operation: Update an existing student's information
     * @param student Student object with updated information
     * @return true if update is successful, false otherwise
     */
    public boolean updateStudent(Student student) {
        String sql = "UPDATE Student SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Set parameters using PreparedStatement
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDepartment());
            pstmt.setDouble(3, student.getMarks());
            pstmt.setInt(4, student.getStudentID());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    /**
     * DELETE Operation: Delete a student from the database
     * @param studentID ID of the student to be deleted
     * @return true if deletion is successful, false otherwise
     */
    public boolean deleteStudent(int studentID) {
        String sql = "DELETE FROM Student WHERE StudentID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }
}
