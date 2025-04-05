package org.example.Dao;

import org.example.Exceptions.EmployeeNotFoundException;
import org.example.Models.Employee;

import java.sql.SQLException;
import java.util.List;

public interface IEmployeeService {
    Employee getEmployeeById(int employeeId) throws SQLException, ClassNotFoundException, EmployeeNotFoundException;
    List<Employee> getAllEmployees() throws SQLException, ClassNotFoundException;
    String addEmployee(Employee employeeData) throws SQLException, ClassNotFoundException;
    String updateEmployee(Employee employeeData) throws SQLException, ClassNotFoundException;
    String removeEmployee(int employeeId) throws SQLException, ClassNotFoundException, EmployeeNotFoundException;
}
