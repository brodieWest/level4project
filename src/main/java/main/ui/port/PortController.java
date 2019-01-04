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
    private Group buildIcon;

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

    @FXML
    private void startWire(MouseEvent mouseEvent) {

        MainSimulationController simulationController = (MainSimulationController)componentController.getSimulationController();

        Parent background = simulationController.getBackground();
        Bounds boundsInScene = background.localToScene(background.getBoundsInLocal());

        if(simulationController.getWireBuilderStartPort() != null) {
            int x = (int)Math.round((mouseEvent.getSceneX()-boundsInScene.getMinX())/10)*10;
            int y = (int)Math.round((mouseEvent.getSceneY()-boundsInScene.getMinY())/10)*10;
            simulationController.newLine(new Coordinates(x,y));
            PortController startPort = simulationController.getWireBuilderStartPort();

            ArrayList<PortIdentifier> endPorts = new ArrayList<>();
            PortIdentifier startPortIdentifier;
            ComponentController startComponent;

            if(startPort.getPortType() == PortType.OUTPUT) {
                startComponent = startPort.componentController;
                startPortIdentifier = new PortIdentifier(startComponent.getUuid(), startPort.port.getPortNo());
                PortIdentifier endPortIdentifier = new PortIdentifier(componentController.getUuid(), port.getPortNo());
                endPortIdentifier.addCorners(simulationController.getWireBuilderCorners());
                endPorts.add(endPortIdentifier);
            } else {
                startComponent = componentController;
                ComponentController endComponent = startPort.componentController;
                startPortIdentifier = new PortIdentifier(componentController.getUuid(), port.getPortNo());
                PortIdentifier endPortIdentifier =  new PortIdentifier(endComponent.getUuid(), startPort.port.getPortNo());
                Collections.reverse(simulationController.getWireBuilderCorners());
                endPortIdentifier.addCorners(simulationController.getWireBuilderCorners());
                endPorts.add(endPortIdentifier);
            }

            if(!simulationController.addWire("wire" + Long.toHexString(Double.doubleToLongBits(Math.random())),startPortIdentifier,endPorts)) {
                logger.error(String.format("failed to build wire between %s %d and %s %d",startComponent.getUuid(),startPort.port.getPortNo(),componentController.getUuid(),port.getPortNo()) );
            }
            endWireBuilder();
            return;
        }

        simulationController.setWireBuilderStartPort(this);

        simulationController.startWireBuilder(port.getEndPosition());

        Mainfx.getRoot().setOnMouseMoved(event -> {
            int x = (int)Math.round((event.getSceneX()-boundsInScene.getMinX())/10)*10;
            int y = (int)Math.round((event.getSceneY()-boundsInScene.getMinY())/10)*10;
            simulationController.displayLine(new Coordinates(x,y));
        });

        Mainfx.getRoot().setOnMouseClicked(event -> {
            int x = (int)Math.round((event.getSceneX()-boundsInScene.getMinX())/10)*10;
            int y = (int)Math.round((event.getSceneY()-boundsInScene.getMinY())/10)*10;
            simulationController.newLine(new Coordinates(x,y));
        });

        Mainfx.getRoot().setOnContextMenuRequested(event -> {
            endWireBuilder();
        });
    }

    private void endWireBuilder() {
        MainSimulationController simulationController = (MainSimulationController)componentController.getSimulationController();
        Mainfx.getRoot().setOnMouseMoved(event -> {});
        Mainfx.getRoot().setOnMouseClicked(event -> {});
        Mainfx.getRoot().setOnContextMenuRequested(event -> {});
        simulationController.setWireBuilderStartPort(null);
        simulationController.clearWireBuilder();
    }

    public void displayPort() {
        line.setStartX(port.getOffset().getX());
        line.setStartY(port.getOffset().getY());
        line.setEndX(port.getEndCoordinates().getX());
        line.setEndY(port.getEndCoordinates().getY());
        line.setStroke(Paint.valueOf(LOGIC_UNDEFINED_COLOUR));
        line.toBack();
        buildIcon.setLayoutX(port.getEndCoordinates().getX()-5);
        buildIcon.setLayoutY(port.getEndCoordinates().getY()-5);
        group.toBack();
    }

    public PortType getPortType() {
        return port.getPortType();
    }
}
