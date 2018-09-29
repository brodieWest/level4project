package javafx.main;

import fileIO.Load;
import javafx.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.simulation.SimulationController;
import javafx.simulation.model.Simulator;
import utils.Fxml;
import utils.FxmlLoaderUtils;

import java.io.IOException;


public class MainController implements Controller {

    private SimulationController simulationController;

    @FXML
    private VBox simulationVBox;

    @FXML
    private MenuItem load;

    @FXML
    protected void loadFile() {
        Load.loadFromFile("/FileFormatExample", simulationController);

    }

    public void initialize(){
        Fxml fxml = FxmlLoaderUtils.loadFxml("fxml/simulation.fxml");

        Parent simulationNode = fxml.getNode();

        this.simulationController = (SimulationController)fxml.getController();

        simulationController.setMainController(this);

        simulationVBox.getChildren().add(simulationNode);
    }
}
