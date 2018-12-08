package javafx.component.controllers;

import javafx.Controller;
import javafx.Descriptions;
import javafx.component.model.component.ReusableComponent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.component.model.component.Component;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.simulation.SimulationController;
import utils.fxml.FxmlLoaderUtils;

public class ComponentController implements Controller {

    Component componentModel;

    SimulationController simulationController;

    static String COMPONENT_PATH = "fxml/components/%s.fxml";

    @FXML
    Text text;

    @FXML
    private Parent component;

    @FXML
    Group svgGroup;

    public ComponentController(SimulationController simulationController, Component componentModel) {
        this.componentModel = componentModel;
        this.simulationController = simulationController;

        loadFxml();

    }

    public void loadFxml() {
        FxmlLoaderUtils.loadFxml(String.format(COMPONENT_PATH, componentModel.getStringIdentifier()), this);
    }


    public Component getComponentModel() {
        return componentModel;
    }

    public void processGateDelay() {
        componentModel.processGateDelay();
    }

    public void reset() {
        componentModel.reset();
    }

    public Parent getComponent() {
        return component;
    }

    public String getUuid() {
        return componentModel.getUuid();
    }

    public void displayText() {
        if (Descriptions.hasDescription(componentModel.getStringIdentifier())) {
            simulationController.displayText(Descriptions.getDescription(componentModel.getStringIdentifier()));
        }
    }
}
