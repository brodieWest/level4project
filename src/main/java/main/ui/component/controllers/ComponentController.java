package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import main.model.ComponentParametersModel;
import main.model.Coordinates;
import main.ui.Controller;
import main.ui.descriptions.Descriptions;
import main.ui.component.model.component.Component;
import main.ui.main.Mainfx;
import main.ui.port.PortController;
import main.ui.simulation.MainSimulationController;
import main.ui.simulation.SimulationController;
import main.fxml.FxmlLoaderUtils;

import static main.utils.MathsUtils.*;

import java.util.List;

public class ComponentController implements Controller {

    Component componentModel;

    SimulationController simulationController;

    private List<PortController> portControllers;

    static String COMPONENT_PATH = "fxml/components/%s.fxml";
    private static int BUILD_ICON_RADIUS = 5;
    public static int HALF_COMPONENT_HEIGHT = 50;
    private static int SCREEN_EDGE = 0;
    private static int ROTATION_FACTOR = 90;

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

    private Group cross = new Group();

    private boolean deletable = false;

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

        buildCross();
    }

    private void buildCross() {
        int lineOffset = componentModel.getDefaultPortOffset()/2;
        Line line1 = new Line(lineOffset,lineOffset,componentModel.getWIDTH()-lineOffset,componentModel.getHEIGHT()-lineOffset);
        Line line2 = new Line(componentModel.getWIDTH()-lineOffset,lineOffset,lineOffset,componentModel.getHEIGHT()-lineOffset);
        line1.setStroke(Paint.valueOf("red"));
        line2.setStroke(Paint.valueOf("red"));
        line1.setStrokeWidth(5);
        line2.setStrokeWidth(5);

        cross.getChildren().add(line1);
        cross.getChildren().add(line2);
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
        hasWires = componentModel.hasWires();

        ((MainSimulationController)simulationController).getRoot().setOnKeyPressed(event -> rotate(event));
    }

    @FXML
    void moveComponent(MouseEvent mouseEvent) {
        if(hasWires) return;
        if(deletable) return;

        double scaleX = simulationController.getScaleFactorX();
        double scaleY = simulationController.getScaleFactorY();

        double newTranslationX = round(mouseEvent.getSceneX()/scaleX + oldX, HALF_COMPONENT_HEIGHT);
        double newTranslationY = round(mouseEvent.getSceneY()/scaleY + oldY, HALF_COMPONENT_HEIGHT);

        double newComponentX = getComponent().getLayoutX() + newTranslationX;
        double newComponentY = getComponent().getLayoutY() + newTranslationY;

        if (newComponentX + BUILD_ICON_RADIUS > SCREEN_EDGE) {
            getComponent().setTranslateX(newTranslationX);
        }

        if(newComponentY + BUILD_ICON_RADIUS > SCREEN_EDGE) {
            getComponent().setTranslateY(newTranslationY);
        }

        double newX = round(getComponent().getLayoutX() + newTranslationX,HALF_COMPONENT_HEIGHT);
        double newY = round(getComponent().getLayoutY() + newTranslationY,HALF_COMPONENT_HEIGHT);

        if(newX < SCREEN_EDGE) newX = SCREEN_EDGE;
        if(newY < SCREEN_EDGE) newY = SCREEN_EDGE;

        componentModel.setCoordinates(new Coordinates((int)newX, (int)newY));
    }

    public void stopMoving() {
        ((MainSimulationController)simulationController).getRoot().setOnKeyPressed(event -> {});
    }

    private void rotate(KeyEvent event) {
        rotatable.setRotate(rotatable.getRotate()+ROTATION_FACTOR);
        componentModel.rotatePorts();


        displayPorts();
    }

    public int getRotation() {
        return (int)rotatable.getRotate();
    }

    public Component getComponentModel() {
        return componentModel;
    }

    public boolean processGateDelay() {
        return componentModel.gateDelay();
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
        this.deletable = deletable;
        if(deletable) {
            getComponent().setOnMouseClicked(event -> {
                simulationController.removeComponent(this);
                componentModel.deleteWires();
            });
            svgGroup.getChildren().add(cross);
        } else {
            getComponent().setOnMouseClicked(event -> {});
            svgGroup.getChildren().remove(cross);
        }
    }

    public boolean isDeletable() {
        return deletable;
    }

    public String getType() {
        return componentModel.getType();
    }
}
