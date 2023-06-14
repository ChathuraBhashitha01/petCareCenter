package lk.ijse.petcarecenter.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Medicine {
    private String medicineID;
    private  String name;
    private int quantity;
    private double price;

}
