package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import javafx.component.model.component.gates.AndGate;
import javafx.component.model.component.gates.OrGate;
import javafx.simulation.SimulationController;
import model.Coordinates;

public class OrFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new OrGate(componentParameters);
        return new ComponentController(simulationController, componentModel);
    }
}
