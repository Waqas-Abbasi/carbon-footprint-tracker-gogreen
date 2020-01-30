package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyBadges extends Base implements Initializable {
    @FXML
    ImageView imageBeginner;
    @FXML
    ImageView imageIntermediate;
    @FXML
    ImageView imageLeader;
    @FXML
    CheckBox checkBeginner;
    @FXML
    CheckBox checkIntermediate;
    @FXML
    CheckBox checkLeader;
    @FXML
    Label labelBeginner;
    @FXML
    Label labelIntermediate;
    @FXML
    Label labelLeader;



    /**
     * check the score the user get and show which badges the user get.
      * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageBeginner.setImage(new Image("/images/beginner.png"));
        imageIntermediate.setImage(new Image("/images/intermediate.png"));
        imageLeader.setImage(new Image("/images/leader.png"));
        ClientConnections cc = new ClientConnections();
        String footprint = cc.sendGet("getscore").getBody();
        Double doublenumber = Double.parseDouble(footprint);
        if (doublenumber >= 100) {
            checkBeginner.setSelected(true);
            checkBeginner.setVisible(true);
            labelBeginner.setVisible(true);
            imageBeginner.setVisible(true);
            if (doublenumber >= 2000) {
                labelIntermediate.setVisible(true);
                checkIntermediate.setSelected(true);
                checkIntermediate.setVisible(true);
                imageIntermediate.setVisible(true);
            }
            if (doublenumber >= 5000) {
                checkLeader.setSelected(true);
                checkLeader.setVisible(true);
                labelLeader.setVisible(true);
                imageLeader.setVisible(true);
            }
        }
    }
}
