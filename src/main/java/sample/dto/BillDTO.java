package sample.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BillDTO {

    private Long idBill;
    private LocalDateTime orderDate;
    private LocalDateTime pickUpDate;
    private EmployeeDTO employeeDTO;
    private String paymentMethod;
    private Long texID;
    private Double cashPaymentAmount;
    private Double cardPaymentAmount;
    private Double payUPaymentAmount;
    private Double sodexoPaymentAmount;

}
