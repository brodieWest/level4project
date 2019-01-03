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

public class WireBuilderController implements Controller {

    @FXML
    private Group group;

    @FXML
    private Path path;

    private LineTo lineTo;

    private static String WIRE_PATH = "fxml/wire.fxml";

    public WireBuilderController() {
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(WIRE_PATH), this);
    }

    private PortController wireBuilderStartPort;

    public PortController getWireBuilderStartPort() {
        return wireBuilderStartPort;
    }

    public void setWireBuilderStartPort(PortController wireBuilderStartPort) {
        this.wireBuilderStartPort = wireBuilderStartPort;
    }

    public Path getPath() {
        return path;
    }


    public void startLine(Coordinates coordinates) {
        path.getElements().clear();
        path.getElements().add(new MoveTo(coordinates.getX(),coordinates.getY()));
        lineTo = new LineTo(coordinates.getX(),coordinates.getY());
        path.getElements().add(lineTo);
    }

    public void displayLine(Coordinates coordinates) {
        lineTo.setX(coordinates.getX());
        lineTo.setY(coordinates.getY());
    }

    public void clear() {
        path.getElements().clear();
    }
}
