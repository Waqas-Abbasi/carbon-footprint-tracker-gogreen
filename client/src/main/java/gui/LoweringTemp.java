package gui;

import com.jfoenix.controls.JFXSlider;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.json.JSONObject;

public class LoweringTemp extends Base implements Initializable {

  @FXML
  JFXSlider outsideTemp;

  @FXML
  JFXSlider insideTemp;

    /**Retrieves the temperature data and sends it to the database.
     * @param event the event
     */
    @FXML
    public void sendTemperatureData(ActionEvent event) throws Exception {
        double outside = outsideTemp.getValue();
        double inside = insideTemp.getValue();
        //TODO: Call method for communication with the database

    ClientConnections clientConnections = new ClientConnections();
    clientConnections.sendPost(sendData(outside + "", inside + ""), "/loweringtemp");
    clientConnections.sendPost("", "/myachievements_10_activities");

    refreshStage(event);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  /**
   * TODO: Add JavaDoc.
   */
  public String sendData(String outside, String inside) throws IllegalArgumentException {
    if (outside == null || outside.isEmpty() || inside == null || inside.isEmpty()) {
      throw new IllegalArgumentException();
    }
    JSONObject obj = new JSONObject();
    obj.put("tempInside", inside);
    obj.put("tempOutside", outside);

    JsonManager js = new JsonManager();
    return js.addDate(obj);
  }
}
