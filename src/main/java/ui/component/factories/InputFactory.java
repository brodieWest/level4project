package ui.component.factories;

import ui.component.controllers.ComponentController;
import ui.component.controllers.InputController;
import ui.component.model.component.Component;
import ui.component.model.component.ComponentParameters;
import ui.component.model.component.Io.Input;
import ui.simulation.SimulationController;

public class InputFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Input(componentParameters);
        return new InputController(simulationController, componentModel);
    }
}
