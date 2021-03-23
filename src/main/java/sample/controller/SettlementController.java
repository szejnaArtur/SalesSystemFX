package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.dto.OrderItemDTO;
import sample.factory.PopupFactory;
import sample.rest.OrderItemRestClient;
import sample.table.OrderTableModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SettlementController implements Initializable {

    private final static String RESTAURANT_PANEL_FXML = "/fxml/restaurantPanel.fxml";
    private static final String ORDER_PANEL_FXML = "/fxml/orderPanel.fxml";
    private static final String TEXID_PANEL_FXML = "/fxml/texIDPanel.fxml";
    private static final String APP_TITLE = "POS Restaurant System";

    private String amount = "";
    private double paid = 0;

    private final PopupFactory popupFactory;
    private final OrderItemRestClient orderItemRestClient;

    @FXML
    private BorderPane settlementBorderPane;

    @FXML
    private Button cardButton;

    @FXML
    private Button payUButton;

    @FXML
    private Button sodexoButton;

    @FXML
    private Button texIDButton;

    @FXML
    private Button neighborButton;

    @FXML
    private Button employeeButton;

    @FXML
    private Button superServiceButton;

    @FXML
    private Button cashButton;

    @FXML
    private Button everyPennyButton;

    @FXML
    private Button sevenButton;

    @FXML
    private Button fourButton;

    @FXML
    private Button oneButton;

    @FXML
    private Button zeroButton;

    @FXML
    private Button eightButton;

    @FXML
    private Button fiveButton;

    @FXML
    private Button twoButton;

    @FXML
    private Button dotButton;

    @FXML
    private Button nineButton;

    @FXML
    private Button sixButton;

    @FXML
    private Button threeButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button hundredButton;

    @FXML
    private Button fiftyButton;

    @FXML
    private Button twentyButton;

    @FXML
    private Button tenButton;

    @FXML
    private Button closeButton;

    @FXML
    private Label toPayLabel;

    @FXML
    private Label restLabel;

    @FXML
    private Label paidLabel;

    @FXML
    private TextField countTextField;

    @FXML
    private Pane menuPane;

    @FXML
    private TableView<OrderTableModel> orderTableView;

    public SettlementController() {
        this.popupFactory = new PopupFactory();
        this.orderItemRestClient = new OrderItemRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeReturnButton();
        initializeBorderPane();
        initializeZeroButton();
        initializeOneButton();
        initializeTwoButton();
        initializeThreeButton();
        initializeFourButton();
        initializeFiveButton();
        initializeSixButton();
        initializeSevenButton();
        initializeEightButton();
        initializeNineButton();
        initializeDotButton();
        initializeClearButton();
        initializeCashButton();
        initializeCardButton();
        initializeEveryPennyButton();
        initializetexIDButton();
        initializeOrderTableView();
        initializeNeighborButton();
        initializeEmployeeButton();
        initializeSuperServiceButton();
    }

    private void initializeNeighborButton() {
        neighborButton.setOnAction(x -> discount(10));
    }

    private void initializeEmployeeButton() {
        employeeButton.setOnAction(x -> discount(50));
    }

    private void initializeSuperServiceButton() {
        superServiceButton.setOnAction(x -> discount(100));
    }

    private void discount(Integer percentDiscount) {
        OrderTableModel selectedItem = orderTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            List<OrderItemDTO> orderItemDTOList = StartController.orderItemDTOList;
            for (OrderItemDTO dto : orderItemDTOList) {
                if (dto.getMenuItemDTO().getName().equals(selectedItem.getItem())) {
                    if (dto.getAmount() >= 2) {
                        Stage spinnerPopup = popupFactory.createSpinnerPopup("How many items do you want to discount?",
                                dto, percentDiscount * 0.01, () -> {
                                    loadMenuOrderData();
                                    toPayLabel.setText(String.format("Total: %.2f PLN", StartController.getTotalPrice()));
                                });
                        spinnerPopup.show();
                    } else {
                        double discount = dto.getAmount() * dto.getMenuItemDTO().getPrice() * 0.10;
                        dto.setDiscount(discount);
                        loadMenuOrderData();
                        toPayLabel.setText(String.format("Total: %.2f PLN", StartController.getTotalPrice()));
                    }
                    break;
                }
            }
        } else {
            Stage infoPopup = popupFactory.createInfoPopup("No items have been selected.");
            infoPopup.show();
        }
    }

    private void initializetexIDButton() {
        texIDButton.setOnAction(x -> {
            try {
                Stage startPanelStage = new Stage();
                startPanelStage.initStyle(StageStyle.UNDECORATED);
                startPanelStage.initModality(Modality.APPLICATION_MODAL);
                Parent startPanelRoot = FXMLLoader.load(getClass().getResource(TEXID_PANEL_FXML));
                Scene scene = new Scene(startPanelRoot, 550, 850);
                startPanelStage.setTitle(APP_TITLE);
                startPanelStage.setScene(scene);
                startPanelStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeEveryPennyButton() {
        everyPennyButton.setOnAction(x -> {
            Double totalPrice = StartController.getTotalPrice();
            paidLabel.setText(String.format("Paid: %.2f PLN", totalPrice));
            amount = "";
            countTextField.setText(amount);
            restLabel.setText(String.format("Rest: %.2f PLN", 0.0));
            StartController.bill.setPaymentMethod("CASH");
            Stage infoPopup = popupFactory.createInfoPopup("Close the drawer", () -> {
                orderItemRestClient.saveOrderItems(StartController.orderItemDTOList);
                getStage().close();
                openOtherStageAndCloseSettlementStage(RESTAURANT_PANEL_FXML);
            });
            infoPopup.show();
        });
    }

    private void initializeCardButton() {
        cardButton.setOnAction(x -> {
            Double totalPrice = StartController.getTotalPrice();
            paidLabel.setText(String.format("Paid: %.2f PLN", totalPrice));
            StartController.bill.setPaymentMethod("CARD");
            Stage infoPopup = popupFactory.createInfoPopup("Ask for card insertion ...", () -> {
                if (totalPrice >= 100) {
                    Stage pinPopup = popupFactory.createInfoPopup("Ask for a PIN code...", () -> {
                        orderItemRestClient.saveOrderItems(StartController.orderItemDTOList);
                        getStage().close();
                        openOtherStageAndCloseSettlementStage(RESTAURANT_PANEL_FXML);
                    });
                    pinPopup.show();
                } else {
                    orderItemRestClient.saveOrderItems(StartController.orderItemDTOList);
                    getStage().close();
                    openOtherStageAndCloseSettlementStage(RESTAURANT_PANEL_FXML);
                }
            });
            infoPopup.show();
        });
    }

    private void initializeCashButton() {
        cashButton.setOnAction(x -> {
            if (!amount.equals("")) {
                double cash = Double.parseDouble(amount);
                paid += cash;

                paidLabel.setText(String.format("Paid: %.2f PLN", paid));
                Double totalPrice = StartController.getTotalPrice();
                amount = "";
                countTextField.setText(amount);
                if (paid > totalPrice) {
                    double rest = paid - totalPrice;
                    restLabel.setText(String.format("Rest: %.2f PLN", rest));
                    StartController.bill.setPaymentMethod("CASH");
                    Stage infoPopup = popupFactory.createInfoPopup(String.format("Spend the rest: %.2f PLN", rest), () -> {
                        orderItemRestClient.saveOrderItems(StartController.orderItemDTOList);
                        getStage().close();
                        openOtherStageAndCloseSettlementStage(RESTAURANT_PANEL_FXML);
                    });
                    infoPopup.show();
                }
            }
        });
    }

    private void initializeClearButton() {
        clearButton.setOnAction(x -> {
            amount = "";
            countTextField.setText(amount);
        });
    }

    private void initializeDotButton() {
        dotButton.setOnAction(x -> {
            amount = amount + ".";
            countTextField.setText(amount);
        });
    }


    private void initializeNineButton() {
        nineButton.setOnAction(x -> {
            amount = amount + "9";
            countTextField.setText(amount);
        });
    }


    private void initializeEightButton() {
        eightButton.setOnAction(x -> {
            amount = amount + "8";
            countTextField.setText(amount);
        });
    }


    private void initializeSevenButton() {
        sevenButton.setOnAction(x -> {
            amount = amount + "7";
            countTextField.setText(amount);
        });
    }

    private void initializeSixButton() {
        sixButton.setOnAction(x -> {
            amount = amount + "6";
            countTextField.setText(amount);
        });
    }

    private void initializeFiveButton() {
        fiveButton.setOnAction(x -> {
            amount = amount + "5";
            countTextField.setText(amount);
        });
    }

    private void initializeFourButton() {
        fourButton.setOnAction(x -> {
            amount = amount + "4";
            countTextField.setText(amount);
        });
    }

    private void initializeThreeButton() {
        threeButton.setOnAction(x -> {
            amount = amount + "3";
            countTextField.setText(amount);
        });
    }

    private void initializeTwoButton() {
        twoButton.setOnAction(x -> {
            amount = amount + "2";
            countTextField.setText(amount);
        });
    }

    private void initializeOneButton() {
        oneButton.setOnAction(x -> {
            amount = amount + "1";
            countTextField.setText(amount);
        });
    }

    private void initializeZeroButton() {
        zeroButton.setOnAction(x -> {
            amount = amount + "0";
            countTextField.setText(amount);
        });
    }

    private void initializeBorderPane() {
        toPayLabel.setText(String.format("To paid: %.2f PLN", StartController.getTotalPrice()));
    }


    private void initializeReturnButton() {
        closeButton.setOnAction(x -> {
            openOtherStageAndCloseSettlementStage(ORDER_PANEL_FXML);
            getStage().close();
        });
    }

    private void openOtherStageAndCloseSettlementStage(String FXML_NAME) {
        try {
            Stage startPanelStage = new Stage();
            Parent startPanelRoot = FXMLLoader.load(getClass().getResource(FXML_NAME));
            Scene scene = new Scene(startPanelRoot, 1920, 1000);
            startPanelStage.setTitle(APP_TITLE);
            startPanelStage.setFullScreen(true);
            startPanelStage.setScene(scene);
            startPanelStage.show();
            getStage().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeOrderTableView() {
        orderTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(220);
        nameColumn.setCellValueFactory(new PropertyValueFactory<OrderTableModel, String>("item"));

        TableColumn quantityColumn = new TableColumn("Quantity");
        quantityColumn.setMinWidth(90);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderTableModel, Integer>("quantity"));

        TableColumn priceColumn = new TableColumn("Price");
        priceColumn.setMinWidth(90);
        priceColumn.setCellValueFactory(new PropertyValueFactory<OrderTableModel, Double>("price"));

        TableColumn totalColumn = new TableColumn("Total");
        totalColumn.setMinWidth(90);
        totalColumn.setCellValueFactory(new PropertyValueFactory<OrderTableModel, Double>("total"));

        TableColumn discountColumn = new TableColumn("Discount");
        discountColumn.setMinWidth(90);
        discountColumn.setCellValueFactory(new PropertyValueFactory<OrderTableModel, Double>("discount"));

        orderTableView.getColumns().addAll(nameColumn, quantityColumn, priceColumn, totalColumn, discountColumn);

        loadMenuOrderData();

        orderTableView.setItems(StartController.data);
    }

    private void loadMenuOrderData() {
        Thread thread = new Thread(() -> {
            StartController.data.clear();
            StartController.data.addAll(StartController.orderItemDTOList.stream().map(OrderTableModel::of).collect(Collectors.toList()));
        });
        thread.start();
    }

    private Stage getStage() {
        return (Stage) settlementBorderPane.getScene().getWindow();
    }
}
