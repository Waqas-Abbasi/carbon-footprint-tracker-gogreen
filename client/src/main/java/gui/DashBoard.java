package gui;

import com.jfoenix.controls.JFXComboBox;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashBoard extends Application implements Initializable {
    @FXML
    Pane targetPane;
    @FXML
    VBox sidebar;
    @FXML
    Button myActivities;
    @FXML
    Button closeButton;
    @FXML
    JFXComboBox activities;
    String[] activity = {"Eating a vegetarian meal", "Buying local produce",
            "Personal Transport", "Public Transport",
            "Lowering the temperature of your home", "Installing solar panels",};

    SceneManager manager = new SceneManager();


    /**
     * button click registration.
     *
     * @param event event
     * @throws Exception exception
     */
    public void onMyActivitiesButtonClicked(ActionEvent event) throws Exception {
        manager.loadStageWithChild(event, "/dashBoard.fxml", "/myActivities.fxml");
    }

    /**
     * sets close button.
     */
    @FXML
    public void setCloseButton() {
        ClientConnections cc = new ClientConnections();
        cc.sendGet("destroy");
        System.exit(0);
    }

    /**
     * instantiates url.
     *
     * @param url            url to instantiate
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activities.getItems().removeAll(activities.getItems());
        activities.getItems().addAll(activity);
    }

    /**
     * starts from given stage.
     *
     * @param stage stage to start from
     * @throws Exception Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        manager.loadStage(stage, "/dashBoard.fxml");
        stage.setOnCloseRequest(t -> {
            Platform.exit();
            ClientConnections cc = new ClientConnections();
            cc.sendGet("destroy");
            System.exit(0);
        });
    }

    /**
     * this method is fired when someone clicks the scoreboard button.
     *
     * @param event the event
     * @throws Exception exception
     */

    @FXML
    public void chooseLeaderboard(ActionEvent event) throws Exception {
        manager.loadStageWithChild(event, "/dashBoard.fxml", "/scoreboard.fxml");
    }
//    @FXML
//    Pane targetPane;
//    @FXML
//    VBox sidebar;
//    @FXML
//    Button myActivities;
//    @FXML
//    Button closeButton;
//    @FXML
//    JFXComboBox<Object> activities;
//    private String[] activity = {"Eating a vegetarian meal", "Buying local produce",
//            "Personal Transport", "Public Transport",
//            "Lowering the temperature of your home", "Installing solar panels",};
//
//    private SceneManager manager = new SceneManager();
//
//
//    public void onMyActivitiesButtonClicked(ActionEvent event) throws Exception {
//        manager.loadStageWithChild(event, "/dashBoard.fxml", "/myActivities.fxml");
//    }
//
//    @FXML
//    public void setCloseButton() {
//        ClientConnections cc = new ClientConnections();
//        cc.sendGet("destroy");
//        System.exit(0);
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        activities.getItems().removeAll(activities.getItems());
//        activities.getItems().addAll((Object[]) activity);
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        manager.loadStage(stage, "/dashBoard.fxml");
//        stage.setOnCloseRequest(t -> {
//            Platform.exit();
//            ClientConnections cc = new ClientConnections();
//            cc.sendGet("destroy");
//            System.exit(0);
//        });
//    }
    /**
     * load the page which the user choose.
     *
     * @param event event
     * @throws Exception exception
     */
    @FXML
    public void loadPage(ActionEvent event) throws Exception {
        String page = activities.getSelectionModel().getSelectedItem().toString();
        switch (page) {
            case "Eating a vegetarian meal":
                manager.loadStageWithChild(event, "/dashBoard.fxml", "/addNewMeal.fxml");
                break;
            case "Personal Transport":
                manager.loadStageWithChild(event, "/dashBoard.fxml", "/bike.fxml");
                break;
            case "Buying local produce":
                manager.loadStageWithChild(event, "/dashBoard.fxml", "/localProduct.fxml");
                break;
            case "Public Transport":
                manager.loadStageWithChild(event, "/dashBoard.fxml", "/publicTransport.fxml");
                break;
            case "Lowering the temperature of your home":
                manager.loadStageWithChild(event, "/dashBoard.fxml", "/loweringTemp.fxml");
                break;
            case "Installing solar panels":
                manager.loadStageWithChild(event, "/dashBoard.fxml", "/solarPanels.fxml");
                break;
            default:
                break;
        }
    }
    /**
     * responds to event add friend.
     *
     * @param event ActionEvent
     * @throws Exception Exception to throw
     */
    @FXML
    public void chooseMyBadges(ActionEvent event) throws Exception {
        manager.loadStageWithChild(event, "/dashBoard.fxml", "/myBadges.fxml");
    }

    /**
     * if add friend is selected.
     * @param event action event
     * @throws Exception exception to throw
     */
    @FXML
    public void chooseAddFriend(ActionEvent event) throws Exception {
        manager.loadStageWithChild(event, "/dashBoard.fxml", "/addFriend.fxml");
    }

}
