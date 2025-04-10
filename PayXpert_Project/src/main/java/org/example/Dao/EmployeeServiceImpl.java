package org.example.Dao;

import org.example.Exceptions.DatabaseConnectionException;
import org.example.Exceptions.EmployeeNotFoundException;
import org.example.Models.Employee;
import org.example.Util.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceImpl implements IEmployeeService{
    Connection connection;
    PreparedStatement pst;
    PreparedStatement pst1;
    @Override
    public Employee getEmployeeById(int employeeId) throws SQLException, ClassNotFoundException, EmployeeNotFoundException, DatabaseConnectionException {
        connection= ConnectionHelper.getConnection();
        String cmd="select * from employee where EmployeeID=?";
        pst=connection.prepareStatement(cmd);
        pst.setInt(1,employeeId);
        ResultSet rs= pst.executeQuery();
        Employee employFound=null;
        if (!rs.next()) {
            throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
        }
        else {
                    employFound=new Employee();
                    employFound.setEmployeeID(rs.getInt("EmployeeID"));
                    employFound.setFirstName(rs.getString("FirstName"));
                    employFound.setLastName(rs.getString("LastName"));
                    employFound.setDateOfBirth(  rs.getDate("DateOfBirth"));
                    employFound.setGender( rs.getString("Gender"));
                    employFound.setEmail( rs.getString("Email"));
                    employFound.setPhoneNumber( rs.getString("PhoneNumber"));
                    employFound.setAddress( rs.getString("Address"));
                    employFound.setPosition(rs.getString("Position"));
                    employFound.setJoiningDate(rs.getDate("JoiningDate"));
                    employFound.setTerminationDate(rs.getDate("TerminationDate"));
        }

        return  employFound;
    }



    @Override
    public List<Employee> getAllEmployees() throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        connection=ConnectionHelper.getConnection();
        String cmd="Select * from Employee";
        pst=connection.prepareStatement(cmd);
        ResultSet rs=pst.executeQuery();
        Employee employFound=null;
        List<Employee> employeeList=new ArrayList<>();
        while(rs.next())
        {

            employFound=new Employee();
            employFound.setEmployeeID(rs.getInt("EmployeeID"));
            employFound.setFirstName(rs.getString("FirstName"));
            employFound.setLastName(rs.getString("LastName"));
            employFound.setDateOfBirth(  rs.getDate("DateOfBirth"));
            employFound.setGender( rs.getString("Gender"));
            employFound.setEmail( rs.getString("Email"));
            employFound.setPhoneNumber( rs.getString("PhoneNumber"));
            employFound.setAddress( rs.getString("Address"));
            employFound.setPosition(rs.getString("Position"));
            employFound.setJoiningDate(rs.getDate("JoiningDate"));
            employFound.setTerminationDate(rs.getDate("TerminationDate"));
            employeeList.add(employFound);
        }
        return employeeList;
    }

    @Override
    public String addEmployee(Employee employeeData) throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        connection =ConnectionHelper.getConnection();
        String cmd = "INSERT INTO Employee (FirstName, LastName, DateOfBirth, Gender, Email, " +
                "PhoneNumber, Address, Position, JoiningDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pst=connection.prepareStatement(cmd);
        pst.setString(1,employeeData.getFirstName());
        pst.setString(2,employeeData.getLastName());
        pst.setDate(3,new java.sql.Date( employeeData.getDateOfBirth().getTime()));
        pst.setString(4,employeeData.getGender());
        pst.setString(5,employeeData.getEmail());
        pst.setString(6,employeeData.getPhoneNumber());
        pst.setString(7,employeeData.getAddress());
        pst.setString(8,employeeData.getPosition());
        pst.setDate(9, new java.sql.Date(employeeData.getJoiningDate().getTime()));
        pst.executeUpdate();

        return "Record Added Successfully......!!";
    }

    @Override
    public String updateEmployee(Employee employeeData) throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        connection =ConnectionHelper.getConnection();
        String cmd="Update Employee set FirstName=?, LastName=?, DateOfBirth=?, Gender=?, Email=?," +
                " PhoneNumber=?, Address=?, Position=?, JoiningDate=? where EmployeeID=?";


        pst=connection.prepareStatement(cmd);
        pst.setString(1,employeeData.getFirstName());
        pst.setString(2,employeeData.getLastName());
        pst.setDate(3,new java.sql.Date( employeeData.getDateOfBirth().getTime()));
        pst.setString(4,employeeData.getGender());
        pst.setString(5,employeeData.getEmail());
        pst.setString(6,employeeData.getPhoneNumber());
        pst.setString(7,employeeData.getAddress());
        pst.setString(8,employeeData.getPosition());
        pst.setDate(9, new java.sql.Date(employeeData.getJoiningDate().getTime()));
        pst.setInt(10,employeeData.getEmployeeID());
        pst.executeUpdate();

        return "Record Updated  Successfully......!!";
    }

    @Override
    public String removeEmployee(int employeeId) throws SQLException, ClassNotFoundException, EmployeeNotFoundException, DatabaseConnectionException {
        connection =ConnectionHelper.getConnection();
        String cmd1="select * from employee where employeeId=?";
        pst1=connection.prepareStatement(cmd1);
        pst1.setInt(1,employeeId);
        ResultSet rs=pst1.executeQuery();
        if(!rs.next()) {

                throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");

        }
        else
        {
            connection =ConnectionHelper.getConnection();
            String cmd="delete from employee where  EmployeeID=?";
            pst=connection.prepareStatement(cmd);
            pst.setInt(1,employeeId);
            pst.executeUpdate();
        }
        return "Record deleted Successfully....";
    }

}
