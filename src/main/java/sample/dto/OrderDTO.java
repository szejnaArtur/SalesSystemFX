package sample.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {

    private Long idOrder;
    private List<OrderItemDTO> orderItems = new ArrayList<>();
    private LocalDateTime orderDate;
    private LocalDateTime pickUpDate;

    public void addOrderItemToOrder(OrderItemDTO orderItemDTO){
        orderItems.add(orderItemDTO);
    }

    public boolean isOrderItem(MenuItemDTO menuItem){
        for (OrderItemDTO orderItem : orderItems){
            if (menuItem.getName().equals(orderItem.getMenuItemDTO().getName())){
                return true;
            }
        }
        return false;
    }

    public boolean isOrderNull(){
        return orderItems.size() == 0;
    }

}
