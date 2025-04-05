package org.example.Models;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class FinancialRecord {
    private int recordId;
    private int employeeId;
    private Date recordDate;
    private String description;
    private double amount;
    private String recordType;
}
