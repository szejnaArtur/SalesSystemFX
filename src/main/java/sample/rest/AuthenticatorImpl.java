package sample.rest;

import org.springframework.web.client.RestTemplate;
import sample.dto.UserAuthenticationResultDTO;
import sample.dto.UserCredentialsDTO;
import sample.handler.AuthenticationResultHandler;

public class AuthenticatorImpl implements Authenticator {

    private static final String AUTHENTICATION_URL = "http://localhost:8080/credentials/veryfy_user_credensials";

    private final RestTemplate restTemplate;

    public AuthenticatorImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void authenticate(UserCredentialsDTO userCredentialsDTO,
                             AuthenticationResultHandler authenticationResultHandler) {
        Runnable authenticationTask = () -> processAuthentication(userCredentialsDTO, authenticationResultHandler);
        Thread authenticationThread = new Thread(authenticationTask);
        authenticationThread.setDaemon(true);
        authenticationThread.start();
    }

    private void processAuthentication(UserCredentialsDTO userCredentialsDTO,
                                       AuthenticationResultHandler authenticationResultHandler) {
//        ResponseEntity<UserAuthenticationResultDTO> responseEntity =
//                restTemplate.postForEntity(AUTHENTICATION_URL, userCredentialsDTO, UserAuthenticationResultDTO.class);
        UserAuthenticationResultDTO dto = new UserAuthenticationResultDTO();
        dto.setAuthenticated(true);
        dto.setFirstName("test");
        dto.setLastName("test");
        dto.setIdUser(152L);
        authenticationResultHandler.handle(dto);
    }

}
