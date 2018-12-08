package javafx.component.controllers;

import javafx.component.WordComponent;
import javafx.component.model.component.Component;
import javafx.component.model.component.word.Split;
import javafx.simulation.SimulationController;

public class WordComponentController extends ComponentController implements WordComponent {
    public WordComponentController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
        simulationController.addWordComponent(this);
    }

    @Override
    public void wireDelay() {
        ((WordComponent)componentModel).wireDelay();
    }
}
