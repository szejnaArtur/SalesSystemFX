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

    public MenuItemDto(String name, Double price, Integer kcal, String type){
        this.setName(name);
        this.setPrice(price);
        this.setKcal(kcal);
        this.setType(type);
    }

}
