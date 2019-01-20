package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
import main.ui.wire.Wire;

import java.util.List;

public class ComponentController implements Controller {

    Component componentModel;

    SimulationController simulationController;

    private List<PortController> portControllers;

    static String COMPONENT_PATH = "fxml/components/%s.fxml";

    @FXML
    Text text;

    @FXML
    AnchorPane component;

    @FXML
    Group svgGroup;

    @FXML
    private Group rotatable;

    private double oldX;
    private double oldY;

    public ComponentController(SimulationController simulationController, Component componentModel) {
        this.componentModel = componentModel;
        this.simulationController = simulationController;
        componentModel.setComponentController(this);

        loadFxml();

        displayPorts();

        Line line = new Line(0,0,componentModel.getWIDTH(),componentModel.getHEIGHT());
        line.setStroke(Paint.valueOf("transparent"));

        svgGroup.getChildren().add(line);

        rotatable.setRotate(componentModel.getInitialRotate());
    }

    void displayPorts() {
        portControllers = componentModel.getPortControllers();

        for(PortController portController : portControllers) {
            if(!svgGroup.getChildren().contains(portController.getGroup())) {
                svgGroup.getChildren().add(portController.getGroup());
            }
            if(portController.getComponentController() == null) {
                portController.setComponentController(this);
            }
            portController.displayPort();
        }
    }

    public void loadFxml() {
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(String.format(COMPONENT_PATH, componentModel.getStringIdentifier())), this);
    }

    public Coordinates getCoordinates() {
        return componentModel.getCoordinates();
    }

    private boolean hasWires = false;

    @FXML
    void findLocation(MouseEvent mouseEvent) {
        double scaleX = simulationController.getScaleFactorX();
        double scaleY = simulationController.getScaleFactorY();

        oldX = getComponent().getTranslateX() - mouseEvent.getSceneX()/scaleX;
        oldY = getComponent().getTranslateY() - mouseEvent.getSceneY()/scaleY;
        //simulationController.setPannable(false);
        //component.toFront();
        hasWires = componentModel.hasWires();

        Mainfx.getRoot().setOnKeyPressed(event -> rotate(event));
    }

    @FXML
    void moveComponent(MouseEvent mouseEvent) {
        if(hasWires) return;

        double scaleX = simulationController.getScaleFactorX();
        double scaleY = simulationController.getScaleFactorY();

        double newTranslationX = (mouseEvent.getSceneX()/scaleX) + oldX;
        double newTranslationY = mouseEvent.getSceneY()/scaleY + oldY;

        if (getComponent().getLayoutX() + newTranslationX > 0) {
            getComponent().setTranslateX(Math.round(newTranslationX/50.0)*50);
        }
        if(getComponent().getLayoutY() + newTranslationY > 0) {
            getComponent().setTranslateY(Math.round(newTranslationY/50.0)*50);
        }

        double newX = Math.round(((getComponent().getLayoutX() + Math.round(newTranslationX/50.0)*50)/50.0))*50;
        double newY = Math.round(((getComponent().getLayoutY() + Math.round(newTranslationY/50.0)*50)/50.0))*50;

        if(newX < 0) newX = 0;
        if(newY < 0) newY = 0;

        componentModel.setCoordinates(new Coordinates((int)newX, (int)newY));
    }

    public void stopMoving() {
        Mainfx.getRoot().setOnKeyPressed(event -> {});
    }

    private void rotate(KeyEvent event) {
        rotatable.setRotate(rotatable.getRotate()+90);
        componentModel.rotatePorts();


        displayPorts();
    }

    public int getRotation() {
        return (int)rotatable.getRotate();
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

    public SimulationController getSimulationController() {
        return simulationController;
    }

    public boolean isConnected() {
        return componentModel.isConnected();
    }

    public void setDeletable(boolean deletable) {
        if(deletable) {
            getComponent().setOnMouseClicked(event -> {
                simulationController.removeComponent(this);
                componentModel.deleteWires();
            });
        } else {
            getComponent().setOnMouseClicked(event -> {});
        }
    }
}
