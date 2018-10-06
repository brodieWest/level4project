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
        Load.loadFromFile(simulationController);

    }

    @FXML
    private void gateDelay() {
        simulationController.gateDelay();
    }

    public void initialize(){
        Fxml fxml = FxmlLoaderUtils.loadFxml("fxml/simulation.fxml");

        Parent simulationNode = fxml.getNode();

        this.simulationController = (SimulationController)fxml.getController();

        simulationController.setMainController(this);

        simulationVBox.getChildren().add(simulationNode);
    }
}
