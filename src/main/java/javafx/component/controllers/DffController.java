package javafx.component.controllers;

import javafx.component.Synchronous;
import javafx.component.model.component.Component;
import javafx.simulation.SimulationController;
import model.Coordinates;

public class DffController extends ComponentController implements Synchronous{


    public DffController(SimulationController simulationController, String type, Coordinates coordinates, String uuid, int noInputs, int noOutputs) {
        super(simulationController, type, coordinates, uuid, noInputs, noOutputs);
    }

    @Override
    public void processClockTick() {
        Synchronous synchronous = (Synchronous)componentModel;
        synchronous.processClockTick();
    }

}
