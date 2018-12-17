package main.ui.component.model.component;

import main.ui.Onscreen;
import main.model.*;
import main.ui.wire.Wire;

import java.util.*;

public abstract class Component implements Onscreen {
    protected List<Port> inputs = new ArrayList<>();
    protected List<Port> outputs = new ArrayList<>();

    private List<Port> westPorts = new ArrayList<>();
    private List<Port> eastPorts = new ArrayList<>();
    private List<Port> southPorts = new ArrayList<>();
    private List<Port> northPorts = new ArrayList<>();

    private Coordinates coordinates;

    private String uuid;
    private String type;

    private int pathDepth = 0;

    private int SIZE = 100;
    private int PORT_OFFSET = 20;

    public Component(ComponentParameters componentParameters){
        this.coordinates = componentParameters.getCoordinates();
        this.uuid = componentParameters.getUuid();
        this.type = componentParameters.getType();
        addPorts(componentParameters.getPortParameters());
    }

    // simulates the component over a single gate delay
    public abstract void processGateDelay();

    public abstract int getDefaultInputs();

    public abstract int getDefaultOutputs();

    public void reset() {
        for(Port input : inputs) {
            input.getLogic().setUndefined(true);
        }
        for(Port output : outputs) {
            output.getLogic().setUndefined(true);
        }
    }

    private void addPorts(List<PortParameters> portParameters) {
        if(portParameters.isEmpty()) {
            for(int i=0;i<getDefaultInputs();i++) {
                portParameters.add(new PortParameters(Direction.WEST, PortType.INPUT,1));
            }
            for(int i=0;i<getDefaultOutputs();i++) {
                portParameters.add(new PortParameters(Direction.EAST, PortType.OUTPUT,1));
            }
        }

        for(PortParameters parameters : portParameters) {
            Port port = new Port(this, parameters);
            if(parameters.getPortType() == PortType.INPUT) {
                inputs.add(port);
            } else {
                outputs.add(port);
            }
            if(parameters.getDirection() == Direction.WEST) {
                westPorts.add(port);
                for(int i = 0; i < westPorts.size(); i++) {
                    Port westPort = westPorts.get(i);
                    westPort.setOffset(new Coordinates(SIZE/2, PORT_OFFSET + (i+1) * ((SIZE-2*PORT_OFFSET)/(westPorts.size()+1)) ));
                }
            } else if(parameters.getDirection() == Direction.EAST) {
                eastPorts.add(port);
                for(int i = 0; i < eastPorts.size(); i++) {
                    Port eastPort = eastPorts.get(i);
                    eastPort.setOffset(new Coordinates(SIZE/2, PORT_OFFSET + (i+1) * ((SIZE-2*PORT_OFFSET)/(eastPorts.size()+1)) ));
                }
            } else if(parameters.getDirection() == Direction.SOUTH) {
                southPorts.add(port);
                for(int i = 0; i < southPorts.size(); i++) {
                    Port southPort = southPorts.get(i);
                    southPort.setOffset(new Coordinates(PORT_OFFSET + (i+1) * ((SIZE-2*PORT_OFFSET)/(southPorts.size()+1)), SIZE/2 ));
                }
            } else if(parameters.getDirection() == Direction.NORTH) {
                northPorts.add(port);
                for(int i = 0; i < northPorts.size(); i++) {
                    Port northPort = northPorts.get(i);
                    northPort.setOffset(new Coordinates(PORT_OFFSET + (i+1) * ((SIZE-2*PORT_OFFSET)/(northPorts.size()+1)) , SIZE/2));
                }
            }
        }

    }

    public Port getOutput(int outputNo) {
        return outputs.get(outputNo);
    }

    public Port getInput(int inputNo) {
        return inputs.get(inputNo);
    }

    public List<Port> getInputs() {
        return inputs;
    }

    public int getInputSize() {
        return inputs.size();
    }

    public int getOutputSize() {
        return outputs.size();
    }

    public List<Wire> getNextWires() {
        List<Wire> wires = new ArrayList<>();

        for(Port output : outputs) {
            wires.add(output.getWire());
        }
        return wires;
    }

    public void deleteIO(String name, PortType portType) {
        //TODO
    }

    public void addWireToInput(String inputName, Wire wire, PortType portType) {

    }

    public void deleteWireToIO(String name, PortType portType) {
        //TODO
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getStringIdentifier() {
        return type;
    }

    public String getUuid() {
        return uuid;
    }

    public int getPathDepth() {
        return pathDepth;
    }

    public void setPathDepth(int pathDepth) {
        this.pathDepth = pathDepth;
    }

    public Map<String,Integer> getPortLocations() {

        Map<String,Integer> portLocations = new TreeMap<>();

        for(int i=0;i<inputs.size();i++) {
            String portId = uuid+".input"+Integer.toString(i)+".";
            Coordinates portPosition = inputs.get(i).getPosition();

            portLocations.put(portId+"x", portPosition.getX());
            portLocations.put(portId+"y", portPosition.getY());
        }

        for(int i=0;i<outputs.size();i++) {
            String portId = uuid+".output"+Integer.toString(i)+".";
            Coordinates portPosition = outputs.get(i).getPosition();

            portLocations.put(portId+"x", portPosition.getX());
            portLocations.put(portId+"y", portPosition.getY());
        }

        return portLocations;
    }
}
