package ui.component.factories;

import ui.component.controllers.ComponentController;
import ui.component.model.component.ComponentParameters;
import ui.simulation.SimulationController;

import java.io.FileNotFoundException;

public abstract class SingleComponentFactory {

    public abstract ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) throws FileNotFoundException;
}
