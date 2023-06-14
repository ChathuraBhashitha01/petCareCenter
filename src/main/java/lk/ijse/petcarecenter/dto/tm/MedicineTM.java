package lk.ijse.petcarecenter.dto.tm;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MedicineTM {
    private String medicineID;
    private  String name;
    private int quantity;
    private double price;

}
