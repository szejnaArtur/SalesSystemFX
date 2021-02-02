package sample.controller;

import lombok.Data;
import sample.dto.EmployeeDTO;
import sample.dto.MenuItemDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class StartController {

    public static EmployeeDTO employeeDTO;

    public static List<MenuItemDTO> menuItemsDTO = new ArrayList<>();

}
