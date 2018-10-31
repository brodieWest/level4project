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
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.transform.Scale;
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

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class SimulationController implements Controller {
    @FXML
    private AnchorPane simulationPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private StackPane stackPane;

    private Group backGround = buildBackground();

    Map<String,ComponentController> componentControllers = new HashMap<>();

    private MainController mainController;

    private Scale scale = new Scale();

    private Map<String, WireController> wireControllers = new HashMap<>();

    private static String SIMULATION_FXML_PATH = "fxml/simulation.fxml";
    private static String BACKGROUND_FXML_PATH = "fxml/background.fxml";
    private static String BACKGROUND_LINE_COLOUR = "lightgray";

    private static int SCREEN_SIZE = 10000;
    private static int BACKGROUND_BOX_SIZE = 100;

    public SimulationController(MainController mainController) {
        FxmlLoaderUtils.loadFxml(SIMULATION_FXML_PATH, this);
        this.mainController = mainController;

        simulationPane.getChildren().add(backGround);

        scale.setPivotX(0);
        scale.setPivotY(0);

        simulationPane.getTransforms().add(scale);
    }

    private Group buildBackground() {

        Group background = (Group)FxmlLoaderUtils.loadFxml(BACKGROUND_FXML_PATH).getNode();

        for(int i=0;i<=SCREEN_SIZE;i+=BACKGROUND_BOX_SIZE) {
            Line line1 = new Line(0,i,SCREEN_SIZE,i);
            line1.setStroke(Paint.valueOf(BACKGROUND_LINE_COLOUR));

            Line line2 = new Line(i,0,i,SCREEN_SIZE);
            line2.setStroke(Paint.valueOf(BACKGROUND_LINE_COLOUR));
            background.getChildren().addAll(line1,line2);
        }
        return background;
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
        simulationPane.getChildren().add(backGround);
        backGround.toBack();
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
        backGround.toBack();
    }

    public ComponentController getComponentController(String uuid) {
        return componentControllers.get(uuid);
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void zoomIn() {
        scale.setX(scale.getX() * 1.5);
        scale.setY(scale.getY() * 1.5);
    }

    public void zoomOut() {
        scale.setX(scale.getX() / 1.5);
        scale.setY(scale.getY() / 1.5);
    }

    public void scrollEvent(ScrollEvent action) {
        if(action.isControlDown()) {
            double zoomFactor = action.getDeltaY();
            if(zoomFactor<1) {
                scale.setX(scale.getX() / 1.1);
                scale.setY(scale.getY() / 1.1);
            } else {
                scale.setX(scale.getX() * 1.1);
                scale.setY(scale.getY() * 1.1);
            }
        }
    }

}
