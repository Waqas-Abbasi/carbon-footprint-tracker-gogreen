package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

public class PublicTransport extends Base implements Initializable {
  @FXML
  ComboBox<Object> mode;
  @FXML
  JFXTextField distance;
  @FXML
  JFXButton submit;
  @FXML
  Label modeLabel;
  @FXML
  Label distanceLabel;
  @FXML
  Label tips;

  private String[] modeBox = {"Bus", "Train", "Metro"};
  @FXML
  ImageView image;

  /**
   * this method is to check whether the text filed is empty or not and is integer
   * or not.
   *
   * @param event event
   * @return boolean value
   */

  @FXML
  private boolean checkTheTextFiled(ActionEvent event) {
    if (mode.getItems().toString().isEmpty() && distance.getText().isEmpty()) {
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
   * submit method to send the data to server.
   *
   * @param event event
   * @throws Exception exception
   */
  @FXML
  public void submitData(ActionEvent event) throws Exception {
    if (checkTheTextFiled(event)) {
      ClientConnections clientConnections = new ClientConnections();
      clientConnections.sendPost(sendData(distance.getText(), mode.getSelectionModel().getSelectedItem().toString()), "/addpublictransport");
      manager.loadStageWithChild(event, "/dashBoard.fxml", "/publicTransport.fxml");
    }
  }

  /**
   * this method is to set the text when the user types in the text field and choose.
   */

  @FXML
  public void setText() {
    String selection = mode.getSelectionModel().getSelectedItem().toString();
    modeLabel.setText(selection);
    switch (selection) {
      case "Bus":
        image.setImage(new Image("/images/bus.png"));
        break;
      case "Train":
        image.setImage(new Image("/images/train.png"));
        break;
      case "Metro":
        image.setImage(new Image("/images/metro.png"));
        break;
      default:
        break;
    }

  }

  /**
   * send data.
   *
   * @param distance distance
   * @param carType  carType
   * @return String data
   * @throws IllegalArgumentException IllegalArgumentException
   */
  private String sendData(String distance, String carType) throws IllegalArgumentException {

    if (distance == null || distance.isEmpty() || carType == null || carType.isEmpty()) {
      throw new IllegalArgumentException();
    }

    JSONObject obj = new JSONObject();
    obj.put("distance", distance);
    obj.put("carType", carType);

    JsonManager js = new JsonManager();

    return js.addDate(obj);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    mode.getItems().removeAll(mode.getItems());
    mode.getItems().addAll((Object[]) modeBox);
    mode.getSelectionModel().select(0);
    modeLabel.setText(mode.getSelectionModel().getSelectedItem().toString());
    image.setImage(new Image("/images/bus.png"));
  }

  public void setDistanceLabel() {
    distanceLabel.setText(distance.getText());
  }
}
