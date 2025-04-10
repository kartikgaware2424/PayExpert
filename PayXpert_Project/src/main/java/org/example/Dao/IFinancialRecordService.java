package org.example.Dao;

import org.example.Exceptions.DatabaseConnectionException;
import org.example.Exceptions.FinancialRecordException;
import org.example.Models.FinancialRecord;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IFinancialRecordService {
   String AddFinancialRecord(int employeeId,String description, double amount, String recordType) throws SQLException, ClassNotFoundException, FinancialRecordException, DatabaseConnectionException;
   String  GetFinancialRecordById(int recordId) throws SQLException, ClassNotFoundException, DatabaseConnectionException;
  FinancialRecord GetFinancialRecordsForEmployee(int employeeId) throws SQLException, ClassNotFoundException, DatabaseConnectionException;
  List<FinancialRecord> GetFinancialRecordsForDate(Date recordDate) throws SQLException, ClassNotFoundException, DatabaseConnectionException;
}
