package ui.component.factories;

import ui.component.controllers.ComponentController;
import ui.component.controllers.OutputController;
import ui.component.model.component.Component;
import ui.component.model.component.ComponentParameters;
import ui.component.model.component.Io.Output;
import ui.simulation.SimulationController;

public class OutputFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Output(componentParameters);
        return new OutputController(simulationController, componentModel);
    }
}
