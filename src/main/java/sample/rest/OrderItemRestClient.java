package sample.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.OrderItemDTO;

import java.util.List;

public class OrderItemRestClient {
    private static final String POST_ADD_URL = "http://localhost:8080/orderItems/addAll";

    private final RestTemplate restTemplate;

    public OrderItemRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public void saveOrderItems(List<OrderItemDTO> orderItemsDTO) {
        try {
            ResponseEntity<OrderItemDTO[]> responseEntity = restTemplate.postForEntity(POST_ADD_URL, orderItemsDTO, OrderItemDTO[].class);
        } catch (Exception ignored){}
    }
}
