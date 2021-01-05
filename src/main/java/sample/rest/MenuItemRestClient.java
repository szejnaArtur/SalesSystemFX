package sample.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.MenuItemDto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuItemRestClient {

    private static final String GET_MENUITEM_URL = "http://localhost:8080/menuItems/findAll";

    private final RestTemplate restTemplate;

    public MenuItemRestClient(){
        this.restTemplate = new RestTemplate();
    }

    public List<MenuItemDto> getMenuItems(){
        ResponseEntity<MenuItemDto[]> menuItems = restTemplate.getForEntity(GET_MENUITEM_URL, MenuItemDto[].class);
        return Arrays.asList(Objects.requireNonNull(menuItems.getBody()));
    }
}
