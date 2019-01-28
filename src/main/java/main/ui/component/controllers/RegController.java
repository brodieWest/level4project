package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import main.ui.component.model.component.ReusableComponent;
import main.ui.simulation.SimulationController;

import java.io.FileNotFoundException;

public class RegController extends ReusableComponentController {


    public RegController(SimulationController simulationController, ReusableComponent componentModel) throws FileNotFoundException {
        super(simulationController, componentModel);

        svgGroup.getChildren().add(displayValue);
    }

    @Override
    public void processClockTick() {
        super.processClockTick();
        displayValue.setText(internalSimulation.getDffValue());
    }
}
