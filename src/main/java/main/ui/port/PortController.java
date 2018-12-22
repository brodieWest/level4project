package main.ui.port;

import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import main.fxml.FxmlLoaderUtils;
import main.model.Coordinates;
import main.model.PortParameters;
import main.ui.Controller;
import main.ui.component.model.component.Component;
import main.ui.main.Mainfx;

public class PortController implements Controller {

    @FXML
    private Line line;

    private Port port;

    private String PORT_FXML_PATH = "fxml/port.fxml";

    private static String LOGIC_UNDEFINED_COLOUR = "lightgrey";

    private static String TRANSPARENT = "transparent";

    PortController(Port port) {
        this.port = port;
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(PORT_FXML_PATH), this);
    }

    void hidePort() {
        line.setStroke(Paint.valueOf(TRANSPARENT));
    }

    public Line getLine() {
        return line;
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
