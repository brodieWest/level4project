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
import model.PortIdentifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.fxml.FxmlLoaderUtils;

public class WireController implements Controller {
    @FXML
    private Path path;

    @FXML
    private Parent group;

    private Wire wire = new Wire();

    private static String LOGIC_0_COLOUR = "0x7293cb";
    private static String LOGIC_1_COLOUR = "0xd35e60";
    private static String LOGIC_UNDEFINED_COLOUR = "grey";

    private static String WIRE_PATH = "fxml/wire.fxml";

    private static Logger logger = LogManager.getLogger(WireController.class);

    public WireController(Port startPort, Port endPort) {
        FxmlLoaderUtils.loadFxml(WIRE_PATH, this);

        wire.setInput(startPort);
        wire.addOutput(endPort);
        startPort.setWire(wire);
        endPort.setWire(wire);

        displayWire(startPort.getPosition(), endPort.getPosition());
    }

    private void displayWire(Coordinates startCoordinates, Coordinates endCoordinates) {
        logger.info(String.format("printing line, startCoords: (%d, %d), endCoords: (%d, %d)", startCoordinates.getX(), startCoordinates.getY(), endCoordinates.getX(), endCoordinates.getY() ));
        MoveTo moveToStart = new MoveTo(startCoordinates.getX(),startCoordinates.getY());
        LineTo lineToEnd = new LineTo(endCoordinates.getX(),endCoordinates.getY());
        path.getElements().add(moveToStart);
        path.getElements().add(lineToEnd);

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
