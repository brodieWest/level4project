package main.ui.component.factories;

import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.OutputController;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.component.model.component.Io.Output;
import main.ui.simulation.SimulationController;

public class OutputFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Output(componentParameters);
        return new OutputController(simulationController, componentModel);
    }
}
