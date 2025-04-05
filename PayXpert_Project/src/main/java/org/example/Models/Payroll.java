package org.example.Models;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payroll {
    private int payrollID;
    private int employeeID;
    private Date payPeriodStartDate;
    private Date payPeriodEndDate;
    private double basicSalary;
    private double overtimePay;
    private double deductions;
    private double netSalary;
}
