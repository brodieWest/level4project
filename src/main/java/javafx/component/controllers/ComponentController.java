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

    private static String COMPONENT_PATH = "fxml/components/%s.fxml";
    private static String REUSABLE = "reusable";

    @FXML
    Text text;

    @FXML
    private Parent component;

    @FXML
    Group svgGroup;

    ComponentController(SimulationController simulationController, Component componentModel) {
        this.componentModel = componentModel;
        this.simulationController = simulationController;

        if(componentModel instanceof ReusableComponent) {
            FxmlLoaderUtils.loadFxml(String.format(COMPONENT_PATH,REUSABLE), this);
        } else {
            FxmlLoaderUtils.loadFxml(String.format(COMPONENT_PATH, componentModel.getStringIdentifier()), this);
        }

    }

    public Component getComponentModel() {
        return componentModel;
    }

    public void deleteImageAsExample() {
        Shape shape = (Shape)svgGroup.getChildren().get(0);
        shape.setFill(Paint.valueOf("yellow"));
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

    public void displayText() {
        if (Descriptions.hasDescription(componentModel.getStringIdentifier())) {
            simulationController.displayText(Descriptions.getDescription(componentModel.getStringIdentifier()));
        }
    }
}
