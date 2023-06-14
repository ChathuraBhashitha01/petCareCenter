package lk.ijse.petcarecenter.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Pet {
    private String petID;
    private  String name;
    private int age;
    private String type;
    private String bread;
    private String ownerID;
}
