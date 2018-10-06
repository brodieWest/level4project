package javafx.simulation;

import javafx.Controller;
import javafx.component.ComponentController;
import javafx.component.model.component.ComponentFactory;
import javafx.wire.WireController;
import model.Coordinates;
import javafx.wire.Wire;
import javafx.component.model.component.Component;
import javafx.fxml.FXML;
import javafx.main.MainController;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import model.Port;
import utils.Fxml;
import utils.FxmlLoaderUtils;

import java.util.HashMap;
import java.util.Map;

public class SimulationController implements Controller {
    @FXML
    AnchorPane simulationPane;


    private Map<String,ComponentController> componentControllers = new HashMap<>();

    private MainController mainController;

    private Map<String, WireController> wireControllers = new HashMap<>();

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public synchronized void clockTick() {
        // TODO
    }

    public synchronized void gateDelay() {
        for(ComponentController controller : componentControllers.values()) {
            Component component = controller.getComponentModel();
            component.processGateDelay();
            if(component.getStringIdentifier().equals("output")) {
                controller.switchOutputValue();
            }
        }

        for(WireController wireController : wireControllers.values()) {
            wireController.getWire().passSignal();
        }
    }

    public void clear() {
        componentControllers.clear();
        wireControllers.clear();
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

    public void addWire(String startComponentName, int startPortNo, String endComponentName, int endPortNo) {
        Fxml fxml = FxmlLoaderUtils.loadFxml("fxml/wire.fxml");

        WireController wireController = (WireController)fxml.getController();

        Parent wireNode = fxml.getNode();
        simulationPane.getChildren().add(wireNode);
        wireNode.toBack();

        Wire wire = new Wire();

        wireController.initialiseWire(wire);

        displayWire(wireController, startComponentName, startPortNo, endComponentName, endPortNo);

        wireControllers.put(wire.getUuid(), wireController);
    }

    private void displayWire(WireController wireController, String startComponentName, int startPortNo, String endComponentName, int endPortNo) {
        Component startComponent = componentControllers.get(startComponentName).getComponentModel();
        Port startPort = startComponent.getOutput(startPortNo);
        int startX = startComponent.getCoordinates().getX() + startPort.getOffset().getX();
        int startY = startComponent.getCoordinates().getY() + startPort.getOffset().getY();

        Component endComponent = componentControllers.get(endComponentName).getComponentModel();
        Port endPort = endComponent.getInput(endPortNo);
        int endX = endComponent.getCoordinates().getX() + endPort.getOffset().getX();
        int endY = endComponent.getCoordinates().getY() + endPort.getOffset().getY();

        wireController.displayWire(new Coordinates(startX, startY), new Coordinates(endX, endY), startPort, endPort);
    }

    public void removeWire() {
        // TODO
    }

    private void placeComponent(Parent componentNode, Coordinates coordinates) {
        simulationPane.getChildren().add(componentNode);
        AnchorPane.setTopAnchor(componentNode, coordinates.getY()*1.0);
        AnchorPane.setLeftAnchor(componentNode, coordinates.getX()*1.0);
    }
}
