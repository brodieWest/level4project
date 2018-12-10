package ui.component.controllers;

import ui.component.WordComponent;
import ui.component.model.component.Component;
import ui.simulation.SimulationController;

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
