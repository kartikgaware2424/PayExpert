package org.example.Main;

import org.example.Dao.IPayrollService;
import org.example.Dao.PayrollServiceImpl;
import org.example.Exceptions.DatabaseConnectionException;
import org.example.Exceptions.PayrollGenerationException;
import org.example.Models.Payroll;

import java.sql.SQLException;
import java.sql.Date;

import java.util.List;
import java.util.Scanner;



public class PayrollMain {
    static Scanner sc=new Scanner(System.in);
    static IPayrollService payrollDao = new PayrollServiceImpl();
    public static void GeneratePayroll()
    {

        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();
        System.out.print("Enter Pay Period Start Date (YYYY-MM-DD): ");
        String startDateStr = sc.next();
        System.out.print("Enter Pay Period End Date (YYYY-MM-DD): ");
        String endDateStr = sc.next();

        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);
        String result = null;
        try {
            result = payrollDao.generatePayroll(empId, startDate, endDate);
            System.out.println(result);
        } catch (SQLException | ClassNotFoundException | DatabaseConnectionException | PayrollGenerationException e) {
            throw new RuntimeException(e);
        }


    }
    public static void getPayrollById() {
        System.out.print("Enter Payroll ID: ");
        int payrollId = sc.nextInt();

        try {
            Payroll payroll = payrollDao.getPayrollById(payrollId);
            if (payroll != null) {
                System.out.println(payroll);
            } else {
                System.out.println("No payroll found with ID: " + payrollId);
            }
        } catch (SQLException | ClassNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error fetching payroll: " + e.getMessage());
        }
    }
    public static void getPayrollsForEmployee() {
        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();

        try {
            List<Payroll> payrolls = payrollDao.getPayrollsForEmployee(empId);
            if (!payrolls.isEmpty()) {
                for (Payroll payroll : payrolls) {
                    System.out.println(payroll);
                }
            } else {
                System.out.println("No payrolls found for Employee ID: " + empId);
            }
        } catch (SQLException | ClassNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error fetching payrolls: " + e.getMessage());
        }
    }

    public static void getPayrollsForPeriod() {
        System.out.print("Enter Start Date (YYYY-MM-DD): ");
        String startDateStr = sc.next();
        System.out.print("Enter End Date (YYYY-MM-DD): ");
        String endDateStr = sc.next();

        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);

        try {
            List<Payroll> payrolls = payrollDao.getPayrollsForPeriod(startDate, endDate);
            if (!payrolls.isEmpty()) {
                for (Payroll payroll : payrolls) {
                    System.out.println(payroll);
                }
            } else {
                System.out.println("No payrolls found for the given period.");
            }
        } catch (SQLException | ClassNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error fetching payrolls: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        boolean back = false;

        while (!back) {
            System.out.println("Payroll Management System ");
            System.out.println("1. Generate Payroll");
            System.out.println("2. Get Payroll By ID");
            System.out.println("3. Get Payrolls for an Employee");
            System.out.println("4. Get Payrolls for a Period");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    GeneratePayroll();
                    break;
                case 2:
                    getPayrollById();
                    break;
                case 3:
                    getPayrollsForEmployee();
                    break;
                case 4:
                    getPayrollsForPeriod();
                    break;

                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
