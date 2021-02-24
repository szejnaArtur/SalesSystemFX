package sample.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BillDTO {

    private Long idBill;
    private LocalDateTime orderDate;
    private LocalDateTime pickUpDate;

}
