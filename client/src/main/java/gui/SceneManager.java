package gui;

import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneManager {
    private double xoffset = 0;
    private double yoffset = 0;

    /**
     * TODO: Add JavaDoc.
     */
    public SceneManager() {
    }

    /**
     * TODO: Add JavaDoc.
     */
    public void setRadioButtonsControlVisibility(Parent container, boolean state) {
        container.setVisible(state);
    }

    /**
     * helper method to load stages.
     * it gets the root from an fxml file and sets a new scene with the appropriate root
     *
     * @param event    event which is fired
     * @param filename name of the fxml file to load
     * @throws Exception TODO: This
     */
    public void loadStage(ActionEvent event, String filename) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(filename));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        moveWindow(root, window);
    }

    /**
     * TODO: Add JavaDoc.
     */
    public void loadStage(Stage window, String filename) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(filename));
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();
        moveWindow(root, window);
    }

    /**
     * TODO: Add JavaDoc.
     */
    public void loadStageWithChild(ActionEvent event,
                                   String parentFileName, String childFileName) throws Exception {


        Pane root = FXMLLoader.load(getClass().getResource(parentFileName));
        Pane child = FXMLLoader.load(getClass().getResource(childFileName));

        Pane target = (Pane) root.getChildren().stream()
                .filter(childElement -> !(childElement instanceof VBox))
                .collect(Collectors.toList())
                .get(0);


        target.getChildren().add(child);

        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        moveWindow(root, window);

    }

    /**
     * helper method to move the window.
     *
     * @param root   root
     * @param window window
     */
    private void moveWindow(Parent root, Stage window) {
        root.setOnMousePressed(e -> {
            xoffset = e.getSceneX();
            yoffset = e.getSceneY();

        });
        root.setOnMouseDragged(e -> {
            window.setX(e.getScreenX() - xoffset);
            window.setY(e.getScreenY() - yoffset);
        });
    }
}
