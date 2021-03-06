package main.ui.port;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import main.fxml.FxmlLoaderUtils;
import main.model.Coordinates;
import main.model.PortIdentifier;
import main.model.PortParameters;
import main.model.PortType;
import main.ui.Controller;
import main.ui.component.controllers.ComponentController;
import main.ui.component.model.component.Component;
import main.ui.main.Mainfx;
import main.ui.simulation.MainSimulationController;
import main.ui.simulation.SimulationController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class PortController implements Controller {

    @FXML
    private Group group;

    @FXML
    private Line line;

    @FXML
    private Line outerPath;

    @FXML
    private Line innerPath;

    private BuildIconController buildIconController;

    private Port port;

    private ComponentController componentController;

    private String PORT_FXML_PATH = "fxml/port.fxml";

    private static String LOGIC_UNDEFINED_COLOUR = "lightgrey";

    private static String TRANSPARENT = "transparent";

    private static Logger logger = LogManager.getLogger(PortController.class);

    PortController(Port port) {
        this.port = port;
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(PORT_FXML_PATH), this);
    }

    public void setComponentController(ComponentController componentController) {
        this.componentController = componentController;
        if(componentController.getSimulationController() instanceof MainSimulationController) {
            buildIconController = new BuildIconController((MainSimulationController) componentController.getSimulationController(), componentController, this, new ArrayList<>());
        }
    }

    void hidePort() {
        group.getChildren().clear();
    }

    public Line getLine() {
        return line;
    }

    public Group getGroup() {
        return group;
    }





    public void displayPort() {
        if(port.getSize() >1) {
            displayWordPort();
        } else {
            displayBitPort();
        }
        // should be inproved to not use instanceof
        if(componentController.getSimulationController() instanceof MainSimulationController) {
            Group buildIcon = buildIconController.getBuildIcon();
            if (!group.getChildren().contains(buildIcon)) {
                group.getChildren().add(buildIcon);
            }
            buildIcon.setLayoutX(port.getEndCoordinates().getX() - 5);
            buildIcon.setLayoutY(port.getEndCoordinates().getY() - 5);
        }
        group.toBack();
    }

    private void displayBitPort() {
        group.getChildren().remove(outerPath);
        group.getChildren().remove(innerPath);
        if(!group.getChildren().contains(line)) {
            group.getChildren().add(line);
        }
        line.setStartX(port.getOffset().getX());
        line.setStartY(port.getOffset().getY());
        line.setEndX(port.getEndCoordinates().getX());
        line.setEndY(port.getEndCoordinates().getY());
        line.setStroke(Paint.valueOf(LOGIC_UNDEFINED_COLOUR));
        line.toBack();
    }

    private void displayWordPort() {
        group.getChildren().remove(line);
        if(!group.getChildren().contains(outerPath)) {
            group.getChildren().add(outerPath);
        }
        if(!group.getChildren().contains(innerPath)) {
            group.getChildren().add(innerPath);
        }
        innerPath.setStartX(port.getOffset().getX());
        innerPath.setStartY(port.getOffset().getY());
        innerPath.setEndX(port.getEndCoordinates().getX());
        innerPath.setEndY(port.getEndCoordinates().getY());
        innerPath.toBack();
        outerPath.setStartX(port.getOffset().getX());
        outerPath.setStartY(port.getOffset().getY());
        outerPath.setEndX(port.getEndCoordinates().getX());
        outerPath.setEndY(port.getEndCoordinates().getY());
        outerPath.toBack();
    }

    public PortType getPortType() {
        return port.getPortType();
    }

    public ComponentController getComponentController() {
        return componentController;
    }

    public Port getPort() {
        return port;
    }

    public void reset() {
        displayPort();
    }
}
