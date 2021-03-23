package sample.factory;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.controller.StartController;
import sample.dto.OrderItemDTO;
import sample.handler.InfoPopupButtonHandler;

public class PopupFactory {

    public Stage createWaitingPopup(String text) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        ProgressBar progressBar = new ProgressBar();

        stage.setScene(new Scene(createVBox(createLabel(text), progressBar), 550, 300));
        stage.initModality(Modality.APPLICATION_MODAL);

        return stage;
    }

    private VBox createVBox(Label label, ProgressBar progressBar) {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        pane.setStyle(waitingPopupPaneStyle());
        pane.getChildren().addAll(label, progressBar);
        return pane;
    }

    private VBox createVBox(Label label, Button button) {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        pane.setStyle(waitingPopupPaneStyle());
        pane.getChildren().addAll(label, button);
        return pane;
    }

    private VBox createVBox(Label label, Spinner spinner, Button button) {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        pane.setStyle(waitingPopupPaneStyle());
        pane.getChildren().addAll(label, spinner, button);
        return pane;
    }

    private VBox createVBox(Label label, Button yesButton, Button noButton) {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        pane.setStyle(waitingPopupPaneStyle());
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.getChildren().addAll(yesButton, noButton);
        pane.getChildren().addAll(label, hBox);
        return pane;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle(waitingLabelStyle());
        label.setFont(Font.font(36));
        return label;
    }

    private String waitingLabelStyle() {
        return "-fx-text-fill: #003366;"
                ;
    }

    private String waitingPopupPaneStyle() {
        return "-fx-background-color: #c7c7c7;" +
                "-fx-border-color: #003366;";
    }

    private String okButtonStyle() {
        return ".login-button{\n" +
                "    -fx-text-fill: #003366;\n" +
                "    -fx-background-color: #c7c7c7;\n" +
                "    -fx-border-color: #003366;\n" +
                "    -fx-background-radius: 0;\n" +
                "}";
    }

    private String okButtonHoverStyle() {
        return ".login-button{\n" +
                "    -fx-text-fill: #003366;\n" +
                "    -fx-background-color: #e1e1e1;\n" +
                "    -fx-border-color: #003366;\n" +
                "    -fx-background-radius: 0;\n" +
                "}";
    }

    public Stage createInfoPopup(String text, InfoPopupButtonHandler handler) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(new Scene(createVBox(createLabel(text), getButton(handler, "Ok", stage)), 550, 300));
        stage.initModality(Modality.APPLICATION_MODAL);

        return stage;
    }

    public Stage createInfoPopup(String text) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(new Scene(createVBox(createLabel(text), getButton("Ok", stage)), 550, 300));
        stage.initModality(Modality.APPLICATION_MODAL);

        return stage;
    }

    public Stage createInfoPopupWithTwoButtons(String text, InfoPopupButtonHandler yesHandler, InfoPopupButtonHandler noHandler) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(new Scene(createVBox(
                createLabel(text), getButton(yesHandler, "Yes", stage),
                getButton(noHandler, "No", stage)), 620, 300));
        stage.initModality(Modality.APPLICATION_MODAL);

        return stage;
    }

    private Button getButton(InfoPopupButtonHandler handler, String text, Stage stage) {
        Button button = new Button(text);
        button.setStyle(okButtonStyle());
        button.setPrefSize(200, 80);
        button.setOnMouseEntered(x -> button.setStyle(okButtonHoverStyle()));
        button.setOnMouseExited(x -> button.setStyle(okButtonStyle()));
        button.setOnAction(x -> {
            stage.close();
            handler.handle();
        });
        return button;
    }

    private Button getButton(String text, Stage stage) {
        Button button = new Button(text);
        button.setStyle(okButtonStyle());
        button.setPrefSize(200, 80);
        button.setOnMouseEntered(x -> button.setStyle(okButtonHoverStyle()));
        button.setOnMouseExited(x -> button.setStyle(okButtonStyle()));
        button.setOnAction(x -> stage.close());
        return button;
    }

    public Stage createSpinnerPopup(String text, OrderItemDTO dto, Double discount, InfoPopupButtonHandler handler) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        ProgressBar progressBar = new ProgressBar();

        Spinner<Integer> spinner = new Spinner<>();
        SpinnerValueFactory<Integer> spinnerValueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, dto.getAmount(), dto.getAmount());
        spinner.setValueFactory(spinnerValueFactory);

        Button button = new Button("Ok");
        button.setStyle(okButtonStyle());
        button.setPrefSize(200, 80);
        button.setOnMouseEntered(x -> button.setStyle(okButtonHoverStyle()));
        button.setOnMouseExited(x -> button.setStyle(okButtonStyle()));
        button.setOnAction(x -> {
            stage.close();
            for (OrderItemDTO item : StartController.orderItemDTOList){
                if(dto.getMenuItemDTO().getName().equals(item.getMenuItemDTO().getName())){
                    item.setDiscount(spinner.getValue() * dto.getMenuItemDTO().getPrice() * discount);
                }
            }
            handler.handle();
        });
        stage.setScene(new Scene(createVBox(createLabel(text), spinner, button), 700, 300));
        stage.initModality(Modality.APPLICATION_MODAL);

        return stage;
    }
}
