package sample.dto;

import lombok.Data;

@Data
public class UserCredentialsDTO {

    private String login;
    private String password;


    public static UserCredentialsDTO of(String login, String password){
        UserCredentialsDTO dto = new UserCredentialsDTO();
        dto.setLogin(login);
        dto.setPassword(password);
        return dto;
    }
}
