package main.ui.component.factories;

import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.DffController;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.component.model.component.Dff;
import main.ui.simulation.SimulationController;

public class DffFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Dff(componentParameters);
        return new DffController(simulationController, componentModel);
    }
}
