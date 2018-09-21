package javafx.simulation;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class SimulationController {
    @FXML
    Node simulationGridPane;

    @FXML
    Button button;

    @FXML
    protected void buttonPress() {
        if(button.getText().equals("loading...")) {
            button.setText("I worked");
        }    else {
            button.setText("loading...");
        }
    }
}
