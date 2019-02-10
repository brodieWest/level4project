package main.ui.simulation;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import main.ui.Controller;
import main.ui.component.InputControllerInterface;
import main.ui.component.OutputControllerInterface;
import main.ui.component.Synchronous;
import main.ui.component.WordComponent;
import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.ComponentControllerFactory;
import main.ui.component.controllers.DffController;
import main.ui.component.controllers.ReusableComponentController;
import main.ui.component.model.component.ComponentParameters;
import main.ui.component.model.component.Dff;
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
import java.util.List;
import java.util.Map;

public class SimulationController implements Controller {
    @FXML
    AnchorPane simulationPane;

    @FXML
    ScrollPane scrollPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private Group backgroundGroup;

    @FXML
    Group wireGroup;

    @FXML
    private Group componentGroup;

    BackgroundController backgroundController = new BackgroundController();

    Map<String, ComponentController> componentControllers = new HashMap<>();
    Map<String, OutputControllerInterface> outputControllers = new HashMap<>();
    Map<String, DffController> dffControllers = new HashMap<>();
    private Map<String, WordComponent> wordComponents = new HashMap<>();
    Map<String, ReusableComponentController> reusableControllers = new HashMap<>();
    Map<String, InputControllerInterface> inputControllers = new HashMap<>();

    private Scale scale = new Scale();

    Map<String, WireController> wireControllers = new HashMap<>();

    private List<Group> buildIcons = new ArrayList<>();

    private static String SIMULATION_FXML_PATH = "fxml/simulation.fxml";

    private static double ZOOM_FACTOR = 1.1;

    public SimulationController() {
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(SIMULATION_FXML_PATH), this);


        backgroundGroup.getChildren().add(backgroundController.getvBox());

        scale.setPivotX(0);
        scale.setPivotY(0);

        simulationPane.getTransforms().add(scale);
    }



    public void clockTick() {
        for (DffController synchronous : dffControllers.values()) {
            synchronous.processClockTick();
        }
        for (Synchronous synchronous : reusableControllers.values()) {
            synchronous.processClockTick();
        }
        resetSimulation();
        wireDelay();
    }

    public void addSynchronous(DffController synchronous) {
        dffControllers.put(synchronous.getUuid(), synchronous);
    }


    public void addReusableController(ReusableComponentController reusableController) {
        reusableControllers.put(reusableController.getUuid(), reusableController);
    }

    public void gateDelay() {
        for (ComponentController controller : componentControllers.values()) {
            controller.processGateDelay();
        }

        wireDelay();


    }

    public void wireDelay() {
        for (int i = 0; i < wordComponents.size() + 1; i++) {
            for (WireController wireController : wireControllers.values()) {
                wireController.passSignal();
                wireController.showSignal();
            }

            for (WordComponent wordComponent : wordComponents.values()) {
                wordComponent.wireDelay();
            }

            for (Synchronous synchronous : dffControllers.values()) {
                synchronous.wireDelay();
            }

            for (Synchronous synchronous : reusableControllers.values()) {
                synchronous.wireDelay();
            }

            for (WireController wireController : wireControllers.values()) {
                wireController.passSignal();
                wireController.showSignal();
            }

            for (OutputControllerInterface outputController : outputControllers.values()) {
                outputController.showOutputValue();
            }

            for (WireController wireController : wireControllers.values()) {
                wireController.passSignal();
                wireController.showSignal();
            }
        }

    }

    public ComponentController getComponent(String uuid) {
        return componentControllers.get(uuid);
    }

    public void addOutput(OutputControllerInterface outputController) {
        outputControllers.put(outputController.getUuid(), outputController);
    }

    public void addWordComponent(WordComponent wordComponent) {
        wordComponents.put(wordComponent.getUuid(), wordComponent);
    }

    public void addInput(InputControllerInterface componentController) {
        inputControllers.put(componentController.getUuid(), componentController);
    }

    public void resetSimulation() {
        for (ComponentController controller : componentControllers.values()) {
            controller.reset();
        }

    }

    public void clear() {
        componentControllers.clear();
        outputControllers.clear();
        inputControllers.clear();
        dffControllers.clear();
        wordComponents.clear();
        reusableControllers.clear();
        wireControllers.clear();
        wireGroup.getChildren().clear();
        componentGroup.getChildren().clear();
        //backGround.toBack();
        resetSimulation();

    }

    public boolean addComponent(ComponentParameters componentParameters) {

        ComponentController componentController = ComponentControllerFactory.getComponentController(this, componentParameters);
        if (componentController == null) return false;

        componentControllers.put(componentController.getUuid(), componentController);

        placeComponent(componentController.getComponent(), componentParameters.getCoordinates());
        return true;

    }

    public void removeComponent(ComponentController componentController) {
        componentControllers.remove(componentController.getUuid());
        outputControllers.remove(componentController.getUuid());
        dffControllers.remove(componentController.getUuid());
        wordComponents.remove(componentController.getUuid());
        reusableControllers.remove(componentController.getUuid());
        inputControllers.remove(componentController.getUuid());

        hideComponent(componentController);
    }

    public void hideComponent(ComponentController componentController) {
        componentGroup.getChildren().remove(componentController.getComponent());
    }

    public boolean addWire(String uuid, PortIdentifier startPortIdentifier, ArrayList<PortIdentifier> endPortIdentifiers) {
        String startComponentName = startPortIdentifier.getComponent();
        int startPortNo = startPortIdentifier.getPort();


        if (!componentControllers.containsKey(startComponentName)) return false;

        Component startComponent = componentControllers.get(startComponentName).getComponentModel();

        ArrayList<WireIdentifier> outputPorts = new ArrayList<>();

        for (PortIdentifier outputPort : endPortIdentifiers) {
            String endComponentName = outputPort.getComponent();
            int endPortNo = outputPort.getPort();

            if (!componentControllers.containsKey(endComponentName)) return false;
            Component endComponent = componentControllers.get(endComponentName).getComponentModel();
            if (endComponent.getInputSize() - 1 < endPortNo) return false;
            outputPorts.add(new WireIdentifier(endComponent.getInput(endPortNo), outputPort.getCorner()));
        }


        if (startComponent.getOutputSize() - 1 < startPortNo) return false;

        Port input = startComponent.getOutput(startPortNo);
        WireController wireController;
        if (input.getSize() > 1) {
            wireController = new WordWireController(this, uuid, input, outputPorts);
        } else {
            wireController = new WireController(this, uuid, input, outputPorts);
        }

        wireControllers.put(wireController.getUuid(), wireController);

        displayWire(wireController.getGroup());

        return true;
    }


    public void removeWire(WireController wireController) {
        wireControllers.remove(wireController.getUuid());
        wireGroup.getChildren().remove(wireController.getGroup());
    }

    private void placeComponent(Parent componentNode, Coordinates coordinates) {
        componentGroup.getChildren().add(componentNode);
        componentNode.setLayoutX(coordinates.getX());
        componentNode.setLayoutY(coordinates.getY());
    }

    private void displayWire(Parent wireNode) {
        wireGroup.getChildren().add(wireNode);
    }

    public void addBuildIcon(Group node) {
        componentGroup.getChildren().add(node);
        buildIcons.add(node);
    }

    void removeBuildIcons() {
        for (Group buildIcon : buildIcons) {
            componentGroup.getChildren().remove(buildIcon);
        }
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
        scale.setX(scale.getX() * ZOOM_FACTOR);
        scale.setY(scale.getY() * ZOOM_FACTOR);
    }

    public void zoomOut() {
        scale.setX(scale.getX() / ZOOM_FACTOR);
        scale.setY(scale.getY() / ZOOM_FACTOR);
    }

    public void scrollEvent(ScrollEvent action) {
        if (action.isControlDown()) {
            action.consume();
            double zoomFactor = action.getDeltaY();
            if(zoomFactor == 0) return;
            if (zoomFactor < 0) {
                zoomOut();
            } else {
                zoomIn();
            }
        }
    }

    public void displayText(Parent parent) {
    }

    public Map<String, Integer> getPortLocations() {
        Map<String, Integer> portLocations = new HashMap<>();

        for (ComponentController componentController : componentControllers.values()) {
            portLocations.putAll(componentController.getComponentModel().getPortLocations());
        }

        return portLocations;
    }

    public AnchorPane getSimulationPane() {
        return simulationPane;
    }

    public double getScaleFactorX() {
        return scale.getX();
    }

    public double getScaleFactorY() {
        return scale.getY();
    }

    public List<Component> getOutputs() {
        List<Component> outputs = new ArrayList<>();

        for(OutputControllerInterface outputController : outputControllers.values()) {
            outputs.add(((ComponentController)outputController).getComponentModel());
        }

        for(ReusableComponentController reusable : reusableControllers.values()) {
            outputs.addAll(reusable.getOutputs());
        }

        for(DffController dffController : dffControllers.values()) {
            outputs.add(dffController.getComponentModel());
        }

        return outputs;
    }

}
