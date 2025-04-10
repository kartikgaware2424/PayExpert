package org.example.Dao;

import org.example.Exceptions.DatabaseConnectionException;
import org.example.Exceptions.FinancialRecordException;
import org.example.Models.FinancialRecord;
import org.example.Util.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IFinancialRecordServiceImpl implements IFinancialRecordService{
    Connection connection;
    PreparedStatement pst;

    @Override
    public String AddFinancialRecord(int employeeId,  String description, double amount, String recordType) throws SQLException, ClassNotFoundException, FinancialRecordException, DatabaseConnectionException {

        connection = ConnectionHelper.getConnection();
        String insert = "INSERT INTO FinancialRecord (EmployeeID, RecordDate, Description, Amount, RecordType) VALUES (?, ?, ?, ?, ?)";
        pst = connection.prepareStatement(insert);
        pst.setInt(1, employeeId);
        pst.setDate(2, new java.sql.Date(System.currentTimeMillis()));
        pst.setString(3, description);
        pst.setDouble(4, amount);
        pst.setString(5, recordType);

        int rows = pst.executeUpdate();


        if (rows <= 0) {
            throw new FinancialRecordException("Failed to add financial record for Employee ID: " + employeeId);
        }
        return "Record added successfully!";
    }

    @Override
    public String GetFinancialRecordById(int recordId) throws SQLException, ClassNotFoundException, DatabaseConnectionException {

        connection = ConnectionHelper.getConnection();





        String cmd="Select * from FinancialRecord where RecordID=?";
        pst=connection.prepareStatement(cmd);
        pst.setInt(1,recordId);
        ResultSet rs= pst.executeQuery();
        FinancialRecord financialRecord = new FinancialRecord();
        if(rs.next())
        {
            financialRecord.setRecordId(rs.getInt("RecordId"));
            financialRecord.setEmployeeId(rs.getInt("EmployeeID"));
            financialRecord.setRecordDate(rs.getDate("RecordDate"));
            financialRecord.setDescription(rs.getString("Description"));
            financialRecord.setAmount( rs.getDouble("Amount"));
            financialRecord.setRecordType( rs.getString("RecordType"));

        }
        int employId=financialRecord.getRecordId();


        String cmd2="Select TaxableIncome from tax where EmployeeId=?";
        PreparedStatement pst1=connection.prepareStatement(cmd2);
        pst1.setInt(1,employId);
        ResultSet rs1= pst1.executeQuery();
        double taxableAmount = 0.0;
        if (rs1.next()) {
            taxableAmount=rs1.getDouble("TaxableIncome");

        } else {
            return "No tax info found for Employee ID: " + recordId;
        }


        String cmd1="update FinancialRecord set  Amount=? where EmployeeId=?";
        PreparedStatement pst2=connection.prepareStatement(cmd1);
        pst2.setDouble(1,taxableAmount);
        pst2.setInt(2,employId);
        pst2.executeUpdate();
        return String.valueOf(financialRecord);
    }

    @Override
    public FinancialRecord GetFinancialRecordsForEmployee(int employeeId) throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        connection = ConnectionHelper.getConnection();
        String cmd="Select * from FinancialRecord where EmployeeID=?";
        pst=connection.prepareStatement(cmd);
        pst.setInt(1,employeeId);
        ResultSet rs= pst.executeQuery();
        FinancialRecord financialRecord = new FinancialRecord();
        if(rs.next())
        {
            financialRecord.setRecordId(rs.getInt("RecordId"));
            financialRecord.setEmployeeId(rs.getInt("EmployeeID"));
            financialRecord.setRecordDate(rs.getDate("RecordDate"));
            financialRecord.setDescription(rs.getString("Description"));
            financialRecord.setAmount( rs.getDouble("Amount"));
            financialRecord.setRecordType( rs.getString("RecordType"));

        }
        return (financialRecord);
    }

    @Override
    public List<FinancialRecord> GetFinancialRecordsForDate(Date recordDate) throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        connection = ConnectionHelper.getConnection();
        String cmd="Select * from FinancialRecord where RecordDate=?";
        pst=connection.prepareStatement(cmd);
        pst.setDate(1,new java.sql.Date(recordDate.getTime()));
        ResultSet rs= pst.executeQuery();

        List<FinancialRecord>   financialRecordList =new ArrayList<>();;
        while(rs.next())
        {
            FinancialRecord financialRecord=new FinancialRecord();
            financialRecord.setRecordId(rs.getInt("RecordId"));
            financialRecord.setEmployeeId(rs.getInt("EmployeeID"));
            financialRecord.setRecordDate(rs.getDate("RecordDate"));
            financialRecord.setDescription(rs.getString("Description"));
            financialRecord.setAmount( rs.getDouble("Amount"));
            financialRecord.setRecordType( rs.getString("RecordType"));
            financialRecordList.add(financialRecord);


        }

        return (financialRecordList);
    }
}
