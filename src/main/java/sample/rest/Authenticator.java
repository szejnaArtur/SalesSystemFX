package sample.rest;

import sample.dto.EmployeeCredentialsDTO;

public interface Authenticator {

    void authenticate(EmployeeCredentialsDTO employeeCredentialsDTO, AuthenticationResultHandler authenticationResultHandler);
}
