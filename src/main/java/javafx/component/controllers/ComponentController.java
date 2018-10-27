package javafx.component.controllers;

import javafx.Controller;
import javafx.application.Platform;
import javafx.component.model.component.ComponentFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.component.model.component.Component;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.simulation.SimulationController;
import model.Coordinates;
import model.Logic;
import utils.Fxml;
import utils.FxmlLoaderUtils;

import java.io.IOException;

public class ComponentController implements Controller {

    Component componentModel;

    SimulationController simulationController;

    private static String COMPONENT_PATH = "fxml/components/%s.fxml";

    @FXML
    Text text;

    @FXML
    private Parent component;

    @FXML
    Group svgGroup;

    ComponentController(SimulationController simulationController, String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {
        Component componentModel = ComponentFactory.getComponent(type, coordinates, uuid, noInputs,noOutputs);
        this.componentModel = componentModel;
        this.simulationController = simulationController;

        FxmlLoaderUtils.loadFxml(String.format(COMPONENT_PATH, componentModel.getStringIdentifier()), this);

    }

    public Component getComponentModel() {
        return componentModel;
    }

    public void deleteImageAsExample() {
        //Shape shape = (Shape)svgGroup.getChildren().get(0);
        //shape.setFill(Paint.valueOf("yellow"));
    }

    public void processGateDelay() {
        componentModel.processGateDelay();
    }

    public void reset() {
        componentModel.reset();
    }

    public void addInput() {
        componentModel.addNewInput();
    }

    public void addOutput() {
        componentModel.addNewOutput();
    }

    public Parent getComponent() {
        return component;
    }

    public String getUuid() {
        return componentModel.getUuid();
    }
}
