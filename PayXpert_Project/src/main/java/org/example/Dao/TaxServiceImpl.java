package org.example.Dao;

import org.example.Exceptions.DatabaseConnectionException;
import org.example.Exceptions.TaxCalculationException;
import org.example.Models.Tax;
import org.example.Util.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaxServiceImpl implements ITaxService{
    Connection connection;
    PreparedStatement pst;
    @Override
    public String calculateTax(int employeeId, int taxYear) throws SQLException, ClassNotFoundException, TaxCalculationException, DatabaseConnectionException {
        connection = ConnectionHelper.getConnection();


        String cmd = "SELECT SUM(NetSalary) AS TotalIncome FROM Payroll WHERE EmployeeID = ? AND YEAR(PayPeriodStartDate) = ?";
        pst = connection.prepareStatement(cmd);
        pst.setInt(1, employeeId);
        pst.setInt(2, taxYear);
        ResultSet rs = pst.executeQuery();

        double taxableIncome = 0.0;
        if (rs.next()) {
            taxableIncome = rs.getDouble("TotalIncome");

        }
        double taxAmount ;

        if (taxableIncome <= 5000) {
            taxAmount = taxableIncome * 0.05;
        } else {
            taxAmount = taxableIncome * 0.10;
        }
        taxableIncome= taxableIncome-taxAmount;
        String cmd2 = "SELECT TaxID FROM Tax WHERE EmployeeID = ? AND TaxYear = ?";
        pst = connection.prepareStatement(cmd2);
        pst.setInt(1, employeeId);
        pst.setInt(2, taxYear);
        ResultSet checkRs = pst.executeQuery();

        if (checkRs.next()) {

            String updateQuery = "UPDATE Tax SET TaxableIncome =  ?, TaxAmount = ? WHERE EmployeeID = ? AND TaxYear = ?";
            pst = connection.prepareStatement(updateQuery);
            pst.setDouble(1, taxableIncome);
            pst.setDouble(2, taxAmount);
            pst.setInt(3, employeeId);
            pst.setInt(4, taxYear);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                return "Tax updated for Employee ID: " + employeeId;
            } else {
                throw new TaxCalculationException("Failed to update tax for Employee ID " + employeeId);
            }
        }
        return "Tax Calculated SuccessFully";
    }

    @Override
    public Tax getTaxById(int taxId) throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        connection = ConnectionHelper.getConnection();
        String query = "SELECT * FROM Tax WHERE TaxID = ?";
        pst = connection.prepareStatement(query);
        pst.setInt(1, taxId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return new Tax(rs.getInt("TaxID"), rs.getInt("EmployeeID"), rs.getInt("TaxYear"),
                    rs.getDouble("TaxableIncome"), rs.getDouble("TaxAmount"));
        }
        return null;
    }

    @Override
    public List<Tax> getTaxesForEmployee(int employeeId) throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        connection = ConnectionHelper.getConnection();
        List<Tax> taxes = new ArrayList<>();

        String query = "SELECT *  FROM Tax  WHERE  EmployeeID = ?";
        pst = connection.prepareStatement(query);
        pst.setInt(1, employeeId);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            taxes.add(new Tax(rs.getInt("TaxID"), rs.getInt("EmployeeID"), rs.getInt("TaxYear"),
                    rs.getDouble("TaxableIncome"), rs.getDouble("TaxAmount")));
        }
        return taxes;
    }

    @Override
    public List<Tax> getTaxesForYear(int taxYear) throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        connection = ConnectionHelper.getConnection();
        List<Tax> taxes = new ArrayList<>();

        String query = "SELECT * FROM Tax WHERE TaxYear = ?";
        pst = connection.prepareStatement(query);
        pst.setInt(1,taxYear);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            taxes.add(new Tax(rs.getInt("TaxID"), rs.getInt("EmployeeID"), rs.getInt("TaxYear"),
                    rs.getDouble("TaxableIncome"), rs.getDouble("TaxAmount")));
        }
        return taxes;
    }
}
