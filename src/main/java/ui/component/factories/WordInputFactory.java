package ui.component.factories;

import ui.component.controllers.ComponentController;
import ui.component.controllers.WordInputController;
import ui.component.model.component.Component;
import ui.component.model.component.ComponentParameters;
import ui.component.model.component.Io.WordInput;
import ui.simulation.SimulationController;

public class WordInputFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new WordInput(componentParameters);
        return new WordInputController(simulationController, componentModel);
    }
}
