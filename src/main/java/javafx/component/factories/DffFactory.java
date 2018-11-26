package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.controllers.DffController;
import javafx.component.model.component.Component;
import javafx.component.model.component.Dff;
import javafx.component.model.component.gates.AndGate;
import javafx.simulation.SimulationController;
import model.Coordinates;

public class DffFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {
        Component componentModel = new Dff(coordinates, uuid, type, noInputs,noOutputs);
        return new DffController(simulationController, componentModel);
    }
}
