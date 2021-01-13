package sample.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.MenuItemDto;
import sample.handler.SavedMenuItemHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuItemRestClient {

    private static final String GET_FINDALL_URL = "http://localhost:8080/menuItems/findAll";
    private static final String POST_ADD_URL = "http://localhost:8080/menuItems/add";
    private static final String GET_FIND_URL = "http://localhost:8080/menuItems/find/";

    private final RestTemplate restTemplate;

    public MenuItemRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<MenuItemDto> getMenuItems() {
        ResponseEntity<MenuItemDto[]> menuItems = restTemplate.getForEntity(GET_FINDALL_URL, MenuItemDto[].class);
        return Arrays.asList(Objects.requireNonNull(menuItems.getBody()));
    }

    public void saveMenuItem(MenuItemDto menuItemDto, SavedMenuItemHandler handler) {
        ResponseEntity<MenuItemDto> responseEntity = restTemplate.postForEntity(POST_ADD_URL, menuItemDto, MenuItemDto.class);
        if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
            handler.handle();
        } else {
            //TODO implement
        }
    }

    public MenuItemDto getMenuItem(Long idMenuItem) {
        String url = "http://localhost:8080/menuItems/find/" + idMenuItem;
        ResponseEntity<MenuItemDto> responseEntity = restTemplate.getForEntity(url, MenuItemDto.class);
        if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
            return responseEntity.getBody();
        } else {
            //TODO implement
            throw new RuntimeException("Can't load Menu item.");
        }
    }
}
