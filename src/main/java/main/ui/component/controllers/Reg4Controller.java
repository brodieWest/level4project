package main.ui.component.controllers;

import main.ui.component.model.component.ReusableComponent;
import main.ui.simulation.SimulationController;

import java.io.FileNotFoundException;

public class Reg4Controller extends ReusableComponentController{

    public Reg4Controller(SimulationController simulationController, ReusableComponent componentModel) throws FileNotFoundException {
        super(simulationController, componentModel);

        svgGroup.getChildren().add(displayValue);
    }

    @Override
    public void processClockTick() {
        super.processClockTick();
        displayValue.setText(internalSimulation.getRegValues());
    }
}
