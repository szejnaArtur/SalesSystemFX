package sample.dto;

import lombok.Data;

@Data
public class MenuItemDTO {

    private Long idMenuItem;
    private String name;
    private Double price;
    private Integer kcal;
    private String description;
    private String type;

    public static MenuItemDTO of(String name, Double price, Integer kcal, String type){
        MenuItemDTO dto = new MenuItemDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setKcal(kcal);
        dto.setType(type);
        return dto;
    }

    public static MenuItemDTO of(String name, Double price, Integer kcal, String type, String description){
        MenuItemDTO dto = new MenuItemDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setKcal(kcal);
        dto.setType(type);
        dto.setDescription(description);
        return dto;
    }

}
