package javafx.simulation;

import javafx.Controller;
import javafx.component.Synchronous;
import javafx.component.controllers.ComponentController;
import javafx.component.controllers.ComponentControllerFactory;
import javafx.component.controllers.OutputController;
import javafx.component.controllers.DffController;
import javafx.component.model.component.ComponentFactory;
import javafx.component.model.component.Dff;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.wire.WireController;
import model.Coordinates;
import javafx.wire.Wire;
import javafx.component.model.component.Component;
import javafx.fxml.FXML;
import javafx.main.MainController;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import utils.Fxml;
import utils.FxmlLoaderUtils;

import java.util.HashMap;
import java.util.Map;

public class SimulationController implements Controller {
    @FXML
    private AnchorPane simulationPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Group group;


    Map<String,ComponentController> componentControllers = new HashMap<>();

    private MainController mainController;

    private Map<String, WireController> wireControllers = new HashMap<>();

    private static String SIMULATION_FXML_PATH = "fxml/simulation.fxml";

    public SimulationController(MainController mainController) {
        FxmlLoaderUtils.loadFxml(SIMULATION_FXML_PATH, this);
        this.mainController = mainController;
    }

    public void clockTick() {
        // TODO improve efficiency here
        for(ComponentController controller : componentControllers.values()) {
            if(controller instanceof Synchronous) {
                Synchronous synchronousController = (Synchronous)controller;
                synchronousController.processClockTick();
            }
        }
        resetSimulation();
        wireDelay();
    }

    public void gateDelay() {
        for(ComponentController controller : componentControllers.values()) {
            controller.processGateDelay();
        }

        wireDelay();

    }

    public void wireDelay() {
        for(WireController wireController : wireControllers.values()) {
            wireController.passSignal();
            wireController.showSignal();
        }

        //TODO improve efficiency here
        for(ComponentController controller : componentControllers.values()) {
            if(controller instanceof OutputController) {
                OutputController outputController = (OutputController) controller;
                outputController.showOutputValue();
            }
        }
    }

    public void resetSimulation() {
        for(ComponentController controller : componentControllers.values()) {
            controller.reset();
        }
    }

    public void clear() {
        Wire.reset();
        componentControllers.clear();
        wireControllers.clear();
        simulationPane.getChildren().clear();
    }

    public void addComponent(String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {

        ComponentController componentController = ComponentControllerFactory.getComponentController(this, type, coordinates, uuid, noInputs,noOutputs);

        componentControllers.put(componentController.getUuid(), componentController);

        placeComponent(componentController.getComponent(), coordinates);

    }

    public void removeComponent() {
        // TODO
    }

    public void addWire(String startComponentName, int startPortNo, String endComponentName, int endPortNo) {

        Component startComponent = componentControllers.get(startComponentName).getComponentModel();
        Component endComponent = componentControllers.get(endComponentName).getComponentModel();

        WireController wireController = new WireController(startComponent, startPortNo, endComponent, endPortNo);

        wireControllers.put(wireController.getUuid(), wireController);

        displayWire(wireController.getGroup());

    }


    public void removeWire() {
        // TODO
    }

    private void placeComponent(Parent componentNode, Coordinates coordinates) {
        simulationPane.getChildren().add(componentNode);
        AnchorPane.setTopAnchor(componentNode, coordinates.getY()*1.0);
        AnchorPane.setLeftAnchor(componentNode, coordinates.getX()*1.0);
    }

    private void displayWire(Parent wireNode) {
        simulationPane.getChildren().add(wireNode);
        wireNode.toBack();
    }

    public ComponentController getComponentController(String uuid) {
        return componentControllers.get(uuid);
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void zoomIn() {
        group.setScaleX(group.getScaleX() * 1.5);
        group.setScaleY(group.getScaleY() * 1.5);
    }

    public void zoomOut() {
        group.setScaleX(group.getScaleX() / 1.5);
        group.setScaleY(group.getScaleY() / 1.5);
    }
}
