package org.example.Dao;

import org.example.Exceptions.DatabaseConnectionException;
import org.example.Exceptions.PayrollGenerationException;
import org.example.Models.Payroll;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IPayrollService {
    String generatePayroll(int employeeId, Date startDate, Date endDate) throws SQLException, ClassNotFoundException, PayrollGenerationException, DatabaseConnectionException;
    Payroll getPayrollById(int payrollId) throws SQLException, ClassNotFoundException, DatabaseConnectionException;

    List<Payroll> getPayrollsForEmployee(int employeeId) throws SQLException, ClassNotFoundException, DatabaseConnectionException;
    List<Payroll> getPayrollsForPeriod(Date startDate, Date endDate) throws SQLException, ClassNotFoundException, DatabaseConnectionException;

}
