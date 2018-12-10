package ui.component.factories;

import ui.component.controllers.ComponentController;
import ui.component.controllers.WordComponentController;
import ui.component.model.component.Component;
import ui.component.model.component.ComponentParameters;
import ui.component.model.component.word.Join;
import ui.simulation.SimulationController;

public class JoinFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Join(componentParameters);
        return new WordComponentController(simulationController, componentModel);
    }
}
