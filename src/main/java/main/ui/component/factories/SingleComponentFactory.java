package main.ui.component.factories;

import main.ui.component.controllers.ComponentController;
import main.ui.component.model.component.ComponentParameters;
import main.ui.simulation.SimulationController;

import java.io.FileNotFoundException;

public abstract class SingleComponentFactory {

    public abstract ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) throws FileNotFoundException;
}
