package sample.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.EmployeeDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EmployeeRestClient {

    private static final String GET_EMPLOYEE_FIND_BY_PIN = "http://localhost:8080/employee/findByPIN/";
    private static final String GET_EMPLOYEE_ENDWORKS_IS_NULL = "http://localhost:8080/employee/findAllAtWork";

    private final RestTemplate restTemplate;

    public EmployeeRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public EmployeeDTO getEmployeeByPIN(String PIN) {
        String url = GET_EMPLOYEE_FIND_BY_PIN + PIN;
        ResponseEntity<EmployeeDTO> responseEntity = restTemplate.getForEntity(url, EmployeeDTO.class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return responseEntity.getBody();
        } else{
            return new EmployeeDTO();
        }
    }

    public List<EmployeeDTO> getEmployeesAtWork() {
        ResponseEntity<EmployeeDTO[]> responseEntity = restTemplate.getForEntity(GET_EMPLOYEE_ENDWORKS_IS_NULL, EmployeeDTO[].class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())){
            return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
        } else {
            throw new NullPointerException("Can't load any employess.");
        }

    }



}
