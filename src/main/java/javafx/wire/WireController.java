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

    public WireController(Component startComponent, int startPortNo, Component endComponent, int endPortNo) {
        FxmlLoaderUtils.loadFxml(WIRE_PATH, this);

        Port startPort = startComponent.getOutput(startPortNo);
        int startX = startComponent.getCoordinates().getX() + startPort.getOffset().getX();
        int startY = startComponent.getCoordinates().getY() + startPort.getOffset().getY();

        Port endPort = endComponent.getInput(endPortNo);
        int endX = endComponent.getCoordinates().getX() + endPort.getOffset().getX();
        int endY = endComponent.getCoordinates().getY() + endPort.getOffset().getY();

        wire.setInput(startPort);
        wire.addOutput(endPort);
        startPort.setWire(wire);
        endPort.setWire(wire);

        displayWire(new Coordinates(startX, startY), new Coordinates(endX, endY));
    }

    public void initialiseWire(Wire wire, Component startComponent, int startPortNo, Component endComponent, int endPortNo) {

    }

    private void displayWire(Coordinates startCoordinates, Coordinates endCoordinates) {
        System.out.println("printing line, startCoords: " + startCoordinates.getX() + ", " + startCoordinates.getY() + " endCoords: " + endCoordinates.getX() + ", " + endCoordinates.getY() );
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
