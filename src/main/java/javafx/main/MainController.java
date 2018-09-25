package javafx.main;

import fileIO.Load;
import javafx.Controller;
import javafx.component.ComponentController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.simulation.SimulationController;
import javafx.simulation.model.Simulator;

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
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getClassLoader().getResource("fxml/simulationGridPane.fxml"));

        Parent simulationNode;

        try {
            simulationNode = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        this.simulationController = fxmlLoader.getController();

        simulationController.setMainController(this);

        simulationVBox.getChildren().add(simulationNode);
    }
}
