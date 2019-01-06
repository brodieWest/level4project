package main.ui.component.factories;

import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.WordOutputController;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.component.model.component.Io.Output;
import main.ui.component.model.component.Io.WordOutput;
import main.ui.simulation.SimulationController;

public class WordOutputFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new WordOutput(componentParameters);
        return new WordOutputController(simulationController, componentModel);
    }
}
