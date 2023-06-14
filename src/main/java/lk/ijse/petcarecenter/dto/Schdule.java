package lk.ijse.petcarecenter.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Schdule {
    private String schduleID;
    private String inTime;
    private String outTime;
    private String date;
    private String doctorID;
}
