package main.ui.wire;

import main.model.PortIdentifier;
import main.model.WireModel;
import main.ui.Onscreen;
import main.model.Coordinates;
import main.ui.port.Port;

import java.util.ArrayList;
import java.util.List;

public class Wire implements Onscreen {


    private Port input;

    private Coordinates coordinates;

    private List<Port> outputs = new ArrayList<>();

    private String uuid;

    public Wire(String uuid) {
        this.uuid = uuid;
    }

    void passSignal() {
        for(Port output : outputs) {
            output.getWord().copy(input.getWord());
        }
    }

    @Override
    public Coordinates getCoordinates() {
        return null;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public Port getInput() {
        return input;
    }

    public void setInput(Port input) {
        this.input = input;
    }

    public Port getOutput(int portNo) {
        return outputs.get(portNo);
    }

    public List<Port> getOutputs() {
        return outputs;
    }

    public void addOutput(Port output) {
        this.outputs.add(output);
    }

    public WireModel getWireModel() {
        List<PortIdentifier> portIdentifiers = new ArrayList<>();
        for(Port output : outputs) {
            portIdentifiers.add(output.getPortIdentifier());
        }
        return new WireModel(uuid, input.getPortIdentifier(),portIdentifiers);
    }
}
