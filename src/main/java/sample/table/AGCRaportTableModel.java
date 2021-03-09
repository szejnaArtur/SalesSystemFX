package sample.table;

import javafx.beans.property.SimpleStringProperty;
import sample.dto.AverageGuestCheckDTO;

public class AGCRaportTableModel {

    private final SimpleStringProperty name;
    private final SimpleStringProperty amount;

    public AGCRaportTableModel(String firsName, String lastName, Double amount) {
        String name = firsName + " " + lastName;
        this.name = new SimpleStringProperty(name);
        String average = String.format("%.2f", amount);
        this.amount = new SimpleStringProperty(average);
    }

    public static AGCRaportTableModel of(AverageGuestCheckDTO averageGuestCheckDTO) {
        return new AGCRaportTableModel(averageGuestCheckDTO.getEmployeeDTO().getFirstName(),
                averageGuestCheckDTO.getEmployeeDTO().getLastName(),
                averageGuestCheckDTO.getAmount() / averageGuestCheckDTO.getNumberOfTransactions());

    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAmount() {
        return amount.get();
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }
}
