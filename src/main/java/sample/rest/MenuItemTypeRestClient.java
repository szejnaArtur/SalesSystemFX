package sample.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.MenuItemDTO;
import sample.dto.MenuItemTypeDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuItemTypeRestClient {

    private static final String GET_FIND_ALL_URL = "http://localhost:8080/menuItemType/findAll";

    private final RestTemplate restTemplate;

    public MenuItemTypeRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<MenuItemTypeDTO> getMenuItemTypes() {
        ResponseEntity<MenuItemTypeDTO[]> types = restTemplate.getForEntity(GET_FIND_ALL_URL, MenuItemTypeDTO[].class);
        return Arrays.asList(Objects.requireNonNull(types.getBody()));
    }
}
