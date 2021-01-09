package sample.rest;

import sample.dto.EmployeeCredentialsDTO;
import sample.handler.AuthenticationResultHandler;

public interface Authenticator {

    void authenticate(EmployeeCredentialsDTO employeeCredentialsDTO, AuthenticationResultHandler authenticationResultHandler);
}
