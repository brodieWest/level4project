package javafx.wire;

import javafx.Controller;
import javafx.component.model.component.Component;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import model.Coordinates;
import javafx.fxml.FXML;
import javafx.scene.shape.Path;
import model.Port;

public class WireController implements Controller {
    @FXML
    private Path path;

    private Wire wire;

    public void initialiseWire(Wire wire, Component startComponent, int startPortNo, Component endComponent, int endPortNo) {
        this.wire = wire;

        Port startPort = startComponent.getOutput(startPortNo);
        int startX = startComponent.getCoordinates().getX() + startPort.getOffset().getX();
        int startY = startComponent.getCoordinates().getY() + startPort.getOffset().getY();

        Port endPort = endComponent.getInput(endPortNo);
        int endX = endComponent.getCoordinates().getX() + endPort.getOffset().getX();
        int endY = endComponent.getCoordinates().getY() + endPort.getOffset().getY();

        wire.setInput(startPort);
        wire.addOutput(endPort);

        displayWire(new Coordinates(startX, startY), new Coordinates(endX, endY));
    }

    private void displayWire(Coordinates startCoordinates, Coordinates endCoordinates) {
        System.out.println("printing line, startCoords: " + startCoordinates.getX() + ", " + startCoordinates.getY() + " endCoords: " + endCoordinates.getX() + ", " + endCoordinates.getY() );
        MoveTo moveToStart = new MoveTo(startCoordinates.getX(),startCoordinates.getY());
        LineTo lineToEnd = new LineTo(endCoordinates.getX(),endCoordinates.getY());
        path.getElements().add(moveToStart);
        path.getElements().add(lineToEnd);

    }

    public void deleteOnClick() {
        path.setStroke(Paint.valueOf("yellow"));
    }

    public Wire getWire() {
        return wire;
    }
}
