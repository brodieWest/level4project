package main.ui.component.factories;

import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.InputController;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.component.model.component.Io.Input;
import main.ui.simulation.SimulationController;

public class InputFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Input(componentParameters);
        return new InputController(simulationController, componentModel);
    }
}
