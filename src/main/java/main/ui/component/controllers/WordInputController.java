package main.ui.component.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.Io.WordInput;
import main.ui.simulation.SimulationController;

import java.util.function.UnaryOperator;

public class WordInputController extends ComponentController {

    @FXML
    private TextField textField;

    private String oldTextValue;

    public WordInputController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
        changeWordValue();

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("([0-9]|[a-f])*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);

        textField.setTextFormatter(textFormatter);
    }

    @FXML
    private void changeWordValue() {
        WordInput wordInput = (WordInput) componentModel;

        simulationController.resetSimulation();
        String textValue = textField.getCharacters().toString();
        if(textValue.equals("")) return;
        int wordValue = Integer.parseInt(textValue,16);
        wordInput.setWordValue(wordValue);
        simulationController.wireDelay();

    }

    @FXML
    private void select() {
        textField.selectAll();
    }
}
