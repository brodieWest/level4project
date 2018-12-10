package ui.component.factories;

import ui.component.controllers.ComponentController;
import ui.component.controllers.ReusableComponentController;
import ui.component.model.component.ComponentParameters;
import ui.component.model.component.ReusableComponent;
import ui.simulation.SimulationController;

import java.io.FileNotFoundException;

public class ReusableFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) throws FileNotFoundException {
        ReusableComponent componentModel = new ReusableComponent(componentParameters);
        return new ReusableComponentController(simulationController, componentModel);
    }
}
