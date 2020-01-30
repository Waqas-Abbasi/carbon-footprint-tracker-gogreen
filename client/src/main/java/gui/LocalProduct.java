
package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import org.json.JSONObject;


import java.net.URL;
import java.util.ResourceBundle;

import gui.ClientConnections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LocalProduct extends Base implements Initializable {
    @FXML
    JFXButton submit;
    @FXML
    JFXTextField localCountry;
    @FXML
    Label tips;
    @FXML
    JFXTextField productName;
    @FXML
    ImageView supermarket;
    @FXML
    ImageView fruit;
    @FXML
    ImageView sale;
    @FXML
    ImageView product;
    @FXML
    ImageView flag;


    /**
     * check the textfield is filled or not.
     *
     * @return true if every field  is filled.
     */
    @FXML
    public boolean checkField() {
        boolean result = false;

        if (!localCountry.getText().toString().isEmpty()
                && !productName.getText().isEmpty()) {
            result = true;
        } else {
            tips.setText("Please enter the country Name or Product name");
        }

        return result;
    }

    @FXML
    public void cleanTips() {
        tips.setText("");
    }

    /**
     * submit method to send the value to server.
     *
     * @param event event
     * @throws Exception exception
     */
    @FXML
    public void setSubmit(ActionEvent event) throws Exception {
        if (checkField()) {
            // TODO: 2019/3/29  send the value to the server.

            ClientConnections clientConnections = new ClientConnections();
            clientConnections.sendPost(
                    sendData(productName.getText(), localCountry.getText()), "/addproduct"
            );
            manager.loadStageWithChild(event, "/dashBoard.fxml", "/localProduct.fxml");
        }
    }

    /**
     * TODO: Add JavaDoc.
     */
    public String sendData(String product, String country) throws IllegalArgumentException {

        if (product == null || product.isEmpty() || country == null || country.isEmpty()) {
            throw new IllegalArgumentException();
        }

        JSONObject obj = new JSONObject();
        obj.put("productName", product);
        obj.put("country_name", country);
        JsonManager js = new JsonManager();

        return js.addDate(obj);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        supermarket.setImage(new Image("/images/supermarket.png"));
        sale.setImage(new Image("/images/sale.png"));
        fruit.setImage(new Image("/images/fruit.png"));
        flag.setImage(new Image("/images/flag.png"));
        product.setImage(new Image("/images/product.png"));
    }
}
//package gui;
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXTextField;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import org.json.JSONObject;
//
//public class LocalProduct extends Base implements Initializable {
//  @FXML
//  JFXButton submit;
//  @FXML
//  JFXTextField localCountry;
//  @FXML
//  Label tips;
//  @FXML
//  JFXTextField productName;
//  @FXML
//  ImageView supermarket;
//  @FXML
//  ImageView fruit;
//  @FXML
//  ImageView sale;
//  @FXML
//  ImageView product;
//  @FXML
//  ImageView flag;
//
//
//  /**
//   * check the textfield is filled or not.
//   *
//   * @return true if every field  is filled.
//   */
//  @FXML
//  public boolean checkField() {
//    boolean result = false;
//
//    if (!localCountry.getText().toString().isEmpty()
//      && !productName.getText().isEmpty()) {
//      result = true;
//    } else {
//      tips.setText("Please enter the country Name or Product name");
//    }
//
//    return result;
//  }
//
//  @FXML
//  public void cleanTips() {
//    tips.setText("");
//  }
//
//    /**
//     * submit method to send the value to server.
//     * @param event event
//     * @throws Exception exception
//     */
//    @FXML
//    public void setSubmit(ActionEvent event) throws Exception {
//        if (checkField()) {
//            // TODO: 2019/3/29  send the value to the server.
//
//      ClientConnections clientConnections = new ClientConnections();
//      clientConnections.sendPost(
//        sendData(productName.getText(), localCountry.getText()), "/addproduct"
//      );
//      refreshStage(event);
//    }
//  }
//
//  /**
//   * TODO: Add JavaDoc.
//   */
//  public String sendData(String product, String country) throws IllegalArgumentException {
//
//    if (product == null || product.isEmpty() || country == null || country.isEmpty()) {
//      throw new IllegalArgumentException();
//    }
//
//    JSONObject obj = new JSONObject();
//    obj.put("productName", product);
//    obj.put("country_name", country);
//    JsonManager js = new JsonManager();
//
//    return js.addDate(obj);
//  }
//
//  @Override
//  public void initialize(URL url, ResourceBundle resourceBundle) {
//    supermarket.setImage(new Image("/images/supermarket.png"));
//    sale.setImage(new Image("/images/sale.png"));
//    fruit.setImage(new Image("/images/fruit.png"));
//    flag.setImage(new Image("/images/flag.png"));
//    product.setImage(new Image("/images/product.png"));
//  }
//}
//>>>>>>> 37e4a29e1016e0993024cd2c2ecffc435ca16563
