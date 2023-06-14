package lk.ijse.petcarecenter.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AppointmentTM {
    private String appointmentID;
    private String petID;
    private String schduleID;
    private String date;
    private String serviceID;
}
