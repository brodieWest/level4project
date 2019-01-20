package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.TextComponent;
import main.ui.simulation.SimulationController;

public class TextController extends ComponentController {

    @FXML
    private TextField textField;

    public TextController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
        String oldText = ((TextComponent)componentModel).getOldText();
        if(!oldText.equals("")) {
            textField.setText(oldText);
        }
    }

    public String getText() {
        return textField.getText();
    }

}
