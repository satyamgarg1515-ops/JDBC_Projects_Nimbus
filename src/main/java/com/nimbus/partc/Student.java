package com.nimbus.partc;

/**
 * Model Class: Student
 * Represents a Student entity with StudentID, Name, Department, and Marks
 * Part of MVC Architecture - MODEL layer
 */
public class Student {
    // Private instance variables
    private int studentID;
    private String name;
    private String department;
    private double marks;

    // Default Constructor
    public Student() {
    }

    // Parameterized Constructor
    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    // Getters and Setters
    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    // toString method for displaying student information
    @Override
    public String toString() {
        return "Student{" +
                "StudentID=" + studentID +
                ", Name='" + name + '\'' +
                ", Department='" + department + '\'' +
                ", Marks=" + marks +
                '}';
    }
}
