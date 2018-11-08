package javafx.component.controllers;

import javafx.component.Synchronous;
import javafx.component.model.component.Component;
import javafx.simulation.SimulationController;
import model.Coordinates;

public class DffController extends ComponentController implements Synchronous{


    public DffController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
    }

    @Override
    public void processClockTick() {
        Synchronous synchronous = (Synchronous)componentModel;
        synchronous.processClockTick();
    }

}
