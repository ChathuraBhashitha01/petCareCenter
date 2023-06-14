package lk.ijse.petcarecenter.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ItemCartTM {
    private  String itemCode;
    private String itemDes;
    private  double itemPrice;
    private int itemQuantity;
    private double total;
    /*private String medicineID;
    private String medicineDes;
    private double medicinePrice;
    private int medicineQty;*/

}
