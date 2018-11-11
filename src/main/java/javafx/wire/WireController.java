package javafx.wire;

import javafx.Controller;
import javafx.component.model.component.Component;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import model.Coordinates;
import javafx.fxml.FXML;
import javafx.scene.shape.Path;
import model.Logic;
import model.Port;
import model.WireIdentifier;
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
    private Parent group;

    private Wire wire;

    private static String LOGIC_0_COLOUR = "0x7293cb";
    private static String LOGIC_1_COLOUR = "0xd35e60";
    private static String LOGIC_UNDEFINED_COLOUR = "grey";

    private static String WIRE_PATH = "fxml/wire.fxml";

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
            displayWire(startPort.getPosition(), endPort.getPosition(), identifier.getCorners());
        }
    }

    private void displayWire(Coordinates startCoordinates, Coordinates endCoordinates, List<Coordinates> corners) {
        logger.info(String.format("printing line, startCoords: (%d, %d), endCoords: (%d, %d)", startCoordinates.getX(), startCoordinates.getY(), endCoordinates.getX(), endCoordinates.getY() ));

        path.getElements().add(new MoveTo(startCoordinates.getX(),startCoordinates.getY()));

        for(Coordinates cornerCoords : corners) {
            path.getElements().add(new LineTo(cornerCoords.getX(), cornerCoords.getY()));
        }

        path.getElements().add(new LineTo(endCoordinates.getX(),endCoordinates.getY()));
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
