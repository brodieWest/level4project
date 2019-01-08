package main.ui.wire;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import main.fxml.FxmlLoaderUtils;
import main.model.Coordinates;
import main.ui.Controller;
import main.ui.main.Mainfx;
import main.ui.port.PortController;
import main.ui.simulation.SimulationController;

import java.util.ArrayList;
import java.util.List;

public class WireBuilderController implements Controller {

    @FXML
    private Group group;

    @FXML
    private Path path;

    private SimulationController simulationController;

    private LineTo line1;

    private LineTo line2;

    private PortController wireBuilderStartPort;

    private List<Coordinates> corners = new ArrayList<>();

    private Coordinates startCoordinates;

    private static String WIRE_PATH = "fxml/wire.fxml";

    public WireBuilderController(SimulationController simulationController) {
        this.simulationController = simulationController;
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(WIRE_PATH), this);
    }

    public PortController getWireBuilderStartPort() {
        return wireBuilderStartPort;
    }

    public void setWireBuilderStartPort(PortController wireBuilderStartPort) {
        this.wireBuilderStartPort = wireBuilderStartPort;
    }

    public Path getPath() {
        return path;
    }

    public void startNewPath(Coordinates coordinates) {
        path.getElements().clear();
        path.getElements().add(new MoveTo(coordinates.getX(),coordinates.getY()));
        startLine(coordinates);
    }


    public void startLine(Coordinates coordinates) {
        if(startCoordinates != null) {
            corners.add(new Coordinates(coordinates.getX(), startCoordinates.getY()));
        }
        corners.add(coordinates);
        startCoordinates = coordinates;
        line1 = new LineTo();
        line2 = new LineTo();
        line1.setY(coordinates.getY());
        line2.xProperty().bind(line1.xProperty());
        line1.setX(coordinates.getX());
        line2.setY(coordinates.getY());
        path.getElements().add(line1);
        path.getElements().add(line2);
    }

    public void displayLine(Coordinates coordinates) {
        line1.setX(coordinates.getX());
        line2.setY(coordinates.getY());
    }

    public void clear() {
        path.getElements().clear();
        corners.clear();
        startCoordinates = null;
    }

    public List<Coordinates> getCorners() {
        return corners;
    }
}
