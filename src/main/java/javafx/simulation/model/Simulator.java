package javafx.simulation.model;

import javafx.component.Synchronous;
import javafx.component.model.component.Component;
import javafx.component.model.component.Io.Input;
import javafx.component.model.component.Io.Output;
import javafx.wire.Wire;
import model.Port;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Simulator {

    private int pathDepth;

    public void calculatePathDepth(List<Input> inputs) {
        int maxPathDepth = 0;
        Set<Port> visitedInputPorts = new HashSet<>();

        Set<Component> components = new HashSet<>(inputs);

        while(!components.isEmpty()) {
            Component component = components.iterator().next();
            components.remove(component);
            int currentDepth = component.getPathDepth();
            List<Wire> outputWires = component.getNextWires();


            if(currentDepth > maxPathDepth && !(component instanceof Output)) {
                maxPathDepth = currentDepth;
            }

            for(Wire wire : outputWires) {
                List<Port> outputs = wire.getOutputs();

                for(Port output : outputs) {
                    if(!visitedInputPorts.contains(output)) {
                        Component newComponent = output.getComponent();
                        components.add(newComponent);
                        visitedInputPorts.add(output);
                        if(newComponent.getPathDepth() < (currentDepth+1)) {
                            newComponent.setPathDepth(currentDepth+1);
                        }
                    }
                }
            }
        }
        pathDepth = maxPathDepth;
    }

    public int getPathDepth() {
        return pathDepth;
    }
}