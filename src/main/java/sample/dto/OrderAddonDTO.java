package sample.dto;

import lombok.Data;

@Data
public class OrderAddonDTO {

    private Long idOrderItem;
    private Integer amount;
    private AddonDTO addonDTO;

    public OrderAddonDTO(AddonDTO addonDTO) {
        this.addonDTO = addonDTO;
        this.amount = 1;
    }

    public void increaseTheQuantity() {
        setAmount(getAmount() + 1);
    }
}
