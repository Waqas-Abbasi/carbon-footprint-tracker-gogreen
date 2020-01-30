package gui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * main method.
 */
public class App extends Application {
  public static void main(String[] args) {
//    This configuration is useful for applications running on custom domains
//    Heroku's default domains have SSL enabled and we don't need this code
//    System.setProperty("javax.net.ssl.trustStore", "cert/dataflow.keystore");
//    System.setProperty("javax.net.ssl.trustStorePassword", "dataflow");
//    System.setProperty("javax.net.ssl.trustStoreType", "jks");
    launch(args);
  }


    /**
     * starts from login stage.
     * @param stage the stage from where to start
     * @throws Exception if stage is not accordingly
     */
    // i try to use fxml to build a pane which is the right part of the dashboard. and use
    //  root.getChildren().add(pane) to add it
    @Override
    public void start(Stage stage) throws Exception {
        LoginPage login = new LoginPage();
        Stage stageLogin = stage;
        login.start(stageLogin);
    }
}
