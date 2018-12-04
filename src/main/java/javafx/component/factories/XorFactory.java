package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import javafx.component.model.component.gates.AndGate;
import javafx.component.model.component.gates.XorGate;
import javafx.simulation.SimulationController;

public class XorFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new XorGate(componentParameters);
        return new ComponentController(simulationController, componentModel);
    }
}
