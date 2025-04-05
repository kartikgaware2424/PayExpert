package org.example.Dao;

import org.example.Models.FinancialRecord;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IFinancialRecordService {
   String AddFinancialRecord(int employeeId,String description, double amount, String recordType) throws SQLException, ClassNotFoundException;
   String  GetFinancialRecordById(int recordId) throws SQLException, ClassNotFoundException;
  FinancialRecord GetFinancialRecordsForEmployee(int employeeId) throws SQLException, ClassNotFoundException;
  List<FinancialRecord> GetFinancialRecordsForDate(Date recordDate) throws SQLException, ClassNotFoundException;
}
