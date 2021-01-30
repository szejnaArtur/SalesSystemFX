package sample.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.EmployeeDTO;

public class EmployeeRestClient {

    private static final String GET_EMPLOYEE_FINDBYPIN = "http://localhost:8080/employee/findByPIN/";

    private final RestTemplate restTemplate;

    public EmployeeRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public EmployeeDTO getEmployeeByPIN(String PIN) {
        String url = GET_EMPLOYEE_FINDBYPIN + PIN;
        ResponseEntity<EmployeeDTO> responseEntity = restTemplate.getForEntity(url, EmployeeDTO.class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return responseEntity.getBody();
        } else{
            return new EmployeeDTO();
        }
    }

}
