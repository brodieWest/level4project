package javafx.component.factories;

import javafx.component.controllers.ComponentController;
import javafx.component.controllers.InputController;
import javafx.component.controllers.WordInputController;
import javafx.component.model.component.Component;
import javafx.component.model.component.ComponentParameters;
import javafx.component.model.component.Io.Input;
import javafx.component.model.component.Io.WordInput;
import javafx.simulation.SimulationController;

public class WordInputFactory extends SingleComponentFactory {

    @Override
    public ComponentController getComponentController(SimulationController simulationController, ComponentParameters componentParameters) {
        Component componentModel = new WordInput(componentParameters);
        return new WordInputController(simulationController, componentModel);
    }
}
