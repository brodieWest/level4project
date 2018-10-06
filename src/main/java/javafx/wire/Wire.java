package javafx.wire;

import javafx.Onscreen;
import model.Coordinates;
import model.Logic;
import model.Port;

import java.util.List;
import java.util.Map;

public class Wire implements Onscreen {

    private Logic logic;

    private Port input;

    private Coordinates coordinates;

    private List<Port> output;

    private String uuid;

    private static int uuidGenerator = 0;

    public Wire() {
        uuid = "wire" + uuidGenerator;
        uuidGenerator++;
    }

    public Logic getLogic() {
        return logic;
    }

    public void PassSignal() {
        // TODO: ouputs == input
    }

    @Override
    public Coordinates getCoordinates() {
        return null;
    }

    @Override
    public String getUuid() {
        return uuid;
    }
}
