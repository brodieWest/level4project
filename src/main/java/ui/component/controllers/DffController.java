package ui.component.controllers;

import ui.component.Synchronous;
import ui.component.model.component.Component;
import ui.simulation.SimulationController;

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
