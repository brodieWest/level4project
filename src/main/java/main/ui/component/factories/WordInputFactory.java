package main.ui.component.factories;

import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.WordInputController;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.component.model.component.Io.WordInput;
import main.ui.simulation.SimulationController;

public class WordInputFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new WordInput(componentParameters);
        return new WordInputController(simulationController, componentModel);
    }
}
