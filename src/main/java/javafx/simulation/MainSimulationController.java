package javafx.simulation;

import javafx.main.MainController;
import javafx.scene.Parent;

public class MainSimulationController extends SimulationController {

    private MainController mainController;

    private int gateDelayCount = 0;

    private int clockTickCount = 0;

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
}
