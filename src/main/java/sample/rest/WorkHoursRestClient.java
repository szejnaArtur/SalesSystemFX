package sample.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.EmployeeDTO;
import sample.dto.WorkHoursDTO;
import sample.handler.SavedMenuItemHandler;

public class WorkHoursRestClient {

    private static final String GET_FINDALL_URL = "http://localhost:8080/workHours/findFirst/";
    private static final String POST_ADD_URL = "http://localhost:8080/workHours/add/";

    private final RestTemplate restTemplate;

    public WorkHoursRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public WorkHoursDTO getLastWorkHours(Long idEmployee) {
        String url = GET_FINDALL_URL + idEmployee;
        ResponseEntity<WorkHoursDTO> responseEntity = restTemplate.getForEntity(url, WorkHoursDTO.class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return responseEntity.getBody();
        } else{
            return new WorkHoursDTO();
        }
    }

    public void saveWorkHours(EmployeeDTO employeeDTO, SavedMenuItemHandler handler) {
        String url = POST_ADD_URL + employeeDTO.getIdEmployee();
        ResponseEntity<WorkHoursDTO> responseEntity = restTemplate.postForEntity(url, null, WorkHoursDTO.class);
        if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
            handler.handle();
        }
    }
}
