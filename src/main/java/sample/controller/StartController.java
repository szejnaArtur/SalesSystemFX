package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import sample.dto.*;
import sample.table.OrderTableModel;

import java.util.ArrayList;
import java.util.List;

@Data
public class StartController {

    public static EmployeeDTO employeeDTO;

    public static List<MenuItemDTO> menuItemsDTO = new ArrayList<>();

    public static BillDTO bill;
    public static List<OrderItemDTO> orderItemDTOList;
    public static List<OrderAddonDTO> orderAddonDTOList;

    public static ObservableList<OrderTableModel> data = FXCollections.observableArrayList();

    public static Double getTotalPrice() {
        double total = 0.0;
        for (OrderItemDTO orderItem : orderItemDTOList) {
            total += (orderItem.getAmount() * orderItem.getMenuItemDTO().getPrice()) - orderItem.getDiscount();
            for (OrderAddonDTO orderAddon : StartController.orderAddonDTOList){
                total += orderAddon.getAmount() * orderAddon.getAddonDTO().getPrice();
            }
        }
        return total;
    }

    public static Double leftToPay(){
        return getTotalPrice() - bill.howMuchWasPaid();
    }
}
