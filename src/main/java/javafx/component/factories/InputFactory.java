package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.controllers.InputController;
import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import javafx.component.model.component.Io.Input;
import javafx.component.model.component.gates.AndGate;
import javafx.simulation.SimulationController;
import model.Coordinates;

public class InputFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Input(componentParameters);
        return new InputController(simulationController, componentModel);
    }
}
