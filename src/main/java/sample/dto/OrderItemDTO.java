package sample.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderItemDTO {

    private Long idOrderItem;
    private Integer amount;
    private MenuItemDTO menuItemDTO;
    private List<OrderAddonDTO> orderAddonDTOList;
    private BillDTO billDTO;
    private Double discount;

    public OrderItemDTO(MenuItemDTO menuItemDTO) {
        this.menuItemDTO = menuItemDTO;
        this.amount = 1;
        this.discount = 0.0;
        this.orderAddonDTOList = new ArrayList<>();
    }

    public void increaseTheQuantity() {
        setAmount(getAmount() + 1);
    }
}
