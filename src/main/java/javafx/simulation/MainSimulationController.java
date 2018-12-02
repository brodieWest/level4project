package javafx.simulation;

import javafx.component.controllers.ComponentController;
import javafx.component.controllers.InputController;
import javafx.component.model.component.Component;
import javafx.component.model.component.Io.Input;
import javafx.main.MainController;
import javafx.scene.Parent;
import javafx.simulation.model.Simulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainSimulationController extends SimulationController {

    private MainController mainController;

    private int gateDelayCount = 0;

    private int clockTickCount = 0;

    private Simulator simulator = new Simulator();

    public MainSimulationController(MainController mainController) {
        super();
        this.mainController = mainController;
    }

    @Override
    public void gateDelay() {
        super.gateDelay();
        updateGateDelayCount();
    }

    @Override
    public void clockTick() {
        super.clockTick();
        updateClockTickCount();

        for(int i=0;i<simulator.getPathDepth();i++) {
            gateDelay();
        }
    }

    @Override
    public void resetSimulation() {
        super.resetSimulation();
        clearGateDelayCount();
    }

    @Override
    public void clear() {
        super.clear();
        clearClockTickCount();
    }

    @Override
    public void displayText(Parent parent) {
        mainController.displayText(parent);
    }


    private void updateGateDelayCount() {
        gateDelayCount++;

        mainController.setGateDelayCount(gateDelayCount);
    }

    private void clearGateDelayCount() {
        gateDelayCount = 0;

        mainController.setGateDelayCount(gateDelayCount);
    }

    private void updateClockTickCount() {
        clockTickCount++;

        mainController.setClockTickCount(clockTickCount);
    }

    private void clearClockTickCount() {
        clockTickCount = 0;

        mainController.setClockTickCount(clockTickCount);
    }

    public void calculatePathDepth() {
        List<Input> inputs = new ArrayList<>();

        Collection<ComponentController> newComponentControllers = componentControllers.values();

        for(ComponentController componentController : newComponentControllers) {
            if(componentController instanceof InputController) {
                inputs.add((Input)componentController.getComponentModel());
            }

        }
        simulator.calculatePathDepth(inputs);
    }
}
