package lk.ijse.petcarecenter.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SchduleTM {
    private String schduleID;
    private String inTime;
    private String outTime;
    private String date;
    private String doctorID;
}
