package javafx.component.controllers;

import javafx.component.model.component.Component;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.simulation.SimulationController;

public class WordOutputController extends ComponentController {
    public WordOutputController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
    }

    @FXML
    private Text text;

    public void showOutputValue() {
        text.setText(componentModel.getInput(0).getWord().toString());
    }
}
