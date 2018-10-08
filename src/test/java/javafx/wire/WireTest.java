package javafx.wire;

import javafx.component.model.component.ComponentFactory;
import model.Logic;
import model.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WireTest {

    private Wire wire;
    private Logic inputLogic;
    private Logic outputLogic;

    @Before
    public void setup() {
        ComponentFactory.initialise();
        wire = new Wire();
        Port input = new Port();
        Port output = new Port();
        wire.setInput(input);
        wire.addOutput(output);
        inputLogic = input.getLogic();
        outputLogic = output.getLogic();
    }

    @Test
    public void passSignal0() {
        outputLogic.setValue(true);
        inputLogic.setValue(false);
        inputLogic.setUndefined(false);

        wire.passSignal();

        assertFalse(outputLogic.value());
        assertFalse(outputLogic.isUndefined());
    }

    @Test
    public void passSignal1() {
        inputLogic.setValue(true);
        inputLogic.setUndefined(false);

        wire.passSignal();

        assertTrue(outputLogic.value());
        assertFalse(outputLogic.isUndefined());
    }

    @Test
    public void passSignalUndefined() {
        inputLogic.setValue(true);
        inputLogic.setUndefined(true);

        wire.passSignal();

        assertTrue(outputLogic.isUndefined());
    }
}