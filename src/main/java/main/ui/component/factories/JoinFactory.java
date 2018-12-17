package main.ui.component.factories;

import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.WordComponentController;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.component.model.component.word.Join;
import main.ui.simulation.SimulationController;

public class JoinFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Join(componentParameters);
        return new WordComponentController(simulationController, componentModel);
    }
}
