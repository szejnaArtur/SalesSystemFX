package sample.table;

import javafx.beans.property.SimpleStringProperty;
import sample.dto.MenuItemDTO;
import sample.dto.MenuItemTypeDTO;

public class MenuItemTableModel {

    private final Long idMenuItem;
    private final SimpleStringProperty name;
    private final SimpleStringProperty price;
    private final SimpleStringProperty kcal;
    private final SimpleStringProperty type;

    public MenuItemTableModel(Long idMenuItem, String name, Double price, Integer kcal, MenuItemTypeDTO type){
        this.idMenuItem = idMenuItem;
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type.getName());
        this.price = new SimpleStringProperty(price.toString());
        this.kcal = new SimpleStringProperty(kcal.toString());
    }

    public static MenuItemTableModel of(MenuItemDTO menuItemDto){
     return new MenuItemTableModel(menuItemDto.getIdMenuItem(), menuItemDto.getName(), menuItemDto.getPrice(),
             menuItemDto.getKcal(), menuItemDto.getType());
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getKcal() {
        return kcal.get();
    }

    public SimpleStringProperty kcalProperty() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal.set(kcal);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public Long getIdMenuItem() {
        return idMenuItem;
    }
}
