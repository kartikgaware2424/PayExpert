package org.example.Models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tax {
    private int taxId;
    private int employeeId;
    private int taxYear;
    private double taxableIncome;
    private double taxAmount;


}
