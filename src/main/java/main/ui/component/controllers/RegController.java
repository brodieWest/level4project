package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import main.model.Logic;
import main.ui.component.model.component.Dff;
import main.ui.component.model.component.ReusableComponent;
import main.ui.simulation.SimulationController;

import java.io.FileNotFoundException;

public class RegController extends ReusableComponentController implements Comparable<RegController>{


    public RegController(SimulationController simulationController, ReusableComponent componentModel) throws FileNotFoundException {
        super(simulationController, componentModel);

        svgGroup.getChildren().add(displayValue);
    }

    public Logic getDffLogic() {
        return internalSimulation.getDffLogic();
    }

    @Override
    public void processClockTick() {
        super.processClockTick();
        displayValue.setText(internalSimulation.getDffValue());
    }

    @Override
    public int compareTo(RegController other) {
        return other.componentModel.getCoordinates().getY() - componentModel.getCoordinates().getY();
    }
}
