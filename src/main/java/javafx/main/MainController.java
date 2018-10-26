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



    @FXML
    private VBox simulationVBox;

    @FXML
    private MenuItem load;

    @FXML
    protected void loadFile() {
        simulationController.clear();
        Load.loadWithFileChooser(simulationController);
        simulationController.resetSimulation();
        simulationController.wireDelay();
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

        this.simulationController = new SimulationController(this);

        simulationVBox.getChildren().add(simulationController.getSimulationPane());
    }
}
