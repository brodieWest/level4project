package main.ui.simulation.model;

import main.ui.component.model.component.Component;
import main.ui.component.model.component.Io.Output;
import main.ui.component.model.component.ReusableComponent;
import main.ui.simulation.MainSimulationController;
import main.ui.simulation.SimulationController;
import main.ui.wire.Wire;
import main.ui.port.Port;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Simulator {

    private int pathDepth;

    private static Logger logger = LogManager.getLogger(Simulator.class);

    public void calculatePathDepth(List<Component> outputs, MainSimulationController simulationController) {
        int newPathDepth = 0;
        while(!isValid(outputs)) {
            simulationController.gateDelay();
            newPathDepth++;
        }

        pathDepth = newPathDepth;

        simulationController.resetSimulation();
        simulationController.wireDelay();
        simulationController.getMainController().setGateDelayCount(0);

        logger.info(String.format("Path Depth is %d ", pathDepth));
    }

    private boolean isValid(List<Component> outputs) {
        for(Component output : outputs) {
            if(output.getInput(0).isUndefined()) {
                return false;
            }
        }
        return true;
    }

    public int getPathDepth() {
        return pathDepth;
    }
}