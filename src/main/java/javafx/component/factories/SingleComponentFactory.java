package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.simulation.SimulationController;
import model.Coordinates;

import java.io.FileNotFoundException;

public abstract class SingleComponentFactory {

    public abstract ComponentController getComponentController(SimulationController simulationController, String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) throws FileNotFoundException;
}
