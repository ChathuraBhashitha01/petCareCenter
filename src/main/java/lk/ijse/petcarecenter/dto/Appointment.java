package lk.ijse.petcarecenter.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Appointment {
    private String appointmentID;
    private String petID;
    private String schduleID;
    private String date;
    private String serviceID;
}
