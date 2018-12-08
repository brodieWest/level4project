package javafx.wire;

import javafx.Controller;
import javafx.component.WordComponent;
import javafx.component.model.component.Component;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.simulation.SimulationController;
import model.*;
import javafx.fxml.FXML;
import javafx.scene.shape.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.fxml.FxmlLoaderUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class WireController implements Controller {
    @FXML
    private Path path;

    @FXML
    private Group group;

    private Wire wire;

    private static String LOGIC_0_COLOUR = "0x7293cb";
    private static String LOGIC_1_COLOUR = "0xd35e60";
    private static String LOGIC_UNDEFINED_COLOUR = "grey";

    private static String WIRE_PATH = "fxml/wire.fxml";
    private int SIZE = 100;

    private static Logger logger = LogManager.getLogger(WireController.class);

    public WireController(String uuid, Port startPort, ArrayList<WireIdentifier> endPorts) {
        FxmlLoaderUtils.loadFxml(WIRE_PATH, this);

        Wire wire = new Wire(uuid);
        this.wire = wire;

        wire.setInput(startPort);
        startPort.setWire(wire);

        for(WireIdentifier identifier : endPorts) {
            Port endPort = identifier.getPort();
            wire.addOutput(endPort);
            endPort.setWire(wire);
        }

        displayWire(startPort, endPorts);
    }

    private void displayWire(Port startPort, ArrayList<WireIdentifier> endPorts) {
        Coordinates startCoordinates = startPort.getPosition();

        if(startPort.getDirection() == Direction.EAST) {
            endPorts = addCorners(startCoordinates, endPorts);
        }

        List<Coordinates> oldCorners = null;

        boolean breakFound = true;

        for(WireIdentifier identifier : endPorts) {

            path.getElements().add(new MoveTo(startCoordinates.getX(),startCoordinates.getY()));

            List<Coordinates> corners = identifier.getCorners();
            Coordinates endCoordinates = identifier.getPort().getPosition();

            logger.info(String.format("printing line, startCoords: (%d, %d), endCoords: (%d, %d)", startCoordinates.getX(), startCoordinates.getY(), endCoordinates.getX(), endCoordinates.getY() ));

            for (int i=0;i<corners.size();i++) {
                Coordinates cornerCoords = corners.get(i);
                path.getElements().add(new LineTo(cornerCoords.getX(), cornerCoords.getY()));

                if(!breakFound && i>0) {
                    if(i<oldCorners.size()) {
                        if (!cornerCoords.equals(oldCorners.get(i))) {
                            group.getChildren().add(new Circle(corners.get(i-1).getX(), corners.get(i-1).getY(), 3));
                        }
                    }
                }
            }

            path.getElements().add(new LineTo(endCoordinates.getX(),endCoordinates.getY()));

            oldCorners = corners;
            breakFound = false;
        }


    }

    private ArrayList<WireIdentifier> addCorners(Coordinates startCoordinates, ArrayList<WireIdentifier> endPorts) {

        for(WireIdentifier endPort : endPorts) {
            Coordinates endPosition = endPort.getPort().getPosition();

            if(endPort.getCorners().isEmpty() && !(startCoordinates.getY() == endPosition.getY())) {
                int newX = (int) (SIZE * (Math.ceil(startCoordinates.getX()*1.0/SIZE)));
                endPort.getCorners().add(new Coordinates(newX, startCoordinates.getY()));
                endPort.getCorners().add(new Coordinates(newX, endPosition.getY()));
            }
        }
        return endPorts;
    }

    public void showSignal() {
        Logic inputLogic = wire.getInput().getLogic();

        if(inputLogic.isUndefined()) {
            path.setStroke(Paint.valueOf(LOGIC_UNDEFINED_COLOUR));
        } else if(inputLogic.value()) {
            path.setStroke(Paint.valueOf(LOGIC_1_COLOUR));
        } else {
            path.setStroke(Paint.valueOf(LOGIC_0_COLOUR));
        }
    }

    public void passSignal() {
        wire.passSignal();
    }

    public Wire getWire() {
        return wire;
    }

    public Parent getGroup() {
        return group;
    }

    public String getUuid() {
        return wire.getUuid();
    }
}
