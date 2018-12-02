package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.controllers.DffController;
import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import javafx.component.model.component.Dff;
import javafx.component.model.component.gates.AndGate;
import javafx.simulation.SimulationController;
import model.Coordinates;

public class DffFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Dff(componentParameters);
        return new DffController(simulationController, componentModel);
    }
}
