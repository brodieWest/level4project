package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.controllers.WordOutputController;
import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import javafx.component.model.component.Io.Output;
import javafx.simulation.SimulationController;

public class WordOutputFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Output(componentParameters);
        return new WordOutputController(simulationController, componentModel);
    }
}
