package sample.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BillDTO {

    private Long idBill;
    private LocalDateTime orderDate;
    private LocalDateTime pickUpDate;
    private EmployeeDTO employeeDTO;
    private Long texID;
    private Double cashPaymentAmount;
    private Double cardPaymentAmount;
    private Double payUPaymentAmount;
    private Double sodexoPaymentAmount;

    public BillDTO(){
        this.cashPaymentAmount = 0.0;
        this.cardPaymentAmount = 0.0;
        this.payUPaymentAmount = 0.0;
        this.sodexoPaymentAmount = 0.0;
    }

    public Double howMuchWasPaid(){
        return cardPaymentAmount + cashPaymentAmount + payUPaymentAmount + sodexoPaymentAmount;
    }

    public Double howMuchWasSodexo(){
        return cardPaymentAmount + cashPaymentAmount + payUPaymentAmount + sodexoPaymentAmount;
    }

}
