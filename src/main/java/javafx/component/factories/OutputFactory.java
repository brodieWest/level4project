package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.controllers.OutputController;
import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import javafx.component.model.component.Io.Output;
import javafx.component.model.component.gates.AndGate;
import javafx.simulation.SimulationController;
import model.Coordinates;

public class OutputFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Output(componentParameters);
        return new OutputController(simulationController, componentModel);
    }
}
