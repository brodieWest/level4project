package ui.component.factories;

import ui.component.controllers.ComponentController;
import ui.component.model.component.Component;
import ui.component.model.component.ComponentParameters;
import ui.component.model.component.gates.NotGate;
import ui.simulation.SimulationController;

public class NotFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new NotGate(componentParameters);
        return new ComponentController(simulationController, componentModel);
    }
}
