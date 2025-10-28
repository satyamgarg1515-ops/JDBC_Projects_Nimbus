# JDBC_Applications

## Project Description
This is a comprehensive JDBC project demonstrating database connectivity and operations using MySQL.

## Project Structure
```
JDBC_Applications/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── nimbus/
│                   ├── parta/          # JDBC Connection and Data Fetching
│                   ├── partb/          # CRUD Operations on Product Table
│                   └── partc/          # Student Management System (MVC)
├── pom.xml
└── README.md
```

## Parts Overview

### Part A - JDBC Connection and Fetching Data
- Connects to MySQL database (nimbusdb)
- Fetches and displays all Employee records
- Table: Employee (EmpID, Name, Salary)

### Part B - CRUD Operations on Product Table
- Menu-driven application
- Full CRUD operations on Product table
- PreparedStatement implementation
- Transaction handling (commit/rollback)
- Table: Product (ProductID, ProductName, Price, Quantity)

### Part C - Student Management System (MVC Architecture)
- Follows Model-View-Controller pattern
- Model: Student class
- View: Menu-based interface
- Controller: JDBC operations
- Table: Student (StudentID, Name, Department, Marks)

## Database Configuration
- Database: nimbusdb
- Username: root
- Password: 1234
- Driver: MySQL Connector/J (mysql-connector-j)

## Requirements
- Java 11 or higher
- MySQL Server
- Maven

## How to Run
1. Ensure MySQL is running
2. Create database 'nimbusdb'
3. Configure database credentials if different
4. Run Maven build: `mvn clean install`
5. Execute individual parts as needed

## Author
Satyam Garg
