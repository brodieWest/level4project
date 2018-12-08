package javafx.component.controllers;

import javafx.component.OutputControllerInterface;
import javafx.component.model.component.Component;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.simulation.SimulationController;

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
