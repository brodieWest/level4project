package javafx.main;

import fileIO.Load;
import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.simulation.SimulationController;
import utils.Fxml;
import utils.FxmlLoaderUtils;


public class MainController implements Controller {

    private SimulationController simulationController;

    private static String SIMULATION_FXML_PATH = "fxml/simulation.fxml";

    @FXML
    private VBox simulationVBox;

    @FXML
    private MenuItem load;

    @FXML
    protected void loadFile() {
        simulationController.clear();
        Load.loadFromFile(simulationController);
        simulationController.resetSimulation();
        simulationController.gateDelay();
    }

    @FXML
    private void clear() {
        simulationController.clear();
    }

    @FXML
    private void gateDelay() {
        simulationController.gateDelay();
    }

    @FXML
    private void clockTick() {
        simulationController.clockTick();
    }

    public void initialize(){
        Fxml fxml = FxmlLoaderUtils.loadFxml(SIMULATION_FXML_PATH);

        Parent simulationNode = fxml.getNode();

        this.simulationController = (SimulationController)fxml.getController();

        simulationController.setMainController(this);

        simulationVBox.getChildren().add(simulationNode);
    }
}
