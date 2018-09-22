package javafx.simulation;

import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import simulator.Simulator;

public class SimulationController implements Controller {
    @FXML
    GridPane simulationGridPane;

    public void initialize(){
        Simulator.setSimulationController(this);
    }

    public void placeComponent(Parent componentNode, int xCoord, int yCoord) {
        GridPane.setConstraints(componentNode, yCoord, xCoord);
        simulationGridPane.getChildren().add(componentNode);
    }
}
