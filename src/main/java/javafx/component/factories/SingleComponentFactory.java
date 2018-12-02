package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.model.component.ComponentParameters;
import javafx.simulation.SimulationController;
import model.Coordinates;

import java.io.FileNotFoundException;

public abstract class SingleComponentFactory {

    public abstract ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) throws FileNotFoundException;
}
