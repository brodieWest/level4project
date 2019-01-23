package main.ui.component.model.component;

import main.ui.Onscreen;
import main.model.*;
import main.ui.component.controllers.ComponentController;
import main.ui.port.Port;
import main.ui.port.PortController;
import main.ui.wire.Wire;

import java.util.*;

public abstract class Component implements Onscreen {

    ComponentController componentController;

    private Map<PortType,List<Port>> portsByType = new TreeMap<>();

    private Map<Direction,List<Port>> portsByDirection = new TreeMap<>();

    List<Port> ports = new ArrayList<>();

    private List<Port> inputs = new ArrayList<>();
    private List<Port> outputs = new ArrayList<>();

    private List<Port> westPorts = new ArrayList<>();
    private List<Port> eastPorts = new ArrayList<>();
    private List<Port> southPorts = new ArrayList<>();
    private List<Port> northPorts = new ArrayList<>();

    Coordinates coordinates;

    String uuid;
    String type;

    private int pathDepth = 0;

    private int initialRotate;

    private int HEIGHT = 100;
    private int WIDTH = 100;
    private int PORT_OFFSET = 20;
    private int SPACE_BETWEEN_PORTS = 20;
    private int SPACE_TO_FIRST_PORT = 10;

    public Component(ComponentParameters componentParameters){
        this.coordinates = componentParameters.getCoordinates();
        this.uuid = componentParameters.getUuid();
        this.type = componentParameters.getType();
        this.initialRotate = componentParameters.getRotate();

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

    public int defaultInputSize() {
        return 1;
    }

    public int defaultOutputSize() {
        return 1;
    }

    private List<PortParameters> addDefaultPorts(PortType portType, int no, int size) {
        List<PortParameters> portParameters = new ArrayList<>();
        for(int i=0;i<no;i++) {
            portParameters.add(new PortParameters(portType.getDirection(), portType,size));
        }
        return portParameters;
    }

    void addPorts(List<PortParameters> portParameters) {
        if(portParameters.isEmpty()) {
            portParameters.addAll(addDefaultPorts(PortType.OUTPUT,getDefaultOutputs(),defaultOutputSize()));
            portParameters.addAll(addDefaultPorts(PortType.INPUT,getDefaultInputs(),defaultInputSize()));
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
            if(portsDirectionSize>=4) {
                PORT_OFFSET = 10;
            }
            for(int i = 0; i < portsDirectionSize; i++) {
                int newIndex = i;
                if(direction == Direction.NORTH | direction == Direction.EAST) {
                    newIndex = portsDirectionSize - i - 1;
                }
                Port currentPort = portsDirection.get(newIndex);
                currentPort.setOffset(getPortOffset(direction,i));
            }
        }
    }

    private Coordinates getPortOffset(Direction direction, int position) {
        if(direction == Direction.EAST || direction == Direction.WEST) {
            int x = WIDTH/2;
            int y =getPortPosition(direction,position,HEIGHT);
            return new Coordinates(x,y);
        }
        int x = getPortPosition(direction,position,WIDTH);
        int y = HEIGHT/2;
        return  new Coordinates(x,y);
    }

    private int getPortPosition(Direction direction, int position, int length) {
        int size = portsByDirection.get(direction).size();
        if(size > 2) {
            return PORT_OFFSET + SPACE_TO_FIRST_PORT + (position) * SPACE_BETWEEN_PORTS;
        }
        return PORT_OFFSET + (position+1) * ((length-2*PORT_OFFSET)/(size+1));
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

    public void deleteWires() {
        for(Port port : ports) {
            if(port.hasWire()) {
                port.getWire().getWireController().delete();
            }
        }
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
        return new ComponentParametersModel(coordinates,saveFileUuid(),type,componentController.getRotation(),inputPorts,outputPorts);
    }

    public String saveFileUuid() {
        return uuid;
    }

    public void rotatePorts() {
        for(Direction direction : Direction.values()) {
            for(Port port : portsByDirection.get(direction)){
                port.setDirection(direction.getNextDirection());
            }
        }

        for (List<Port> ports : portsByDirection.values()) {
            ports.clear();
        }

        for (Port port : ports) {
            portsByDirection.get(port.getDirection()).add(port);
        }

        positionPorts();

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

    public int getDefaultPortOffset() {
        return 20;
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

    public boolean hasWires() {
        for(Port port : ports) {
            if (port.getWire() != null) {
                return true;
            }
        }
        return false;
    }

    public ComponentController getComponentController() {
        return componentController;
    }

    public void setComponentController(ComponentController componentController) {
        this.componentController = componentController;
    }

    public boolean hasPorts() {
        return !ports.isEmpty();
    }

    public boolean isConnected() {
        for(Port port : ports) {
            if(!port.hasWire()) return false;
        }
        return true;
    }

    public Port getPort(int portNo) {
        return ports.get(portNo);
    }

    public int getInitialRotate() {
        return initialRotate;
    }
}
