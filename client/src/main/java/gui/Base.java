package gui;

import javafx.event.ActionEvent;


/**
 * abstract class.
 */
public abstract class Base {

    SceneManager manager = new SceneManager();
    /**
     *  this method to help refresh the page after press submit button.
     * @param event event
     * @throws Exception Exception
     */

    public void refreshStage(ActionEvent event) throws Exception {
        manager.loadStage(event, "/dashBoard.fxml");
    }
}
