package javafx.simulation;

import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import simulator.Simulator;

public class SimulationController implements Controller {
    @FXML
    Node simulationGridPane;

    public void initialize(){
        Simulator.setSimulationController(this);
    }

    public void placeComponent(Parent componentFxml, int xCoord, int yCoord) {
        // TODO
    }
}
