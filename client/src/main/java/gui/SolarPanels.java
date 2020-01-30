package gui;

import com.jfoenix.controls.JFXSlider;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.json.JSONObject;

public class SolarPanels extends Base implements Initializable {

  @FXML
  JFXSlider electricityUsage;

  @FXML
  JFXSlider percentageSaved;

  /**
   * Retrieves the solar panel data and sends it to the database.
   *
   * @param event the event
   */
  @FXML
  public void sendSolarPanelData(ActionEvent event) throws Exception {
    double usage = electricityUsage.getValue();
    double saved = percentageSaved.getValue();

    ClientConnections clientConnections = new ClientConnections();
    clientConnections.sendPost(sendData(usage + "", saved + ""), "/addsolarpanels");
    clientConnections.sendPost("", "/myachievements_solar_panel");
    manager.loadStageWithChild(event, "/dashBoard.fxml", "/solarPanels.fxml");
  }

  public String sendData(String usage, String saved) throws IllegalArgumentException {

    if (usage == null || usage.isEmpty() || saved == null || saved.isEmpty()) {
      throw new IllegalArgumentException();
    }

    JSONObject obj = new JSONObject();
    obj.put("electricityUsage", usage);
    obj.put("percentageSaved", saved);
    JsonManager js = new JsonManager();

    return js.addDate(obj);

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
