package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import main.ui.component.Synchronous;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.Dff;
import main.ui.simulation.SimulationController;

public class DffController extends ComponentController implements Synchronous{

    @FXML
    Text displayValue;


    public DffController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
        simulationController.addSynchronous(this);
    }

    @Override
    public void processClockTick() {
        Synchronous synchronous = (Synchronous)componentModel;
        synchronous.processClockTick();
        displayValue.setText(((Dff)componentModel).getStoredValue().toString());
    }


    @Override
    public void wireDelay() {
        ((Synchronous) componentModel).wireDelay();
    }
}
