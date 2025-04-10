package org.example.Dao;

import org.example.Exceptions.DatabaseConnectionException;
import org.example.Exceptions.EmployeeNotFoundException;
import org.example.Models.Employee;

import java.sql.SQLException;
import java.util.List;

public interface IEmployeeService {
    Employee getEmployeeById(int employeeId) throws SQLException, ClassNotFoundException, EmployeeNotFoundException, DatabaseConnectionException;
    List<Employee> getAllEmployees() throws SQLException, ClassNotFoundException, DatabaseConnectionException;
    String addEmployee(Employee employeeData) throws SQLException, ClassNotFoundException, DatabaseConnectionException;
    String updateEmployee(Employee employeeData) throws SQLException, ClassNotFoundException, DatabaseConnectionException;
    String removeEmployee(int employeeId) throws SQLException, ClassNotFoundException, EmployeeNotFoundException, DatabaseConnectionException;

}
