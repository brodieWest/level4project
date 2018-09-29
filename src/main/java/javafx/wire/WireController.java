package javafx.wire;

import javafx.Controller;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import model.Coordinates;
import javafx.fxml.FXML;
import javafx.scene.shape.Path;

public class WireController implements Controller {
    @FXML
    private Path path;

    private Wire wire;

    public void initialiseWire(Wire wire) {
        this.wire = wire;
    }

    public void displayWire(Coordinates startCoordinates, Coordinates endCoordinates) {
        MoveTo moveToStart = new MoveTo(startCoordinates.getX(),startCoordinates.getY());
        LineTo lineToEnd = new LineTo(endCoordinates.getX(),endCoordinates.getY());
        path.getElements().add(moveToStart);
        path.getElements().add(lineToEnd);
    }
}
