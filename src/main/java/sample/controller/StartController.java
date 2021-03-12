package sample.controller;

import lombok.Data;
import sample.dto.BillDTO;
import sample.dto.EmployeeDTO;
import sample.dto.MenuItemDTO;
import sample.dto.OrderItemDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class StartController {

    public static EmployeeDTO employeeDTO;

    public static List<MenuItemDTO> menuItemsDTO = new ArrayList<>();

    public static BillDTO bill;
    public static List<OrderItemDTO> orderItemDTOList;

    public static Double getTotalPrice() {
        double total = 0.0;
        for (OrderItemDTO orderItem : orderItemDTOList) {
            total += orderItem.getAmount() * orderItem.getMenuItemDTO().getPrice();
        }
        return total;
    }
}
