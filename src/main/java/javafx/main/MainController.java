package javafx.main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class MainController extends BorderPane {
    @FXML
    private MenuItem load;

    @FXML
    private VBox simulationVBox;

    @FXML
    protected void loadFile() {
        Label label = new Label();
        label.setText("I'm an AND gate");
        simulationVBox.getChildren().add(label);
    }
}
