CREATE DATABASE payXpert;
use payXpert;

CREATE TABLE Employee (
    EmployeeID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    DateOfBirth DATE NOT NULL,
    Gender ENUM('Male', 'Female', 'Other') NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    PhoneNumber VARCHAR(15) UNIQUE NOT NULL,
    Address TEXT,
    Position VARCHAR(50) NOT NULL,
    JoiningDate DATE NOT NULL,
    TerminationDate DATE NULL
);

desc employee;

-- Payroll Table
CREATE TABLE Payroll (
    PayrollID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT,
    PayPeriodStartDate DATE NOT NULL,
    PayPeriodEndDate DATE NOT NULL,
    BasicSalary DECIMAL(10,2) NOT NULL,
    OvertimePay DECIMAL(10,2) DEFAULT 0.00,
    Deductions DECIMAL(10,2) DEFAULT 0.00,
    NetSalary DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID) ON DELETE CASCADE
);
desc payroll;

-- Tax Table
CREATE TABLE Tax (
    TaxID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT,
    TaxYear YEAR NOT NULL,
    TaxableIncome DECIMAL(10,2) NOT NULL,
    TaxAmount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID) ON DELETE CASCADE
);

desc tax;

-- FinancialRecord Table
CREATE TABLE FinancialRecord (
    RecordID INT PRIMARY KEY AUTO_INCREMENT,
    EmployeeID INT,
    RecordDate DATE NOT NULL,
    Description TEXT NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    RecordType ENUM('Income', 'Expense', 'Tax Payment') NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID) ON DELETE CASCADE
);
