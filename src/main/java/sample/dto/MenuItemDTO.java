package sample.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuItemDTO {

    private Long idMenuItem;
    private String name;
    private Double price;
    private Integer kcal;
    private String description;
    private MenuItemTypeDTO type;
    private List<AddonDTO> addons;

    public static MenuItemDTO of(String name, Double price, Integer kcal, MenuItemTypeDTO type, List<AddonDTO> addons) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setKcal(kcal);
        dto.setType(type);
        dto.setAddons(addons);
        return dto;
    }

    public static MenuItemDTO of(String name, Double price, Integer kcal, MenuItemTypeDTO type, String description,
                                 List<AddonDTO> addons) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setKcal(kcal);
        dto.setType(type);
        dto.setDescription(description);
        dto.setAddons(addons);
        return dto;
    }

}
