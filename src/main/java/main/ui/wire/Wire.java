package main.ui.wire;

import main.model.PortIdentifier;
import main.model.WireModel;
import main.ui.Onscreen;
import main.model.Coordinates;
import main.ui.port.Port;

import java.util.ArrayList;
import java.util.List;

public class Wire {
    private WireController wireController;

    private Port input;

    private List<Port> outputs = new ArrayList<>();

    private String uuid;

    public Wire(WireController wireController,String uuid) {
        this.uuid = uuid;
        this.wireController = wireController;
    }

    void passSignal() {
        for(Port output : outputs) {
            output.getWord().copy(input.getWord());
        }
    }

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

    public WireController getWireController() {
        return wireController;
    }

    public void resetPorts() {
        input.reset();
        for(Port output : outputs) {
            output.reset();
        }
    }

}
