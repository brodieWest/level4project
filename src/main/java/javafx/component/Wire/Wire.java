package javafx.component.Wire;

import javafx.Onscreen;
import javafx.component.model.Coordinates;
import javafx.component.model.Logic;
import javafx.component.model.Port;

import java.util.Map;

public class Wire implements Onscreen {

    private Logic logic;

    private Port input;

    private Coordinates coordinates;

    private Map<String,Port> output;

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
