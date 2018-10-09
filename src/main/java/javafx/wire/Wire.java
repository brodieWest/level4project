package javafx.wire;

import javafx.Onscreen;
import model.Coordinates;
import model.Logic;
import model.Port;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Wire implements Onscreen {


    private Port input;

    private Coordinates coordinates;

    private List<Port> outputs = new ArrayList<>();

    private String uuid;

    private static int uuidGenerator = 0;

    public Wire() {
        uuid = "wire" + uuidGenerator;
        uuidGenerator++;
    }

    public void passSignal() {
        for(Port output : outputs) {
            output.getLogic().copy(input.getLogic());
        }
    }

    public static void reset() {
        uuidGenerator = 0;
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

    public void addOutput(Port output) {
        this.outputs.add(output);
    }
}
