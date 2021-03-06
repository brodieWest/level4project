package main.ui.wire;

import main.ui.component.model.component.Component;
import main.model.*;
import main.ui.port.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class WireTest {

    private Wire wire;
    private Logic inputLogic;
    private Logic outputLogic;

    @Before
    public void setup() {
        wire = new Wire(mock(WireController.class),"wire1");
        Port input = new Port(mock(Component.class), new PortParameters(Direction.SOUTH, PortType.INPUT),0);
        Port output = new Port(mock(Component.class), new PortParameters(Direction.EAST, PortType.OUTPUT),0);
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