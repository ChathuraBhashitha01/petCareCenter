package lk.ijse.petcarecenter.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Payment {
    private String paymentID;
    private String petID;
    private double itemSalary;
    private double serviceSalary;
    private double doctorSalary;
    private double clinicPayment;
}
