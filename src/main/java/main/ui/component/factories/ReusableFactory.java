package main.ui.component.factories;

import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.ReusableComponentController;
import main.ui.component.model.component.ComponentParameters;
import main.ui.component.model.component.ReusableComponent;
import main.ui.simulation.SimulationController;

import java.io.FileNotFoundException;

public class ReusableFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) throws FileNotFoundException {
        ReusableComponent componentModel = new ReusableComponent(componentParameters);
        return new ReusableComponentController(simulationController, componentModel);
    }
}
