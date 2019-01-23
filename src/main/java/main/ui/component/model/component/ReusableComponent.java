package main.ui.component.model.component;

import main.model.ComponentParametersModel;
import main.model.Direction;
import main.model.PortParameters;
import main.model.PortType;
import main.ui.wire.Wire;
import main.ui.port.Port;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReusableComponent extends Component{

    private List<Wire> internalInputs;

    private List<Wire> internalOutputs;

    private int defaultInputs = 0;

    private int defaultOutputs = 0;

    private boolean portsAdded = false;


    public ReusableComponent(ComponentParameters componentParameters) {
        super(componentParameters);
    }

    public void addExternalPort(Component internalComponent, Direction direction) {
        portsAdded = true;
        Port port = internalComponent.getPort(0);
        Wire wire = port.getWire();
        List<PortParameters> portParameters = new ArrayList<>();
        PortType portType;
        if (port.getPortType() == PortType.INPUT) {
            portType = PortType.OUTPUT;
        } else {
            portType = PortType.INPUT;
        }
        portParameters.add(new PortParameters(direction, portType, port.getSize()));
        addPorts(portParameters);
        Port externalPort = ports.get(ports.size() - 1);

        if (portType == PortType.INPUT) {
            wire.setInput(externalPort);
        } else {
            wire.addOutput(externalPort);
        }
    }

    @Override
    public void processGateDelay() {
    }

    @Override
    public int getDefaultInputs() {
        return defaultInputs;
    }

    @Override
    public int getDefaultOutputs() {
        return defaultOutputs;
    }

    public List<Wire> getInternalWires() {
        return internalInputs;
    }

    public boolean isPortsAdded() {
        return portsAdded;
    }

    @Override
    public ComponentParametersModel getComponentParameters() {
        return new ComponentParametersModel(coordinates,uuid,type,componentController.getRotation(),new ArrayList<>(),new ArrayList<>());
    }
}
