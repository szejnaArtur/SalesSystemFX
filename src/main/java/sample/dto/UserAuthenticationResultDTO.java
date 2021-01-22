package sample.dto;

import lombok.Data;

@Data
public class UserAuthenticationResultDTO {

    private Long idUser;
    private String firstName;
    private String lastName;
    private boolean authenticated;

}
