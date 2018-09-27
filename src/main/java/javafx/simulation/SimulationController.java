package javafx.simulation;

import javafx.Controller;
import javafx.component.ComponentController;
import javafx.component.model.ComponentFactory;
import javafx.component.model.Coordinates;
import javafx.component.Wire.Wire;
import javafx.component.model.component.Component;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.main.MainController;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.simulation.model.Simulator;
import utils.Fxml;
import utils.FxmlLoaderUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimulationController implements Controller {
    @FXML
    AnchorPane simulationPane;


    private Map<String,ComponentController> componentControllers = new HashMap<>();

    private MainController mainController;

    private Map<String, Wire> wires = new HashMap<>();

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public synchronized void clockTick() {
        // TODO
    }

    public synchronized void gateDelay() {
        // TODO
    }

    public void addComponent(String type, Coordinates coordinates) {

        Fxml fxml = FxmlLoaderUtils.loadFxml( "fxml/components/" + type + ".fxml");
        placeComponent(fxml.getNode(), coordinates);
        ComponentController componentController = (ComponentController)fxml.getController();

        Component componentModel = ComponentFactory.getComponent(type, coordinates);

        componentController.initialiseComponent(componentModel);

        // TODO: should check that uuid does not already exist
        componentControllers.put(componentModel.getUuid(), componentController);

    }

    public void removeComponent() {
        // TODO
    }

    public void addWire() {
        Wire wire = new Wire();
        wires.put(wire.getUuid(), wire);
    }

    public void removeWire() {
        // TODO
    }

    private void placeComponent(Parent componentNode, Coordinates coordinates) {
        simulationPane.getChildren().add(componentNode);
        AnchorPane.setTopAnchor(componentNode, coordinates.getX()*100.0);
        AnchorPane.setLeftAnchor(componentNode, coordinates.getY()*100.0);
    }
}
