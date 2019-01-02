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
import main.ui.Controller;
import main.ui.component.controllers.ComponentController;
import main.ui.component.model.component.Component;
import main.ui.main.Mainfx;
import main.ui.simulation.SimulationController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.UUID;

public class PortController implements Controller {

    @FXML
    private Group group;

    @FXML
    private Line line;

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
        line.setStroke(Paint.valueOf(TRANSPARENT));
    }

    public Line getLine() {
        return line;
    }

    public Group getGroup() {
        return group;
    }

    @FXML
    private void startWire(MouseEvent mouseEvent) {

        if(componentController.getSimulationController().getWireBuilderStartPort() != null) {
            PortController startPort = componentController.getSimulationController().getWireBuilderStartPort();
            ComponentController startComponent = startPort.componentController;
            PortIdentifier startPortIdentifier = new PortIdentifier(startComponent.getUuid(),startPort.port.getPortNo());
            PortIdentifier endPortIdentifier = new PortIdentifier(componentController.getUuid(),port.getPortNo());
            ArrayList<PortIdentifier> endPorts = new ArrayList<>();
            endPorts.add(endPortIdentifier);
            if(!componentController.getSimulationController().addWire(UUID.randomUUID().toString(),startPortIdentifier,endPorts)) {
                logger.error(String.format("failed to build wire between %s %d and %s %d",startComponent.getUuid(),startPort.port.getPortNo(),componentController.getUuid(),port.getPortNo()) );
            }
            Mainfx.getRoot().setOnMouseMoved(event -> {});
            componentController.getSimulationController().setWireBuilderStartPort(null);
            return;
        }

        componentController.getSimulationController().setWireBuilderStartPort(this);

        Group group = new Group();

        Line newLine = new Line();

        group.getChildren().add(newLine);

        componentController.getSimulationController().addWireBuilder(group);

        Parent background = componentController.getSimulationController().getBackground();
        Bounds boundsInScene = background.localToScene(background.getBoundsInLocal());

        newLine.setStartX(port.getEndPosition().getX());
        newLine.setStartY(port.getEndPosition().getY());

        newLine.setStroke(Paint.valueOf("lightgray"));
        newLine.setStrokeWidth(3);

        Mainfx.getRoot().setOnMouseMoved(event -> {
            //Coordinates componentCoordinates = componentController.getCoordinates();
            //double simulationX = componentController.getSimulationController().getBackground().getLayoutX();
            //double simulationY = componentController.getSimulationController().getBackground().getLayoutY();
            //Parent background = componentController.getSimulationController().getBackground();
            //Bounds boundsInScene = background.localToScene(background.getBoundsInLocal());
            newLine.setEndX(event.getSceneX()-boundsInScene.getMinX());
            newLine.setEndY(event.getSceneY()-boundsInScene.getMinY());
        });
    }

    public void displayPort() {
        line.setStartX(port.getOffset().getX());
        line.setStartY(port.getOffset().getY());
        line.setEndX(port.getEndCoordinates().getX());
        line.setEndY(port.getEndCoordinates().getY());
        line.setStroke(Paint.valueOf(LOGIC_UNDEFINED_COLOUR));
        line.toBack();
    }
}
