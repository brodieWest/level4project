package javafx.simulation;

import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class SimulationController implements Controller {
    @FXML
    Node simulationGridPane;

    @FXML
    private Button button;

    @FXML
    protected void buttonPress() {
        if(button.getText().equals("loading...")) {
            button.setText("I worked");
        }    else {
            button.setText("loading...");
        }
    }
}
