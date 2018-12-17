package main.ui.component.controllers;

import main.ui.component.WordComponent;
import main.ui.component.model.component.Component;
import main.ui.simulation.SimulationController;

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
