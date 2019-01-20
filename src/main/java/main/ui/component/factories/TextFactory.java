package main.ui.component.factories;

import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.TextController;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.component.model.component.TextComponent;
import main.ui.component.model.component.gates.AndGate;
import main.ui.simulation.SimulationController;

public class TextFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new TextComponent(componentParameters);
        return new TextController(simulationController, componentModel);
    }
}
