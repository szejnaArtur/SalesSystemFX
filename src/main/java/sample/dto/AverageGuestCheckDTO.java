package sample.dto;

import lombok.Data;

@Data
public class AverageGuestCheckDTO {

    private EmployeeDTO employeeDTO;
    private Integer numberOfTransactions;
    private Double amount;

}
