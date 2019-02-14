package main.ui.simulation.model;

import main.ui.component.model.component.Component;
import main.ui.component.model.component.Io.Output;
import main.ui.component.model.component.ReusableComponent;
import main.ui.simulation.MainSimulationController;
import main.ui.simulation.SimulationController;
import main.ui.utils.AlertUtils;
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

    public boolean calculatePathDepth(List<Component> outputs, MainSimulationController simulationController) {
        int newPathDepth = 0;
        while(!isValid(outputs)) {
            if(!simulationController.gateDelay()) {
                handleAsynchronous();
                return false;
            }
            newPathDepth++;
        }

        pathDepth = newPathDepth;

        simulationController.resetSimulation();
        simulationController.wireDelay();
        simulationController.getMainController().setGateDelayCount(0);

        logger.info(String.format("Path Depth is %d ", pathDepth));
        return true;
    }

    private void handleAsynchronous() {
        logger.info("asynchronous");
        AlertUtils.information("Circuit contains a loop in combinational logic.\n\nAny feedback in the circuit should go through a Delay Flip Flop.");

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