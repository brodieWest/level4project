package main.ui.wire;

import javafx.fxml.FXML;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import main.model.*;
import main.ui.main.Mainfx;
import main.ui.port.Port;
import main.ui.simulation.SimulationController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main.fxml.FxmlLoaderUtils;

import java.util.ArrayList;

public class WordWireController extends WireController {

    @FXML
    private Path outerPath;

    @FXML
    private Path innerPath;

    private static String FXML_PATH = "fxml/wordWire.fxml";

    private static Logger logger = LogManager.getLogger(WordWireController.class);

    public WordWireController(SimulationController simulationController,String uuid, Port startPort, ArrayList<WireIdentifier> endPorts) {
        super(simulationController,uuid, startPort, endPorts);
    }

    @Override
    public void loadFxml() {
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(FXML_PATH), this);
    }

    @Override
    public void addMoveTo(Coordinates coordinates) {
        innerPath.getElements().add(new MoveTo(coordinates.getX(),coordinates.getY()));
        outerPath.getElements().add(new MoveTo(coordinates.getX(),coordinates.getY()));
    }

    @Override
    public void displayLine(Coordinates coordinates) {
        innerPath.getElements().add(new LineTo(coordinates.getX(),coordinates.getY()));
        outerPath.getElements().add(new LineTo(coordinates.getX(),coordinates.getY()));
    }

    @Override
    public void showSignal() {
    }

    @Override
    public void setInitialColour() {
    }

    @Override
    public void setDeletable(boolean deletable) {
        if(deletable) {
            outerPath.setOnMouseClicked(event -> delete());
            innerPath.setOnMouseClicked(event -> delete());
        } else {
            outerPath.setOnMouseClicked(event -> {});
            innerPath.setOnMouseClicked(event -> {});
        }

    }

}
