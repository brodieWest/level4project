package javafx.simulation.model;

import javafx.component.Synchronous;
import javafx.component.model.component.Component;
import javafx.component.model.component.Io.Input;
import javafx.component.model.component.Io.Output;
import javafx.component.model.component.ReusableComponent;
import javafx.wire.Wire;
import model.Port;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Simulator {

    private int pathDepth;

    private static Logger logger = LogManager.getLogger(Simulator.class);

    public void calculatePathDepth(List<Component> inputs) {
        int maxPathDepth = 0;
        Set<Port> visitedInputPorts = new HashSet<>();
        Set<ReusableComponent> visitedReusables = new HashSet<>();
        Set<Component> visitedComponents = new HashSet<>();

        Set<Component> components = new HashSet<>(inputs);

        while(!components.isEmpty()) {
            Component component = components.iterator().next();
            components.remove(component);
            int currentDepth = component.getPathDepth();
            List<Wire> outputWires;

            if(component instanceof ReusableComponent && !visitedReusables.contains(component)) {
                ReusableComponent reusableComponent = (ReusableComponent) component;
                visitedReusables.add(reusableComponent);
                outputWires = reusableComponent.getInternalWires();
            } else  {
                outputWires = component.getNextWires();
            }


            if(currentDepth > maxPathDepth && !(component instanceof Output)) {
                maxPathDepth = currentDepth;
            }

            for(Wire wire : outputWires) {
                List<Port> outputs = wire.getOutputs();

                for(Port output : outputs) {
                    Component newComponent = output.getComponent();
                    visitedInputPorts.add(output);
                    if(visitedInputPorts.containsAll(newComponent.getInputs()) && !visitedComponents.contains(newComponent)) {
                        components.add(newComponent);
                        visitedComponents.add(newComponent);
                    }
                    if (newComponent.getPathDepth() < (currentDepth + 1)) {
                        newComponent.setPathDepth(currentDepth + 1);
                    }
                }
            }
        }
        pathDepth = maxPathDepth - visitedReusables.size() * 2;

        logger.info(String.format("Path Depth is %d ", pathDepth));
    }

    public int getPathDepth() {
        return pathDepth;
    }
}