package org.example.Main;

import org.example.Dao.ITaxService;
import org.example.Dao.TaxServiceImpl;
import org.example.Exceptions.DatabaseConnectionException;
import org.example.Exceptions.TaxCalculationException;
import org.example.Models.Tax;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaxMain {
    static Scanner sc = new Scanner(System.in);
    static ITaxService taxService = new TaxServiceImpl();

    public static void calculateTax() {
        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();
        System.out.print("Enter Tax Year: ");
        int taxYear = sc.nextInt();

        try {
            String result = taxService.calculateTax(empId, taxYear);
            System.out.println(result);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error calculating tax: " + e.getMessage());
        } catch (TaxCalculationException | DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }
    }
    public static void getTaxById() {
        System.out.print("Enter Tax ID: ");
        int taxId = sc.nextInt();

        try {
            Tax tax = taxService.getTaxById(taxId);
            System.out.println(tax != null ? tax : "No tax record found.");
        } catch (SQLException | ClassNotFoundException | DatabaseConnectionException e) {
            System.out.println("Error fetching tax: " + e.getMessage());
        }
    }
    public static  void GetTaxesForEmployee()
    {
        int empid;
        System.out.println("Enter the employ id :");
        empid= sc.nextInt();;
        try {
            List<Tax> Empoyee=taxService.getTaxesForEmployee(empid);
            for(Tax e:Empoyee)
            {
                System.out.println(e);
            }
        } catch (SQLException | ClassNotFoundException | DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }


    }
    public static void GetTaxesForYear()
    {

        System.out.println("Enter tax year : ");
        int taxYear = sc.nextInt();


        try {
            List<Tax> taxYearDetails=taxService.getTaxesForYear(taxYear);
            for(Tax t:taxYearDetails)
            {
                System.out.println(t);
            }
        } catch (SQLException | ClassNotFoundException | DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }


    }



    public static void main(String[] args) {
        boolean back = false;

        while (!back) {
            System.out.println("Tax Management System");
            System.out.println("1. Calculate Tax");
            System.out.println("2. Get Tax By ID");
            System.out.println("3. Get Taxes for an Employee");
            System.out.println("4. Get Taxes for a Year");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    calculateTax();
                break;
                case 2:
                    getTaxById();
                break;
                case 3:
                     GetTaxesForEmployee();
                    break;
                case 4:
                    GetTaxesForYear();
                    break;
                case 5:
                    back = true;
                    break;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
