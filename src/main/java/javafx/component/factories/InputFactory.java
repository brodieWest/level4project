package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.controllers.InputController;
import javafx.component.model.component.Component;
import javafx.component.model.component.Io.Input;
import javafx.component.model.component.gates.AndGate;
import javafx.simulation.SimulationController;
import model.Coordinates;

public class InputFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {
        Component componentModel = new Input(coordinates, uuid, type, noInputs,noOutputs);
        return new InputController(simulationController, componentModel);
    }
}
