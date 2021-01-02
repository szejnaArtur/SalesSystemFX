package sample.dto;

import lombok.Data;

@Data
public class EmployeeAuthenticationResultDTO {

    private Long idEmployee;
    private String firstName;
    private String lastName;
    private boolean authenticated;

}
