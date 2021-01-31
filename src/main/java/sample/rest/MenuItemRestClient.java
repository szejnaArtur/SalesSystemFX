package sample.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.MenuItemDTO;
import sample.handler.DeletedMenuItemHandler;
import sample.handler.SavedMenuItemHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuItemRestClient {

    private static final String GET_FINDALL_URL = "http://localhost:8080/menuItems/findAll";
    private static final String POST_ADD_URL = "http://localhost:8080/menuItems/add";
    private static final String GET_FIND_URL = "http://localhost:8080/menuItems/find/";
    private static final String DELETE_FIND_URL = "http://localhost:8080/menuItems/delete/";

    private final RestTemplate restTemplate;

    public MenuItemRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<MenuItemDTO> getMenuItems() {
        ResponseEntity<MenuItemDTO[]> menuItems = restTemplate.getForEntity(GET_FINDALL_URL, MenuItemDTO[].class);
        return Arrays.asList(Objects.requireNonNull(menuItems.getBody()));
    }

    public void saveMenuItem(MenuItemDTO menuItemDto, SavedMenuItemHandler handler) {
        ResponseEntity<MenuItemDTO> responseEntity = restTemplate.postForEntity(POST_ADD_URL, menuItemDto, MenuItemDTO.class);
        if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
            handler.handle();
        }
    }

    public MenuItemDTO getMenuItem(Long idMenuItem) {
        String url = "http://localhost:8080/menuItems/find/" + idMenuItem;
        ResponseEntity<MenuItemDTO> responseEntity = restTemplate.getForEntity(url, MenuItemDTO.class);
        if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
            return responseEntity.getBody();
        } else {
            //TODO implement
            throw new RuntimeException("Can't load Menu item.");
        }
    }

    public void deleteMenuItem(Long idMenuItem, DeletedMenuItemHandler handler) {
        restTemplate.delete(DELETE_FIND_URL + idMenuItem);
        handler.handle();
    }
}
