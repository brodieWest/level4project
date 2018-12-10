package ui.wire;

import ui.Onscreen;
import model.Coordinates;
import model.Port;

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
}
