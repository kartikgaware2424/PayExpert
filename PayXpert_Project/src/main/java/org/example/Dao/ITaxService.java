package org.example.Dao;

import org.example.Models.Tax;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ITaxService {
    String calculateTax(int employeeId, int taxYear) throws SQLException, ClassNotFoundException;
    Tax getTaxById(int taxId) throws SQLException, ClassNotFoundException;
    List<Tax> getTaxesForEmployee(int employeeId) throws SQLException, ClassNotFoundException;
    List<Tax> getTaxesForYear(int taxYear) throws SQLException, ClassNotFoundException;
}
