package sample.handler;

import sample.dto.EmployeeAuthenticationResultDTO;

@FunctionalInterface
public interface AuthenticationResultHandler {

    void handle(EmployeeAuthenticationResultDTO employeeAuthenticationResultDTO);
}
