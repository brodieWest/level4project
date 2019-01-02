package main.ui.component.model.component;

import main.ui.Onscreen;
import main.model.*;
import main.ui.port.Port;
import main.ui.port.PortController;
import main.ui.wire.Wire;

import java.util.*;

public abstract class Component implements Onscreen {

    private Map<PortType,List<Port>> portsByType = new TreeMap<>();

    private Map<Direction,List<Port>> portsByDirection = new TreeMap<>();

    private List<Port> ports = new ArrayList<>();

    private List<Port> inputs = new ArrayList<>();
    private List<Port> outputs = new ArrayList<>();

    private List<Port> westPorts = new ArrayList<>();
    private List<Port> eastPorts = new ArrayList<>();
    private List<Port> southPorts = new ArrayList<>();
    private List<Port> northPorts = new ArrayList<>();

    private Coordinates coordinates;

    private String uuid;
    private String type;

    private int pathDepth = 0;

    private int HEIGHT = 100;
    private int WIDTH = 100;
    private int PORT_OFFSET = 20;

    public Component(ComponentParameters componentParameters){
        this.coordinates = componentParameters.getCoordinates();
        this.uuid = componentParameters.getUuid();
        this.type = componentParameters.getType();

        portsByType.put(PortType.INPUT, inputs);
        portsByType.put(PortType.OUTPUT, outputs);

        portsByDirection.put(Direction.EAST,eastPorts);
        portsByDirection.put(Direction.WEST,westPorts);
        portsByDirection.put(Direction.SOUTH,southPorts);
        portsByDirection.put(Direction.NORTH,northPorts);

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

    private List<PortParameters> addDefaultPorts(PortType portType, int no) {
        List<PortParameters> portParameters = new ArrayList<>();
        for(int i=0;i<no;i++) {
            portParameters.add(new PortParameters(portType.getDirection(), portType));
        }
        return portParameters;
    }

    private void addPorts(List<PortParameters> portParameters) {
        if(portParameters.isEmpty()) {
            portParameters.addAll(addDefaultPorts(PortType.OUTPUT,getDefaultOutputs()));
            portParameters.addAll(addDefaultPorts(PortType.INPUT,getDefaultInputs()));
        }

        for(PortParameters parameters : portParameters) {
            PortType portType = parameters.getPortType();
            Port port = new Port(this, parameters, portsByType.get(portType).size());

            ports.add(port);

            portsByType.get(portType).add(port);

            Direction direction = parameters.getDirection();
            portsByDirection.get(direction).add(port);
        }

        positionPorts();

    }

    public void positionPorts() {
        for(Direction direction : Direction.values()) {
            List<Port> portsDirection = portsByDirection.get(direction);
            int portsDirectionSize = portsDirection.size();
            for(int i = 0; i < portsDirectionSize; i++) {
                Port currentPort = portsDirection.get(i);
                currentPort.setOffset(getPortOffset(direction,i));
            }
        }
    }

    private Coordinates getPortOffset(Direction direction, int position) {
        if(direction == Direction.EAST || direction == Direction.WEST) {
            return new Coordinates(WIDTH/2, PORT_OFFSET + (position+1) * ((HEIGHT-2*PORT_OFFSET)/(portsByDirection.get(direction).size()+1)));
        }
        return  new Coordinates(PORT_OFFSET + (position+1) * ((WIDTH-2*PORT_OFFSET)/(portsByDirection.get(direction).size()+1)) , HEIGHT/2);
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

    public List<Port> getPortsByDirection(Direction direction) {
        return portsByDirection.get(direction);
    }

    private List<Port> getPorts(PortType portType) {
        if(portType == PortType.INPUT) {
            return inputs;
        } else {
            return outputs;
        }
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

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
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

        portLocations.putAll(getPortLocation(PortType.INPUT));

        portLocations.putAll(getPortLocation(PortType.OUTPUT));

        return portLocations;
    }

    private Map<String,Integer> getPortLocation(PortType portType) {
        List<Port> ports = getPorts(portType);

        Map<String,Integer> portLocations = new TreeMap<>();

        for(int i=0;i<ports.size();i++) {
            String portId = uuid+"."+portType.getIdentifier()+i+".";
            Coordinates portPosition = ports.get(i).getPosition();

            portLocations.put(portId+"x", portPosition.getX());
            portLocations.put(portId+"y", portPosition.getY());
        }
        return portLocations;
    }

    public ComponentParametersModel getComponentParameters() {
        List<PortParameters> inputPorts = new ArrayList<>();
        for(Port input : inputs) {
            inputPorts.add(input.getParameters());
        }
        List<PortParameters> outputPorts = new ArrayList<>();
        for(Port output : outputs) {
            outputPorts.add(output.getParameters());
        }
        return new ComponentParametersModel(coordinates,uuid,type,inputPorts,outputPorts);
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public int getPORT_OFFSET() {
        return PORT_OFFSET;
    }

    public void setPORT_OFFSET(int PORT_OFFSET) {
        this.PORT_OFFSET = PORT_OFFSET;
    }

    public List<PortController> getPortControllers() {
        List<PortController> portControllers = new ArrayList<>();
        for(Port port : ports) {
            portControllers.add(port.getPortController());
        }
        return portControllers;
    }
}
