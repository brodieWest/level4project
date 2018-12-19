package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
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

    private double oldX;
    private double oldY;

    public ComponentController(SimulationController simulationController, Component componentModel) {
        this.componentModel = componentModel;
        this.simulationController = simulationController;

        loadFxml();

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

        //if (svgGroup.getLayoutX() + newTranslationX > simulationController.getScrollPane().getLayoutX()) {
            getComponent().setTranslateX(Math.round(newTranslationX/50.0)*50);
        //}
        //if(svgGroup.getLayoutY() + newTranslationY > simulationController.getScrollPane().getLayoutY()) {
            getComponent().setTranslateY(Math.round(newTranslationY/50.0)*50);
        //}
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
}
