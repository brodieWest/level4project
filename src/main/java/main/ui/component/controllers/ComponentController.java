package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import main.model.ComponentParametersModel;
import main.model.Coordinates;
import main.model.Direction;
import main.ui.Controller;
import main.ui.Descriptions;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.main.Mainfx;
import main.ui.port.PortController;
import main.ui.simulation.SimulationController;
import main.fxml.FxmlLoaderUtils;

import java.util.List;

public class ComponentController implements Controller {

    Component componentModel;

    SimulationController simulationController;

    private List<PortController> portControllers;

    static String COMPONENT_PATH = "fxml/components/%s.fxml";

    @FXML
    Text text;

    @FXML
    Parent component;

    @FXML
    Group svgGroup;

    private double oldX;
    private double oldY;

    public ComponentController(SimulationController simulationController, Component componentModel) {
        this.componentModel = componentModel;
        this.simulationController = simulationController;

        loadFxml();

        portControllers = componentModel.getPortControllers();

        for(PortController portController : portControllers) {
            svgGroup.getChildren().add(portController.getLine());
            portController.displayPort();
        }

        Line line = new Line(0,0,componentModel.getWIDTH(),componentModel.getHEIGHT());
        line.setStroke(Paint.valueOf("transparent"));

        svgGroup.getChildren().add(line);
    }

    public void loadFxml() {
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(String.format(COMPONENT_PATH, componentModel.getStringIdentifier())), this);
    }


    @FXML
    private void findLocation(MouseEvent mouseEvent) {
        oldX = getComponent().getTranslateX() - mouseEvent.getSceneX();
        oldY = getComponent().getTranslateY() - mouseEvent.getSceneY();
        //simulationController.setPannable(false);
        //component.toFront();
    }

    @FXML
    private void moveComponent(MouseEvent mouseEvent) {

        double newTranslationX = mouseEvent.getSceneX() + oldX;
        double newTranslationY = mouseEvent.getSceneY() + oldY;

        double simulationX = simulationController.getBackground().getLayoutX();
        double simulationY = simulationController.getBackground().getLayoutY();

        if (getComponent().getLayoutX() + newTranslationX > simulationX) {
            getComponent().setTranslateX(Math.round(newTranslationX/50.0)*50);
        }
        if(getComponent().getLayoutY() + newTranslationY > simulationY) {
            getComponent().setTranslateY(Math.round(newTranslationY/50.0)*50);
        }

        double newX = Math.round(((getComponent().getLayoutX() + Math.round(newTranslationX/50.0)*50 - simulationX)/50.0))*50;
        double newY = Math.round(((getComponent().getLayoutY() + Math.round(newTranslationY/50.0)*50 - simulationY)/50.0))*50;

        if(newX < 0) newX = 0;
        if(newY < 0) newY = 0;

        componentModel.setCoordinates(new Coordinates((int)newX, (int)newY));
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
        return svgGroup;
    }

    public String getUuid() {
        return componentModel.getUuid();
    }

    public void displayText() {
        if (Descriptions.hasDescription(componentModel.getStringIdentifier())) {
            simulationController.displayText(Descriptions.getDescription(componentModel.getStringIdentifier()));
        }
    }

    public ComponentParametersModel getComponentParameters() {
        return componentModel.getComponentParameters();
    }
}
