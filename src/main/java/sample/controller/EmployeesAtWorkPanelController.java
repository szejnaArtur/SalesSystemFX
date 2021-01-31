package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.dto.EmployeeDTO;
import sample.rest.EmployeeRestClient;
import sample.table.EmployeeTableModel;
import sample.table.MenuItemTableModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EmployeesAtWorkPanelController implements Initializable {

    private final EmployeeRestClient employeeRestClient;

    private final ObservableList<EmployeeTableModel> data;

    @FXML
    private AnchorPane employeeAtWorkAnchorPane;

    @FXML
    private TableView<EmployeeTableModel> employeeTableView;

    @FXML
    private Button cancelButton;

    public EmployeesAtWorkPanelController() {
        this.employeeRestClient = new EmployeeRestClient();
        this.data = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCancelButton();
        initializeEmployeeTableView();
    }

    private void initializeCancelButton() {
        cancelButton.setOnAction(x -> getStage().close());
    }

    private void initializeEmployeeTableView() {
        employeeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn nameColumn = new TableColumn("Employee");
        nameColumn.setMinWidth(1000);
        nameColumn.setCellValueFactory(new PropertyValueFactory<MenuItemTableModel, String>("employee"));

        employeeTableView.getColumns().addAll(nameColumn);

        loadMenuItemData();

        employeeTableView.setItems(data);
    }

    private void loadMenuItemData() {
        Thread thread = new Thread(() -> {
            List<EmployeeDTO> employees = employeeRestClient.getEmployeesAtWork();
            data.clear();
            data.addAll(employees.stream().map(EmployeeTableModel::of).collect(Collectors.toList()));
        });
        thread.start();
    }

    private Stage getStage() {
        return (Stage) employeeAtWorkAnchorPane.getScene().getWindow();
    }
}
