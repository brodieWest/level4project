package main.model;

import java.util.List;

public class WireModel {

    private String uuid;

    private PortIdentifier input;

    private List<PortIdentifier> output;

    public WireModel(String uuid, PortIdentifier input, List<PortIdentifier> outputs) {
        this.uuid = uuid;
        this.input = input;
        this.output = outputs;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public PortIdentifier getInput() {
        return input;
    }

    public void setInput(PortIdentifier input) {
        this.input = input;
    }

    public List<PortIdentifier> getOutputs() {
        return output;
    }

    public void setOutputs(List<PortIdentifier> outputs) {
        this.output = outputs;
    }
}
