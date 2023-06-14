package lk.ijse.petcarecenter.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MedicineCartTM {
    private  String medicineID;
    private String name;
    private int medicineQuantity;
    private  double price;
    private double medicineTotal;
}
