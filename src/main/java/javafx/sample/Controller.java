package javafx.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;


public class Controller {
    @FXML
    MenuItem load;

    @FXML
    VBox simulationVBox;

    @FXML
    protected void loadFile(ActionEvent event) {
        Label label = new Label();
        label.setText("I'm an AND gate");
        simulationVBox.getChildren().add(label);
    }
}
