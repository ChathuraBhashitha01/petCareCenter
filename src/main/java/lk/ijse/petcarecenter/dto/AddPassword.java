package lk.ijse.petcarecenter.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddPassword {
    private String user;
    private String userName;
    private String password;
}
