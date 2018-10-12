package javafx.component.controllers;

import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.component.model.component.Component;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.simulation.SimulationController;

public class ComponentController implements Controller {

    Component componentModel;

    SimulationController simulationController;

    @FXML
    Text text;

    @FXML
    private Parent component;

    @FXML
    Group svgGroup;

    public void initialiseComponent(Component componentModel, SimulationController simulationController) {
        this.componentModel = componentModel;
        this.simulationController = simulationController;
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

}
