package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import main.ui.component.WordComponent;
import main.ui.component.model.component.Component;
import main.ui.simulation.SimulationController;

public class WordComponentController extends ComponentController implements WordComponent {

    @FXML
    private Rectangle rectangle;

    private int SIZE = 100;

    private int OFFSET = 20;

    public WordComponentController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
        simulationController.addWordComponent(this);

        int wordSize = Math.max(componentModel.getOutputSize(),componentModel.getInputSize());

        component.prefHeight(SIZE * wordSize);

        rectangle.setHeight(SIZE * wordSize - OFFSET * 2);
    }

    @Override
    public void wireDelay() {
        ((WordComponent)componentModel).wireDelay();
    }
}
