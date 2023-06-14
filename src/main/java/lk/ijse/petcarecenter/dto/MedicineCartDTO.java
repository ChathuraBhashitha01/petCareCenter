package lk.ijse.petcarecenter.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class MedicineCartDTO {
    private  String medicineID;
    private int medicineQuantity;
}
