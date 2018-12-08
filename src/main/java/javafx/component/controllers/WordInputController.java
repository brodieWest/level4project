package javafx.component.controllers;

import javafx.component.model.component.Component;
import javafx.component.model.component.Io.WordInput;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.simulation.SimulationController;

public class WordInputController extends ComponentController {

    @FXML
    private TextField textField;

    public WordInputController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
    }


    public void changeWordValue() {
        WordInput wordInput = (WordInput) componentModel;

        simulationController.resetSimulation();
        wordInput.setWordValue(Integer.parseInt(textField.getCharacters().toString(),16));
        simulationController.wireDelay();
    }
}