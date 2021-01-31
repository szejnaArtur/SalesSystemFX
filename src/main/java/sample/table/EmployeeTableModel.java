package sample.table;

import javafx.beans.property.SimpleStringProperty;
import sample.dto.EmployeeDTO;

public class EmployeeTableModel {

    private final Long idEmployee;
    private final SimpleStringProperty employee;

    public EmployeeTableModel(Long idEmployee, String employee){
        this.idEmployee = idEmployee;
        this.employee = new SimpleStringProperty(employee);
    }

    public static EmployeeTableModel of(EmployeeDTO employeeDTO){
        String employeeName = employeeDTO.getFirstName()+" "+employeeDTO.getLastName();
        return new EmployeeTableModel(employeeDTO.getIdEmployee(), employeeName);
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public String getEmployee() {
        return employee.get();
    }

    public SimpleStringProperty employeeProperty() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee.set(employee);
    }
}
