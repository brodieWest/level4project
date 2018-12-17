package main.ui.component.controllers;

import main.ui.component.Synchronous;
import main.ui.component.model.component.Component;
import main.ui.simulation.SimulationController;

public class DffController extends ComponentController implements Synchronous{


    public DffController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
        simulationController.addSynchronous(this);
    }

    @Override
    public void processClockTick() {
        Synchronous synchronous = (Synchronous)componentModel;
        synchronous.processClockTick();
    }

}
