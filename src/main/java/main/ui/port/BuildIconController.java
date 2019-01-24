package main.ui.port;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import main.fxml.FxmlLoaderUtils;
import main.model.Coordinates;
import main.model.PortIdentifier;
import main.model.PortType;
import main.model.WireIdentifier;
import main.ui.Controller;
import main.ui.component.controllers.ComponentController;
import main.ui.main.Mainfx;
import main.ui.simulation.MainSimulationController;
import main.ui.wire.WireController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static main.utils.MathsUtils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildIconController implements Controller {

    @FXML
    private Group buildIcon;

    private static String ICON_FXML_PATH = "fxml/buildIcon.fxml";

    private List<Coordinates> corners;

    private MainSimulationController simulationController;

    private ComponentController componentController;

    private PortController portController;

    private static int SCREEN_EDGE = 0;
    private static int SMALLEST_INCREMENT = 10;
    private static int WIRE_WIDTH = 5;
    private static int SMALLEST_POSITION = SCREEN_EDGE + (WIRE_WIDTH/2);

    private static Logger logger = LogManager.getLogger(BuildIconController.class);

    public BuildIconController(MainSimulationController simulationController,ComponentController componentController,PortController portController,List<Coordinates> corners) {
        this.corners = corners;
        this.simulationController = simulationController;
        this.componentController = componentController;
        this.portController = portController;
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(ICON_FXML_PATH), this);
    }

    public Group getBuildIcon() {
        return buildIcon;
    }

    @FXML
    private void startWire(MouseEvent mouseEvent) {

        Port port = portController.getPort();

        Parent background = simulationController.getBackground();
        Bounds boundsInScene = background.localToScene(background.getBoundsInLocal());

        double scaleX = simulationController.getScaleFactorX();
        double scaleY = simulationController.getScaleFactorY();

        if(simulationController.getWireBuilderStartPort() != null) {
            int x = (int) round((mouseEvent.getSceneX() - boundsInScene.getMinX())/scaleX, SMALLEST_INCREMENT);
            int y = (int) round((mouseEvent.getSceneY() - boundsInScene.getMinY())/scaleY,SMALLEST_INCREMENT);
            simulationController.newLine(new Coordinates(x, y));
            PortController startPort = simulationController.getWireBuilderStartPort();

            ArrayList<PortIdentifier> endPorts = new ArrayList<>();
            PortIdentifier startPortIdentifier;
            ComponentController startComponent;

            if (startPort.getPortType() == PortType.OUTPUT) {
                startComponent = startPort.getComponentController();
                startPortIdentifier = new PortIdentifier(startComponent.getUuid(), startPort.getPort().getPortNo());
                PortIdentifier endPortIdentifier = new PortIdentifier(componentController.getUuid(), port.getPortNo());
                endPortIdentifier.addCorners(simulationController.getWireBuilderCorners());
                endPorts.add(endPortIdentifier);
            } else {
                startComponent = componentController;
                ComponentController endComponent = startPort.getComponentController();
                startPortIdentifier = new PortIdentifier(componentController.getUuid(), port.getPortNo());
                PortIdentifier endPortIdentifier = new PortIdentifier(endComponent.getUuid(), startPort.getPort().getPortNo());
                Collections.reverse(simulationController.getWireBuilderCorners());
                endPortIdentifier.addCorners(simulationController.getWireBuilderCorners());
                endPorts.add(endPortIdentifier);
            }

            if (port.hasWire()) {
                List<Coordinates> newCorners = new ArrayList<>();
                newCorners.addAll(corners);
                newCorners.addAll(simulationController.getWireBuilderCorners());
                port.getWire().getWireController().addEndPort(new WireIdentifier(startPort.getPort(), newCorners));
            } else {
                if (!simulationController.addWire("wire" + Long.toHexString(Double.doubleToLongBits(Math.random())), startPortIdentifier, endPorts)) {
                    logger.error(String.format("failed to build wire between %s %d and %s %d", startComponent.getUuid(), startPort.getPort().getPortNo(), componentController.getUuid(), port.getPortNo()));
                }
            }
            endWireBuilder();
            return;
        }

        simulationController.showBuildIcons();

        simulationController.setWireBuilderStartPort(portController);

        simulationController.startWireBuilder(port.getEndPosition());

        Mainfx.getRoot().setOnMouseMoved(event -> {
            int x = (int)round((event.getSceneX()-boundsInScene.getMinX())/scaleX,SMALLEST_INCREMENT);
            int y = (int)round((event.getSceneY()-boundsInScene.getMinY())/scaleY,SMALLEST_INCREMENT);
            if(x < SMALLEST_POSITION || y < SMALLEST_POSITION) {
                endWireBuilder();
            }
            simulationController.displayLine(new Coordinates(x,y));
        });

        Mainfx.getRoot().setOnMouseClicked(event -> {
            int x = (int)round((event.getSceneX()-boundsInScene.getMinX())/scaleX,SMALLEST_INCREMENT);
            int y = (int)round((event.getSceneY()-boundsInScene.getMinY())/scaleY,SMALLEST_INCREMENT);
            simulationController.newLine(new Coordinates(x,y));
        });

        Mainfx.getRoot().setOnContextMenuRequested(event -> {
            endWireBuilder();
        });
    }

    private void endWireBuilder() {
        Mainfx.getRoot().setOnMouseMoved(event -> {});
        Mainfx.getRoot().setOnMouseClicked(event -> {});
        Mainfx.getRoot().setOnContextMenuRequested(event -> {});
        simulationController.setWireBuilderStartPort(null);
        simulationController.clearWireBuilder();
    }

    public List<Coordinates> getCorners() {
        return corners;
    }
}
