package main.ui.component.factories;

import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.WordComponentController;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.component.model.component.word.Split;
import main.ui.simulation.SimulationController;

public class SplitFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Split(componentParameters);
        return new WordComponentController(simulationController, componentModel);
    }
}
