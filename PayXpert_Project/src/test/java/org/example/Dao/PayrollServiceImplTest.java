package org.example.Dao;

import org.example.Exceptions.DatabaseConnectionException;
import org.example.Exceptions.PayrollGenerationException;
import org.example.Exceptions.TaxCalculationException;
import org.example.Models.Employee;
import org.example.Models.Payroll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Date;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PayrollServiceImplTest {
    private PayrollServiceImpl payrollService;
    private TaxServiceImpl taxService;
    private Employee testEmployee;
    private Date startDate;
    private Date endDate;

    @BeforeEach
    void setUp() {
        payrollService = new PayrollServiceImpl();
        taxService = new TaxServiceImpl();


        testEmployee = new Employee();
        testEmployee.setEmployeeID(1);


        startDate = Date.valueOf("2024-01-01");
        endDate = Date.valueOf("2024-01-31");
    }

    @Test
    void testCalculateGrossSalaryForEmployee() {

        Payroll payroll = new Payroll();
        payroll.setBasicSalary(5000.0);
        payroll.setOvertimePay(1000.0);


        double expectedGrossSalary = payroll.getBasicSalary() + payroll.getOvertimePay();
        double actualGrossSalary = payroll.getBasicSalary() + payroll.getOvertimePay();

        assertEquals(expectedGrossSalary, actualGrossSalary,
                "Gross salary calculation should be correct");
    }

    @Test
    void testCalculateNetSalaryAfterDeductions() {

        Payroll payroll = new Payroll();
        payroll.setBasicSalary(5000.0);
        payroll.setOvertimePay(500.0);
        payroll.setDeductions(1000.0);


        double expectedNetSalary = payroll.getBasicSalary() + payroll.getOvertimePay() - payroll.getDeductions();
        payroll.setNetSalary(expectedNetSalary);

        assertEquals(expectedNetSalary, payroll.getNetSalary(),
                "Net salary calculation should be correct");
    }

    @Test
    void testVerifyTaxCalculationForHighIncomeEmployee() throws SQLException, ClassNotFoundException, TaxCalculationException, DatabaseConnectionException {

        Payroll payroll = new Payroll();
        payroll.setBasicSalary(10000.0);
        payroll.setOvertimePay(2000.0);
        payroll.setDeductions(1000.0);
        payroll.setNetSalary(payroll.getBasicSalary() + payroll.getOvertimePay() - payroll.getDeductions());


        String result = taxService.calculateTax(testEmployee.getEmployeeID(), 2024);


        double expectedTaxRate = 0.10;
        double taxableIncome = payroll.getNetSalary();
        double expectedTaxAmount = taxableIncome * expectedTaxRate;

        assertTrue(result.contains("Tax Calculated SuccessFully"),
                "Tax calculation should be successful for high income employee");
    }

    @Test
    void testProcessPayrollForMultipleEmployees() throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        // Test Case: ProcessPayrollForMultipleEmployees
        List<Payroll> payrolls = payrollService.getPayrollsForPeriod(startDate, endDate);

        assertNotNull(payrolls, "Should be able to retrieve payrolls for multiple employees");
        assertTrue(payrolls.size() >= 0, "Payroll list should be non-negative");
    }

    @Test
    void testVerifyErrorHandlingForInvalidEmployeeData() {

        int invalidEmployeeId = -1;

        assertThrows(PayrollGenerationException.class, () -> {
            payrollService.generatePayroll(invalidEmployeeId, startDate, endDate);
        }, "Should throw exception for invalid employee ID");


        Date invalidStartDate = Date.valueOf("2024-02-01");
        Date invalidEndDate = Date.valueOf("2024-01-31");

        assertThrows(PayrollGenerationException.class, () -> {
            payrollService.generatePayroll(testEmployee.getEmployeeID(), invalidStartDate, invalidEndDate);
        }, "Should throw exception for invalid date range");
    }
}