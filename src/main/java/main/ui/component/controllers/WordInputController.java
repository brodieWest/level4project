package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.Io.WordInput;
import main.ui.simulation.SimulationController;

public class WordInputController extends ComponentController {

    @FXML
    private TextField textField;

    public WordInputController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
        changeWordValue();
    }

    @FXML
    private void changeWordValue() {
        WordInput wordInput = (WordInput) componentModel;

        simulationController.resetSimulation();
        wordInput.setWordValue(Integer.parseInt(textField.getCharacters().toString(),16));
        simulationController.wireDelay();
    }
}