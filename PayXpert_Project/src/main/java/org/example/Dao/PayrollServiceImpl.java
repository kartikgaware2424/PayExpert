package org.example.Dao;

import org.example.Exceptions.DatabaseConnectionException;
import org.example.Exceptions.InvalidInputException;
import org.example.Exceptions.PayrollGenerationException;
import org.example.Models.Payroll;
import org.example.Util.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayrollServiceImpl implements IPayrollService {
    Connection connection;
    PreparedStatement pst;
    @Override
    public String generatePayroll(int employeeId, Date startDate, Date endDate) throws SQLException, ClassNotFoundException, PayrollGenerationException, DatabaseConnectionException {
        connection = ConnectionHelper.getConnection();

        // Fetch Payroll Record for the Given Employee
        String fetchQuery = "SELECT PayrollID, BasicSalary, OvertimePay, Deductions FROM Payroll WHERE EmployeeID = ? AND PayPeriodStartDate = ? AND PayPeriodEndDate = ?";
        pst = connection.prepareStatement(fetchQuery);
        pst.setInt(1, employeeId);
        pst.setDate(2, new java.sql.Date(startDate.getTime()));
        pst.setDate(3, new java.sql.Date(endDate.getTime()));
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            int payrollId = rs.getInt("PayrollID");
            double basicSalary = rs.getDouble("BasicSalary");
            double overtimePay = rs.getDouble("OvertimePay");
            double deductions = rs.getDouble("Deductions");

            // Calculate Net Salary
            double netSalary = basicSalary + overtimePay - deductions;

            // Update NetSalary in Existing Payroll Entry
            String updateQuery = "UPDATE Payroll SET NetSalary = ? WHERE PayrollID = ?";
            pst = connection.prepareStatement(updateQuery);
            pst.setDouble(1, netSalary);
            pst.setInt(2, payrollId);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                return "Payroll updated! Employee ID: " + employeeId + " | Net Salary: " + netSalary;
            } else {
                throw new PayrollGenerationException("Failed to update payroll for Employee ID " + employeeId);
            }
        } else {
            throw new PayrollGenerationException("Payroll entry not found for Employee ID " + employeeId + " in the given period.");

        }
    }

    @Override
    public Payroll getPayrollById(int payrollId) throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        connection = ConnectionHelper.getConnection();
        String query = "SELECT * FROM Payroll WHERE PayrollID = ?";
        pst = connection.prepareStatement(query);
        pst.setInt(1, payrollId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return new Payroll(
                    rs.getInt("PayrollID"),
                    rs.getInt("EmployeeID"),
                    rs.getDate("PayPeriodStartDate"),
                    rs.getDate("PayPeriodEndDate"),
                    rs.getDouble("BasicSalary"),
                    rs.getDouble("OvertimePay"),
                    rs.getDouble("Deductions"),
                    rs.getDouble("NetSalary")
            );
        }
        return null;
    }

    @Override
    public List<Payroll> getPayrollsForEmployee(int employeeId) throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        connection = ConnectionHelper.getConnection();
        String query = "SELECT * FROM Payroll WHERE EmployeeID = ?";
        pst = connection.prepareStatement(query);
        pst.setInt(1, employeeId);
        ResultSet rs = pst.executeQuery();

        List<Payroll> payrollList = new ArrayList<>();
        while (rs.next()) {
            Payroll payroll = new Payroll(
                    rs.getInt("PayrollID"),
                    rs.getInt("EmployeeID"),
                    rs.getDate("PayPeriodStartDate"),
                    rs.getDate("PayPeriodEndDate"),
                    rs.getDouble("BasicSalary"),
                    rs.getDouble("OvertimePay"),
                    rs.getDouble("Deductions"),
                    rs.getDouble("NetSalary")
            );
            payrollList.add(payroll);
        }
        return payrollList;
    }
    public List<Payroll> getPayrollsForPeriod(Date startDate, Date endDate) throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        connection = ConnectionHelper.getConnection();
        String query = "SELECT * FROM Payroll WHERE PayPeriodStartDate >= ? AND PayPeriodEndDate <= ?";
        pst = connection.prepareStatement(query);
        pst.setDate(1, new java.sql.Date(startDate.getTime()));
        pst.setDate(2, new java.sql.Date(endDate.getTime()));
        ResultSet rs = pst.executeQuery();

        List<Payroll> payrollList = new ArrayList<>();
        while (rs.next()) {
            Payroll payroll = new Payroll(
                    rs.getInt("PayrollID"),
                    rs.getInt("EmployeeID"),
                    rs.getDate("PayPeriodStartDate"),
                    rs.getDate("PayPeriodEndDate"),
                    rs.getDouble("BasicSalary"),
                    rs.getDouble("OvertimePay"),
                    rs.getDouble("Deductions"),
                    rs.getDouble("NetSalary")
            );
            payrollList.add(payroll);
        }
        return payrollList;
    }
}
