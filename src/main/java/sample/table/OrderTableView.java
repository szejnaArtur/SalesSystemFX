package sample.table;

import javafx.beans.property.SimpleStringProperty;
import sample.dto.MenuItemDTO;
import sample.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderTableView {

    private final SimpleStringProperty item;
    private final SimpleStringProperty price;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty total;

    public OrderTableView(String item, Double price, Integer quantity) {
        this.item = new SimpleStringProperty(item);
        this.price = new SimpleStringProperty(Double.toString(price));
        this.quantity = new SimpleStringProperty(Integer.toString(quantity));
        this.total = new SimpleStringProperty(Double.toString(price * quantity));
    }

    public static OrderTableView of(String item, Double price, Integer quantity){
        return new OrderTableView(item, price, quantity);
    }
}
