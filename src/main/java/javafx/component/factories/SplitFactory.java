package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.controllers.WordComponentController;
import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import javafx.component.model.component.word.Split;
import javafx.simulation.SimulationController;

public class SplitFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new Split(componentParameters);
        return new WordComponentController(simulationController, componentModel);
    }
}