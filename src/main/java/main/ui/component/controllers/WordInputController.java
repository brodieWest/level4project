package main.ui.component.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import main.ui.component.InputControllerInterface;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.Io.WordInput;
import main.ui.simulation.SimulationController;
import main.ui.wire.Wire;

import java.util.function.UnaryOperator;

public class WordInputController extends IoController implements InputControllerInterface {

    @FXML
    private TextField textField;


    public WordInputController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
        simulationController.addInput(this);
        changeWordValue(null);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("([0-9]|[a-f])|")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);

        textField.setTextFormatter(textFormatter);
    }

    @FXML
    private void changeWordValue(KeyEvent keyEvent) {
        WordInput wordInput = (WordInput) componentModel;

        simulationController.resetSimulation();
        String character = " ";
        if(keyEvent != null) {
            character = keyEvent.getCharacter();
        }
        String textValue;
        if(character.matches("([0-9]|[a-f])")) {
            textValue = character;
        } else {
            textValue = textField.getCharacters().toString();
        }
        if(textValue.equals("")) return;
        int wordValue = Integer.parseInt(textValue,16);
        wordInput.setWordValue(wordValue);
        simulationController.wireDelay();

    }

    @Override
    public void ioShowValue(String textString, String colour) {
        svgPath.setFill(Paint.valueOf(colour));
    }

    @FXML
    private void select() {
        textField.selectAll();
    }


    @Override
    public Wire getWire() {
        return componentModel.getOutput(0).getWire();
    }
}
