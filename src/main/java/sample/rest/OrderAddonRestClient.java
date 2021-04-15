package sample.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.OrderAddonDTO;

import java.util.List;

public class OrderAddonRestClient {
    private static final String POST_ADD_URL = "http://localhost:8080/orderAddons/addAll";

    private final RestTemplate restTemplate;

    public OrderAddonRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public void saveOrderAddons(List<OrderAddonDTO> orderAddonDTOList) {
        try {
            ResponseEntity<OrderAddonDTO[]> responseEntity = restTemplate.postForEntity(POST_ADD_URL, orderAddonDTOList, OrderAddonDTO[].class);
        } catch (Exception ignored) {
        }
    }
}
