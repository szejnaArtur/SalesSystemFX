package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.dto.AverageGuestCheckDTO;
import sample.rest.AverageGuestCheckRestClient;
import sample.table.AGCRaportTableModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AGCRaportController implements Initializable {

    private final AverageGuestCheckRestClient averageGuestCheckRestClient;

    private final ObservableList<AGCRaportTableModel> data;

    @FXML
    private TableView<AGCRaportTableModel> agcTableView;

    public AGCRaportController() {
        this.averageGuestCheckRestClient = new AverageGuestCheckRestClient();
        this.data = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeAGCTableView();
    }

    private void initializeAGCTableView() {
        agcTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn employeeColumn = new TableColumn("Employee");
        employeeColumn.setMinWidth(500);
        employeeColumn.setCellValueFactory(new PropertyValueFactory<AGCRaportTableModel, String>("name"));

        TableColumn agcColumn = new TableColumn("AGC");
        agcColumn.setMinWidth(500);
        agcColumn.setCellValueFactory(new PropertyValueFactory<AGCRaportTableModel, Double>("amount"));

        agcTableView.getColumns().addAll(employeeColumn, agcColumn);
        loadMenuItemData();
        agcTableView.setItems(data);
    }

    private void loadMenuItemData() {
        Thread thread = new Thread(() -> {
            List<AverageGuestCheckDTO> averageGuestCheckDTOList = averageGuestCheckRestClient.getAGC();
            data.clear();
            data.addAll(averageGuestCheckDTOList.stream().map(AGCRaportTableModel::of).collect(Collectors.toList()));
        });
        thread.start();
    }
}
