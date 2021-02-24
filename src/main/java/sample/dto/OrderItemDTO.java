package sample.dto;

import lombok.Data;

@Data
public class OrderItemDTO {

    private Long idOrderItem;
    private Integer amount;
    private MenuItemDTO menuItemDTO;
    private BillDTO billDTO;

    public OrderItemDTO(MenuItemDTO menuItemDTO) {
        this.menuItemDTO = menuItemDTO;
        this.amount = 1;
    }

    public void increaseTheQuantity() {
        setAmount(getAmount() + 1);
    }
}
