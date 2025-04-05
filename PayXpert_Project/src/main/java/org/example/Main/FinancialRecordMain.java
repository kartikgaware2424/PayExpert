package org.example.Main;

import org.example.Dao.IFinancialRecordService;
import org.example.Dao.IFinancialRecordServiceImpl;
import org.example.Models.FinancialRecord;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class FinancialRecordMain {
    static  Scanner sc=new Scanner(System.in);
    static IFinancialRecordService dao=new IFinancialRecordServiceImpl();

    public static  void  addFinancialRecord()
    {
        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Description: ");
        String description = sc.nextLine();

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine(); // consume newline

        System.out.print("Enter Record Type (Income/Expense): ");
        String recordType = sc.nextLine();

        try {
            String result = dao.AddFinancialRecord(empId, description, amount, recordType);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static  void  getFinancialRecordById()
    {
        System.out.println("Enter the Record ID ");
        int Record_Id=sc.nextInt();
        try {

            System.out.println(  dao.GetFinancialRecordById(Record_Id));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
public static void GetFinancialRecordsForEmployee()
{
    System.out.println("Enter the Employee ID ");
    int Employee_ID=sc.nextInt();
    try {
        System.out.println(dao.GetFinancialRecordsForEmployee(Employee_ID));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }

}
public  static  void GetFinancialRecordsForDate()
{
    System.out.println("Enter the Date ");
    String dateStr=sc.next();
    Date startDate = Date.valueOf(dateStr);
    try {
        List<FinancialRecord> recordList=dao.GetFinancialRecordsForDate(startDate);
        for(FinancialRecord record:recordList)
        {
            System.out.println(record);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }


}

    public static void main(String[] args) {
        boolean back = false;

        while (!back) {
            System.out.println("\n===== Financial Record Menu =====");
            System.out.println("1. Add Financial Record");
            System.out.println("2. Get Record by ID");
            System.out.println("3. Get Records for an Employee");
            System.out.println("4. Get Records for a Date");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addFinancialRecord();
                    break;
                case 2:
                    getFinancialRecordById();
                    break;
                case 3:
                   GetFinancialRecordsForEmployee();
                    break;
                case 4:
                   GetFinancialRecordsForDate() ;
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
