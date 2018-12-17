package main.ui.simulation;

import javafx.scene.Parent;
import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.DffController;
import main.ui.component.controllers.InputController;
import main.ui.component.model.component.Component;
import main.ui.main.MainController;
import main.ui.simulation.model.Simulator;

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

        if(mainController.getLongClockTick()) {
            for (int i = 0; i < simulator.getPathDepth(); i++) {
                gateDelay();
            }
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
        List<Component> inputs = new ArrayList<>();

        Collection<ComponentController> newComponentControllers = componentControllers.values();

        for(ComponentController componentController : newComponentControllers) {
            if(componentController instanceof InputController || componentController instanceof DffController) {
                inputs.add(componentController.getComponentModel());
            }

        }
        simulator.calculatePathDepth(inputs);
        mainController.setPathDepth(simulator.getPathDepth());
    }
}
