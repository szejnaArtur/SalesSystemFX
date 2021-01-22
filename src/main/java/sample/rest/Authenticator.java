package sample.rest;

import sample.dto.UserCredentialsDTO;
import sample.handler.AuthenticationResultHandler;

public interface Authenticator {

    void authenticate(UserCredentialsDTO userCredentialsDTO, AuthenticationResultHandler authenticationResultHandler);
}
