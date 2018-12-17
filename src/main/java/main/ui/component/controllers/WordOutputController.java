package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import main.ui.component.OutputControllerInterface;
import main.ui.component.model.component.Component;
import main.ui.simulation.SimulationController;

public class WordOutputController extends ComponentController implements OutputControllerInterface {
    public WordOutputController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
        simulationController.addOutput(this);
    }

    @FXML
    private Text text;

    public void showOutputValue() {
        text.setText(componentModel.getInput(0).getWord().toString());
    }
}
