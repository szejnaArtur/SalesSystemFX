package sample.rest;

import org.springframework.web.client.RestTemplate;
import sample.dto.OrderBillDTO;

public class OrderRestClient {
    private static final String POST_ADD_URL = "http://localhost:8080/orderBills/add";

    private final RestTemplate restTemplate;

    public OrderRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public void saveOrderBill(OrderBillDTO orderBillDTO) {
        restTemplate.postForEntity(POST_ADD_URL, orderBillDTO, OrderBillDTO.class);
    }
}
