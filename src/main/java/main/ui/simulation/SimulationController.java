package main.ui.simulation;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.transform.Scale;
import main.ui.Controller;
import main.ui.component.OutputControllerInterface;
import main.ui.component.Synchronous;
import main.ui.component.WordComponent;
import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.ComponentControllerFactory;
import main.ui.component.controllers.ReusableComponentController;
import main.ui.component.model.component.ComponentParameters;
import main.ui.main.Mainfx;
import main.ui.wire.WireController;
import main.ui.wire.WordWireController;
import main.model.Coordinates;
import main.ui.component.model.component.Component;
import main.ui.port.Port;
import main.model.PortIdentifier;
import main.model.WireIdentifier;
import main.fxml.FxmlLoaderUtils;

import java.util.ArrayList;
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
    private Map<String,OutputControllerInterface> outputControllers = new HashMap<>();
    private Map<String,Synchronous> synchronousControllers = new HashMap<>();
    private Map<String, WordComponent> wordComponents = new HashMap<>();
    private Map<String, ReusableComponentController> reusableControllers = new HashMap<>();

    private Scale scale = new Scale();

    private Map<String, WireController> wireControllers = new HashMap<>();

    private static String SIMULATION_FXML_PATH = "fxml/simulation.fxml";
    private static String BACKGROUND_FXML_PATH = "fxml/background.fxml";
    private static String BACKGROUND_LINE_COLOUR = "lightgray";

    private static int SCREEN_SIZE = 10000;
    private static int BACKGROUND_BOX_SIZE = 100;

    public SimulationController() {
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(SIMULATION_FXML_PATH), this);


        simulationPane.getChildren().add(backGround);

        scale.setPivotX(0);
        scale.setPivotY(0);

        simulationPane.getTransforms().add(scale);
    }

    private Group buildBackground() {

        Group background = (Group)FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(BACKGROUND_FXML_PATH)).getNode();

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
        for(Synchronous synchronous : synchronousControllers.values()) {
            synchronous.processClockTick();
        }
        resetSimulation();
        wireDelay();
    }

    public void addSynchronous(Synchronous synchronous) {
        synchronousControllers.put(synchronous.getUuid(),synchronous);
    }


    public void addReusableController(ReusableComponentController reusableController) {
        reusableControllers.put(reusableController.getUuid(),reusableController);
    }

    public void gateDelay() {
        for(ComponentController controller : componentControllers.values()) {
            controller.processGateDelay();
        }

        wireDelay();



    }

    public void wireDelay() {
        for(int i=0;i<wordComponents.size()+1;i++) {
            for (WireController wireController : wireControllers.values()) {
                wireController.passSignal();
                wireController.showSignal();
            }

            for (WordComponent wordComponent : wordComponents.values()) {
                wordComponent.wireDelay();
            }

            for (OutputControllerInterface outputController : outputControllers.values()) {
                outputController.showOutputValue();
            }

            for (ReusableComponentController reusableController : reusableControllers.values()) {
                reusableController.wireDelay();
            }
        }

    }

    public void addOutput(OutputControllerInterface outputController) {
        outputControllers.put(outputController.getUuid(),outputController);
    }

    public void addWordComponent(WordComponent wordComponent) {
        wordComponents.put(wordComponent.getUuid(),wordComponent);
    }

    public void resetSimulation() {
        for(ComponentController controller : componentControllers.values()) {
            controller.reset();
        }

    }

    public void clear() {
        componentControllers.clear();
        wireControllers.clear();
        simulationPane.getChildren().clear();
        simulationPane.getChildren().add(backGround);
        backGround.toBack();
        resetSimulation();

    }

    public boolean addComponent(ComponentParameters componentParameters) {

        ComponentController componentController = ComponentControllerFactory.getComponentController(this, componentParameters);
        if(componentController == null) return false;

        componentControllers.put(componentController.getUuid(), componentController);

        placeComponent(componentController.getComponent(), componentParameters.getCoordinates());
        return true;

    }

    public void removeComponent(ComponentController componentController) {
        componentControllers.remove(componentController.getUuid());

        simulationPane.getChildren().remove(componentController.getComponent());
    }

    public boolean addWire(String uuid, PortIdentifier startPortIdentifier, ArrayList<PortIdentifier> endPortIdentifiers) {
        String startComponentName = startPortIdentifier.getComponent();
        int startPortNo = startPortIdentifier.getPortNo();


        if(!componentControllers.containsKey(startComponentName)) return false;

        Component startComponent = componentControllers.get(startComponentName).getComponentModel();

        ArrayList<WireIdentifier> outputPorts = new ArrayList<>();

        for(PortIdentifier outputPort : endPortIdentifiers) {
            String endComponentName = outputPort.getComponent();
            int endPortNo = outputPort.getPortNo();

            if(!componentControllers.containsKey(endComponentName)) return false;
            Component endComponent = componentControllers.get(endComponentName).getComponentModel();
            if(endComponent.getInputSize() -1 < endPortNo) return false;
            outputPorts.add(new WireIdentifier(endComponent.getInput(endPortNo),outputPort.getCorners()));
        }



        if(startComponent.getOutputSize() -1 < startPortNo) return false;

        Port input = startComponent.getOutput(startPortNo);
        WireController wireController;
        if(input.getWord().size()>1) {
            wireController = new WordWireController(uuid,input,outputPorts);
        } else {
            wireController = new WireController(uuid, input, outputPorts);
        }

        wireControllers.put(wireController.getUuid(), wireController);

        displayWire(wireController.getGroup());

        return true;
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

    ComponentController getComponentController(String uuid) {
        return componentControllers.getOrDefault(uuid, null);
    }

    WireController getWireController(String uuid) {
        return wireControllers.get(uuid);
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
            action.consume();
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

    public void displayText(Parent parent) {
    }

    public Map<String,Integer> getPortLocations() {
        Map<String, Integer> portLocations = new HashMap<>();

        for(ComponentController componentController : componentControllers.values()) {
            portLocations.putAll(componentController.getComponentModel().getPortLocations());
        }

        return portLocations;
    }

    public Group getBackground() {
        return backGround;
    }
}
