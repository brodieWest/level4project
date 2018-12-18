package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import main.ui.Controller;
import main.ui.Descriptions;
import main.ui.component.model.component.Component;
import main.ui.main.Mainfx;
import main.ui.simulation.SimulationController;
import main.fxml.FxmlLoaderUtils;

public class ComponentController implements Controller {

    Component componentModel;

    SimulationController simulationController;

    static String COMPONENT_PATH = "fxml/components/%s.fxml";

    @FXML
    Text text;

    @FXML
    Parent component;

    @FXML
    Group svgGroup;

    public ComponentController(SimulationController simulationController, Component componentModel) {
        this.componentModel = componentModel;
        this.simulationController = simulationController;

        loadFxml();

    }

    public void loadFxml() {
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(String.format(COMPONENT_PATH, componentModel.getStringIdentifier())), this);
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
