package sample.table;

import javafx.beans.property.SimpleStringProperty;
import sample.dto.OrderItemDTO;

public class OrderTableModel {

    private final SimpleStringProperty item;
    private final SimpleStringProperty price;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty total;

    public OrderTableModel(String item, Double price, Integer quantity) {
        this.item = new SimpleStringProperty(item);
        this.price = new SimpleStringProperty(Double.toString(price));
        this.quantity = new SimpleStringProperty(Integer.toString(quantity));
        String totalPrice = String.format("%.2f", price*quantity);
        this.total = new SimpleStringProperty(totalPrice);
    }

    public static OrderTableModel of(OrderItemDTO orderItemDTO){
        return new OrderTableModel(orderItemDTO.getMenuItemDTO().getName(),
                orderItemDTO.getMenuItemDTO().getPrice(),
                orderItemDTO.getAmount());
    }

    public String getItem() {
        return item.get();
    }

    public SimpleStringProperty itemProperty() {
        return item;
    }

    public void setItem(String item) {
        this.item.set(item);
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

    public String getQuantity() {
        return quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getTotal() {
        return total.get();
    }

    public SimpleStringProperty totalProperty() {
        return total;
    }

    public void setTotal(String total) {
        this.total.set(total);
    }
}
