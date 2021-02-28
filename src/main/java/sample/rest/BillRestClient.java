package sample.rest;

import org.springframework.web.client.RestTemplate;
import sample.dto.BillDTO;

public class BillRestClient {
    private static final String POST_ADD_URL = "http://localhost:8080/bills/add";

    private final RestTemplate restTemplate;

    public BillRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public void saveBill(BillDTO billDTO) {
        restTemplate.postForEntity(POST_ADD_URL, billDTO, BillDTO.class);
    }
}
