
package gui;

import com.jfoenix.controls.JFXTextField;

import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

import gui.ClientConnections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bike extends Base implements Initializable {
    @FXML
    JFXTextField distance;
    @FXML
    Button submitButton;
    @FXML
    Label distanceLabel;
    @FXML
    Label tips;
    @FXML
    ImageView image;
    @FXML
    ComboBox mode;

    String[] modeBox = {"Car", "Electric car", "Motor bike", "Hybrid car", "Scooter", "Bike"};

    /**
     * this method is to check whether the text filed is empty or not and is integer
     * or not.
     *
     * @param event event
     * @return boolean value
     */
    @FXML
    private boolean checkTheTextFiled(ActionEvent event) {
        if (distance.getText().isEmpty()) {
            tips.setText("Please Enter Your Data");
            return false;
        }
        if (!distance.getText().matches("[0-9]+")) {
            tips.setText("Please Enter Integer");
            return false;
        }
        return true;
    }

    /**
     * this method is to set the text when the user types in the text field.
     */
    @FXML
    public void setText() {
        distanceLabel.setText(distance.getText());
    }

    /**
     * the method for submit button and refresh the page.
     *
     * @param event event
     * @throws Exception Exception
     */
    @FXML
    public void submitData(ActionEvent event) throws Exception {

        if (checkTheTextFiled(event)) {
            String type = mode.getSelectionModel().getSelectedItem().toString();
            ClientConnections clientConnections = new ClientConnections();
            clientConnections.sendPost(sendData(distance.getText(), type), "/addpublictransport");
            refreshStage(event);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mode.getItems().removeAll(mode.getItems());
        mode.getItems().addAll(modeBox);
        mode.getSelectionModel().select(0);
        image.setImage(new Image("/images/car.png"));
    }

    /**
     * Sends the data to the server.
     *
     * @param distance the traveled distance
     * @param carType  the car type
     * @return a string
     * @throws IllegalArgumentException if the distance or carType is null or empty
     */
    public String sendData(String distance, String carType) throws IllegalArgumentException {

        if (distance == null || distance.isEmpty() || carType == null || carType.isEmpty()) {
            throw new IllegalArgumentException();
        }

        JSONObject obj = new JSONObject();
        obj.put("distance", distance);
        obj.put("carType", carType);

        JsonManager js = new JsonManager();

        return js.addDate(obj);
    }

    /**
     * change the pic when the user choose different pic.
     */
    public void setImage() {
        String selection = mode.getSelectionModel().getSelectedItem().toString();
        switch (selection) {
            case "Bike":
                image.setImage(new Image("/images/bike.png"));
                break;
            case "Electric car":
                image.setImage(new Image("/images/Electric_car.png"));
                break;
            case "Motor bike":
                image.setImage(new Image("/images/moto.png"));
                break;
            case "Car":
                image.setImage(new Image("/images/car.png"));
                break;
            case "Hybrid car":
                image.setImage(new Image("/images/eco-car.png"));
                break;
            case "Scooter":
                image.setImage(new Image("/images/scooter.png"));
                break;

            default:
                break;
        }
    }

}
