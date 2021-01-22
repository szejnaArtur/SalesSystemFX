package sample.handler;

import sample.dto.UserAuthenticationResultDTO;

@FunctionalInterface
public interface AuthenticationResultHandler {

    void handle(UserAuthenticationResultDTO userAuthenticationResultDTO);
}
