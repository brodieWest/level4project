package main.ui.component.factories;

import main.ui.component.controllers.ComponentController;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.component.model.component.gates.OrGate;
import main.ui.simulation.SimulationController;

public class OrFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new OrGate(componentParameters);
        return new ComponentController(simulationController, componentModel);
    }
}
