package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import main.ui.component.model.component.Component;
import main.ui.simulation.SimulationController;

public class TextController extends ComponentController {
    public TextController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
    }
}
