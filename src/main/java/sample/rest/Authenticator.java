package sample.rest;

import sample.dto.UserCredentialsDTO;

public interface Authenticator {

    void authenticate(UserCredentialsDTO userCredentialsDTO, AuthenticationResultHandler authenticationResultHandler);
}
