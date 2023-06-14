package lk.ijse.petcarecenter.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Doctor {
    private String doctorID;
    private String name;
    private int phoneNumber;
}
