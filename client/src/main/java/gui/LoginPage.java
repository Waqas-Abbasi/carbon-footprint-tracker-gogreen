package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;

public class LoginPage extends Application implements Initializable {

  @FXML
  Button login;
  @FXML
  JFXTextField email;
  @FXML
  JFXPasswordField password;
  @FXML
  Label statusLogin;
  @FXML
  JFXButton register;
  @FXML
  JFXTextField name;
  @FXML
  JFXTextField emailRegister;
  @FXML
  JFXTextField country;
  @FXML
  JFXPasswordField passwordRegister;
  @FXML
  JFXPasswordField passwordRegisterRepeat;
  @FXML
  Label statusRegister;
  private double offsetX = 0;
  private double offsetY = 0;


    SceneManager manager = new SceneManager();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/loginPage.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);

    root.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        offsetX = event.getSceneX();
        offsetY = event.getSceneY();
      }
    });
    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        stage.setX(event.getScreenX() - offsetX);
        stage.setY(event.getScreenY() - offsetY);
      }
    });

    stage.setScene(scene);
    stage.show();
    stage.setOnCloseRequest(t -> {
      Platform.exit();
      ClientConnections cc = new ClientConnections();
      cc.sendGet("destroy");
      System.exit(0);
    });
  }

  /*
   * Login Button Action Method
   */

  /**
   * TODO: Add JavaDoc.
   */
  @FXML

    public void onLoginButtonClicked(ActionEvent event) throws Exception {
        if (!email.getText().isEmpty() && !password.getText().isEmpty()) {
            ClientConnections cc = new ClientConnections();
            Boolean success = cc.sendGetAuth(email.getText(), password.getText());
            if (success) {
                manager.loadStageWithChild(event, "/dashBoard.fxml", "/myActivities.fxml");

//                Parent newRoot = FXMLLoader.load(getClass().getResource("/dashBoard.fxml"));
//                Scene newScene = new Scene(newRoot);
//                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                window.setScene(newScene);
//                window.show();
            } else {
                statusLogin.setText("Bad credentials");
            }
        } else {
            statusLogin.setText("Supply both username and password!");
        }

  }

    /**
     * TODO: Add JavaDoc.
     */
    @FXML
    public void onRegisterButtonClicked(ActionEvent event) throws IOException {
        if (!emailRegister.getText().isEmpty() && !passwordRegister.getText().isEmpty()
                && !country.getText().isEmpty()   && !passwordRegisterRepeat.getText().isEmpty()
                && passwordRegister.getText().equals(passwordRegisterRepeat.getText())) {

      statusRegister.setText("Registration successful!");

            JSONObject obj = new JSONObject();
            obj.put("name", name.getText());
            obj.put("username", emailRegister.getText());
            obj.put("password", passwordRegister.getText());
            obj.put("country", country.getText());
            ClientConnections cc = new ClientConnections();
            String registerState = cc.sendPost(obj.toString(), "register").getBody();
            if (registerState.equals("welcome")) {
                statusRegister.setText("Registration successful!");
            } else {
                statusRegister.setText("Registration unsuccessful!");
            }
            name.clear();
            emailRegister.clear();
            passwordRegister.clear();
            passwordRegisterRepeat.clear();
            country.clear();
        } else if (!passwordRegister.getText().equals(passwordRegisterRepeat.getText())) {
            statusRegister.setText("Passwords do not match");
        } else {
            statusRegister.setText("Missing information");
        }
    }


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }
}
