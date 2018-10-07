package javafx.simulation;

import javafx.Controller;
import javafx.component.ComponentController;
import javafx.component.model.component.ComponentFactory;
import javafx.component.model.component.Dff;
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
    AnchorPane simulationPane;

    private static String COMPONENT_PATH = "fxml/components/%s.fxml";
    private static String WIRE_PATH = "fxml/wire.fxml";
    private static String OUTPUT = "output";


    private Map<String,ComponentController> componentControllers = new HashMap<>();

    private MainController mainController;

    private Map<String, WireController> wireControllers = new HashMap<>();

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void clockTick() {
        // TODO improve efficiency here
        for(ComponentController controller : componentControllers.values()) {
            Component component = controller.getComponentModel();
            if(component.getStringIdentifier().equals("dff")) {
                Dff dff = (Dff) component;
                dff.processClockTick();
            }
        }
        resetSimulation();
        wireDelay();
    }

    public void gateDelay() {
        for(ComponentController controller : componentControllers.values()) {
            Component component = controller.getComponentModel();
            component.processGateDelay();
        }

        wireDelay();

    }

    public void wireDelay() {
        for(WireController wireController : wireControllers.values()) {
            wireController.getWire().passSignal();
            wireController.showSignal();
        }

        //TODO improve efficiency here
        for(ComponentController controller : componentControllers.values()) {
            Component component = controller.getComponentModel();
            if(component.getStringIdentifier().equals(OUTPUT)) {
                controller.showOutputValue();
            }
        }
    }

    public void resetSimulation() {
        for(ComponentController controller : componentControllers.values()) {
            controller.getComponentModel().reset();
        }
    }

    public void clear() {
        // not working because uuids don't reset
        componentControllers.clear();
        wireControllers.clear();
        simulationPane.getChildren().clear();
    }

    public void addComponent(String type, Coordinates coordinates) {

        Fxml fxml = FxmlLoaderUtils.loadFxml( String.format(COMPONENT_PATH, type));
        ComponentController componentController = (ComponentController)fxml.getController();

        Component componentModel = ComponentFactory.getComponent(type, coordinates);

        componentController.initialiseComponent(componentModel, this);

        // TODO: should check that uuid does not already exist
        componentControllers.put(componentModel.getUuid(), componentController);

        placeComponent(fxml.getNode(), coordinates);

    }

    public void removeComponent() {
        // TODO
    }

    public void addWire(String startComponentName, int startPortNo, String endComponentName, int endPortNo) {
        Fxml fxml = FxmlLoaderUtils.loadFxml(WIRE_PATH);

        WireController wireController = (WireController)fxml.getController();

        Parent wireNode = fxml.getNode();
        simulationPane.getChildren().add(wireNode);
        wireNode.toBack();

        Wire wire = new Wire();

        Component startComponent = componentControllers.get(startComponentName).getComponentModel();
        Component endComponent = componentControllers.get(endComponentName).getComponentModel();

        wireController.initialiseWire(wire, startComponent, startPortNo, endComponent, endPortNo);

        wireControllers.put(wire.getUuid(), wireController);
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
