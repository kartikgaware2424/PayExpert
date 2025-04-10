package org.example.Dao;

import org.example.Exceptions.DatabaseConnectionException;
import org.example.Exceptions.TaxCalculationException;
import org.example.Models.Tax;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ITaxService {
    String calculateTax(int employeeId, int taxYear) throws SQLException, ClassNotFoundException, TaxCalculationException, DatabaseConnectionException;
    Tax getTaxById(int taxId) throws SQLException, ClassNotFoundException, DatabaseConnectionException;
    List<Tax> getTaxesForEmployee(int employeeId) throws SQLException, ClassNotFoundException, DatabaseConnectionException;
    List<Tax> getTaxesForYear(int taxYear) throws SQLException, ClassNotFoundException, DatabaseConnectionException;
}
