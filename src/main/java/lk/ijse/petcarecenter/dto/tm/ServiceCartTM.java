package lk.ijse.petcarecenter.dto.tm;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ServiceCartTM {
    private String serviceID;
    private String serviceDes;
    private double serviceCharge;
    private double total;
}
