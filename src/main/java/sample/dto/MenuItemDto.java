package sample.dto;

import lombok.Data;

@Data
public class MenuItemDto {

    private Long idMenuItem;
    private String name;
    private Double price;
    private Integer kcal;
    private String description;
    private String type;

    public static MenuItemDto of(String name, Double price, Integer kcal, String type){
        MenuItemDto dto = new MenuItemDto();
        dto.setName(name);
        dto.setPrice(price);
        dto.setKcal(kcal);
        dto.setType(type);
        return dto;
    }

    public static MenuItemDto of(String name, Double price, Integer kcal, String type, String description){
        MenuItemDto dto = new MenuItemDto();
        dto.setName(name);
        dto.setPrice(price);
        dto.setKcal(kcal);
        dto.setType(type);
        dto.setDescription(description);
        return dto;
    }

}
