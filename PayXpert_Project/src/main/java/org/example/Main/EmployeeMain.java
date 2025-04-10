package org.example.Main;

import org.example.Dao.EmployeeServiceImpl;
import org.example.Dao.IEmployeeService;
import org.example.Exceptions.DatabaseConnectionException;
import org.example.Exceptions.EmployeeNotFoundException;
import org.example.Models.Employee;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EmployeeMain {
    static Scanner sc=new Scanner(System.in);
    static IEmployeeService Dao=new EmployeeServiceImpl();

    public static void viewEmployeeById()
    {
        System.out.println("Enter the EmoloyeeId:");
        int EmployeeId=sc.nextInt();

        try {
            Employee EmployFound=Dao.getEmployeeById(EmployeeId);
            System.out.println(EmployFound);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (EmployeeNotFoundException | DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }
    }
    public static void viewAllEmployees() {
        try {
            List<Employee> employeeList=Dao.getAllEmployees();
            for(Employee employee: employeeList)
            {
                System.out.println(employee);
            }
        } catch (SQLException | ClassNotFoundException | DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addEmployee() throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        Employee employee=new Employee();
        System.out.println("\nAdd New Employee");
        System.out.println("----------------");

        System.out.print("First Name: ");
        employee.setFirstName(sc.next());


        System.out.print("Last Name: ");
        employee.setLastName(sc.next());

        System.out.print("Date of Birth (yyyy-MM-dd): ");
        employee.setDateOfBirth(parseDate(sc.next()));

        System.out.print("Gender (Male/Female/Other): ");
       employee.setGender(sc.next());

        System.out.print("Email: ");
        employee.setEmail(sc.next());

        System.out.print("Phone Number: ");
        employee.setPhoneNumber(sc.next());

        System.out.print("Address: ");
        employee.setAddress(sc.next());

        System.out.print("Position: ");
        employee.setPosition(sc.next());

        System.out.print("Joining Date (yyyy-MM-dd): ");
        employee.setJoiningDate(parseDate(sc.next()));

        Dao.addEmployee(employee);
        System.out.println("Employee added successfully!");

    }
    public static void updateEmployee() throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        Employee employee=new Employee();
        System.out.println("\nUpdate Employee");
        System.out.println("----------------");

        System.out.print("Employee Id : ");
        employee.setEmployeeID(sc.nextInt());

        System.out.print("First Name: ");
        employee.setFirstName(sc.next());


        System.out.print("Last Name: ");
        employee.setLastName(sc.next());

        System.out.print("Date of Birth (yyyy-MM-dd): ");
        employee.setDateOfBirth(parseDate(sc.next()));

        System.out.print("Gender (Male/Female/Other): ");
        employee.setGender(sc.next());

        System.out.print("Email: ");
        employee.setEmail(sc.next());

        System.out.print("Phone Number: ");
        employee.setPhoneNumber(sc.next());

        System.out.print("Address: ");
        employee.setAddress(sc.next());

        System.out.print("Position: ");
        employee.setPosition(sc.next());

        System.out.print("Joining Date (yyyy-MM-dd): ");
        employee.setJoiningDate(parseDate(sc.next()));

        Dao.updateEmployee(employee);
        System.out.println("Employee updated successfully!");

    }
    public static void removeEmployee()
    {
        System.out.println("Remove the Employee");
        System.out.println("Enter EmployeeId: ");
        int employeeiD=sc.nextInt();
        try {
            System.out.println(Dao.removeEmployee(employeeiD));
        } catch (SQLException | EmployeeNotFoundException | ClassNotFoundException | DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }
    }
    private static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return new Date(); // Return current date as fallback
        }
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException, DatabaseConnectionException {

        boolean back = false;

        while (!back) {
            System.out.println("\nEmployee Management");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. View Employee by ID");
            System.out.println("4. Update Employee");
            System.out.println("5. Remove Employee");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                   viewAllEmployees();
                    break;
                case 3:
                    viewEmployeeById();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    removeEmployee();
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
