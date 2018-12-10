package ui.component.factories;

import ui.component.controllers.ComponentController;
import ui.component.model.component.Component;
import ui.component.model.component.ComponentParameters;
import ui.component.model.component.gates.OrGate;
import ui.simulation.SimulationController;

public class OrFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new OrGate(componentParameters);
        return new ComponentController(simulationController, componentModel);
    }
}
