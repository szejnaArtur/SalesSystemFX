package sample.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.EmployeeCredentialsDTO;
import sample.dto.EmployeeAuthenticationResultDTO;

public class AuthenticatorImpl implements Authenticator {

    private static final String AUTHENTICATION_URL = "http://localhost:8080/credentials/veryfy_employee_credensials";

    private final RestTemplate restTemplate;

    public AuthenticatorImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void authenticate(EmployeeCredentialsDTO employeeCredentialsDTO,
                             AuthenticationResultHandler authenticationResultHandler) {
        Runnable authenticationTask = () -> processAuthentication(employeeCredentialsDTO, authenticationResultHandler);
        Thread authenticationThread = new Thread(authenticationTask);
        authenticationThread.setDaemon(true);
        authenticationThread.start();
    }

    private void processAuthentication(EmployeeCredentialsDTO employeeCredentialsDTO,
                                       AuthenticationResultHandler authenticationResultHandler) {
//        ResponseEntity<EmployeeAuthenticationResultDTO> responseEntity =
//                restTemplate.postForEntity(AUTHENTICATION_URL, employeeCredentialsDTO, EmployeeAuthenticationResultDTO.class);
        EmployeeAuthenticationResultDTO dto = new EmployeeAuthenticationResultDTO();
        dto.setAuthenticated(true);
        dto.setFirstName("test");
        dto.setLastName("test");
        dto.setIdEmployee(152L);
        authenticationResultHandler.handle(dto);
    }

}
