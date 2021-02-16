package sample.dto;

import lombok.Data;

@Data
public class OrderItemDTO {

    private Long idOrderItem;
    private MenuItemDTO menuItemDTO;
    private OrderBillDTO orderBillDTO;
    private Integer quantity;

    public OrderItemDTO(MenuItemDTO menuItemDTO) {
        this.menuItemDTO = menuItemDTO;
        this.quantity = 1;
    }

    public void increaseTheQuantity() {
        setQuantity(getQuantity() + 1);
    }
}
