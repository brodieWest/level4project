package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.controllers.ReusableComponentController;
import javafx.component.model.component.Component;
import javafx.component.model.component.ReusableComponent;
import javafx.component.model.component.gates.AndGate;
import javafx.simulation.SimulationController;
import model.Coordinates;

import java.io.FileNotFoundException;

public class ReusableFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) throws FileNotFoundException {
        Component componentModel = new ReusableComponent(coordinates, uuid, type, noInputs,noOutputs);
        return new ReusableComponentController(simulationController, componentModel);
    }
}
