package sample.dto;

import lombok.Data;

@Data
public class OrderAddonDTO {

    private Long idOrderAddon;
    private Integer amount;
    private AddonDTO addonDTO;
    private OrderItemDTO orderItemDTO;

    public OrderAddonDTO(AddonDTO addonDTO, OrderItemDTO orderItemDTO) {
        this.addonDTO = addonDTO;
        this.amount = 1;
        this.orderItemDTO = orderItemDTO;
    }

    public void increaseTheQuantity() {
        setAmount(getAmount() + 1);
    }
}
