package sample.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.AverageGuestCheckDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AverageGuestCheckRestClient {

    private static final String POST_GET_URL = "http://localhost:8080/bills/AGC";

    private final RestTemplate restTemplate;

    public AverageGuestCheckRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<AverageGuestCheckDTO> getAGC() {
        ResponseEntity<AverageGuestCheckDTO[]> responseEntity = restTemplate.getForEntity(POST_GET_URL, AverageGuestCheckDTO[].class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())){

            return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
        } else {
            throw new NullPointerException("Can't load any average bill amount.");
        }

    }

}
