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
import main.ui.simulation.MainSimulationController;
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

        MainSimulationController simulationController = (MainSimulationController)componentController.getSimulationController();

        if(simulationController.getWireBuilderStartPort() != null) {
            PortController startPort = simulationController.getWireBuilderStartPort();
            ComponentController startComponent = startPort.componentController;
            PortIdentifier startPortIdentifier = new PortIdentifier(startComponent.getUuid(),startPort.port.getPortNo());
            PortIdentifier endPortIdentifier = new PortIdentifier(componentController.getUuid(),port.getPortNo());
            //System.out.println(simulationController.getWireBuilderCorners().size());
            endPortIdentifier.addCorners(simulationController.getWireBuilderCorners());
            ArrayList<PortIdentifier> endPorts = new ArrayList<>();
            endPorts.add(endPortIdentifier);
            if(!simulationController.addWire("wire" + Long.toHexString(Double.doubleToLongBits(Math.random())),startPortIdentifier,endPorts)) {
                logger.error(String.format("failed to build wire between %s %d and %s %d",startComponent.getUuid(),startPort.port.getPortNo(),componentController.getUuid(),port.getPortNo()) );
            }
            Mainfx.getRoot().setOnMouseMoved(event -> {});
            Mainfx.getRoot().setOnMouseClicked(event -> {});
            simulationController.setWireBuilderStartPort(null);
            simulationController.clearWireBuilder();
            return;
        }

        simulationController.setWireBuilderStartPort(this);

        Parent background = simulationController.getBackground();
        Bounds boundsInScene = background.localToScene(background.getBoundsInLocal());

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
