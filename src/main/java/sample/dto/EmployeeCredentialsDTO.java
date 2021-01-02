package sample.dto;

import lombok.Data;

@Data
public class EmployeeCredentialsDTO {

    private String login;
    private String password;


    public static EmployeeCredentialsDTO of(String login, String password){
        EmployeeCredentialsDTO dto = new EmployeeCredentialsDTO();
        dto.setLogin(login);
        dto.setPassword(password);
        return dto;
    }
}
