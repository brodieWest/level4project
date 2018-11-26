package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.model.component.Component;
import javafx.component.model.component.gates.AndGate;
import javafx.component.model.component.gates.NotGate;
import javafx.simulation.SimulationController;
import model.Coordinates;

public class NotFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {
        Component componentModel = new NotGate(coordinates, uuid, type, noInputs,noOutputs);
        return new ComponentController(simulationController, componentModel);
    }
}
