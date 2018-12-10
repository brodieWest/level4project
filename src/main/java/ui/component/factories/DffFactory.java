package ui.component.factories;

import ui.component.controllers.ComponentController;
import ui.component.controllers.DffController;
import ui.component.model.component.Component;
import ui.component.model.component.ComponentParameters;
import ui.component.model.component.Dff;
import ui.simulation.SimulationController;

public class DffFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Dff(componentParameters);
        return new DffController(simulationController, componentModel);
    }
}
