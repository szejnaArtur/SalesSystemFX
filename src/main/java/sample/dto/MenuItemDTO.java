package sample.dto;

import lombok.Data;

@Data
public class MenuItemDTO {

    private Long idMenuItem;
    private String name;
    private Double price;
    private Integer kcal;
    private String description;
    private MenuItemTypeDTO type;

    public static MenuItemDTO of(String name, Double price, Integer kcal, MenuItemTypeDTO type){
        MenuItemDTO dto = new MenuItemDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setKcal(kcal);
        dto.setType(type);
        return dto;
    }

    public static MenuItemDTO of(String name, Double price, Integer kcal, MenuItemTypeDTO type, String description){
        MenuItemDTO dto = new MenuItemDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setKcal(kcal);
        dto.setType(type);
        dto.setDescription(description);
        return dto;
    }

}
