package sample.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.MenuItemTypeDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuItemTypeRestClient {

    private static final String GET_FIND_ALL_URL = "http://localhost:8080/menuItemTypes/findAll";
    private static final String GET_FIND_BY_NAME_URL = "http://localhost:8080/menuItemType/findByName/";

    private final RestTemplate restTemplate;

    public MenuItemTypeRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<MenuItemTypeDTO> getMenuItemTypes() {
        ResponseEntity<MenuItemTypeDTO[]> types = restTemplate.getForEntity(GET_FIND_ALL_URL, MenuItemTypeDTO[].class);
        return Arrays.asList(Objects.requireNonNull(types.getBody()));
    }

    public MenuItemTypeDTO getMenuItemType(String name){
        String url = GET_FIND_BY_NAME_URL + name;
        ResponseEntity<MenuItemTypeDTO> type = restTemplate.getForEntity(url, MenuItemTypeDTO.class);
        if (HttpStatus.OK.equals(type.getStatusCode())) {
            return type.getBody();
        } else{
            return new MenuItemTypeDTO();
        }
    }
}
