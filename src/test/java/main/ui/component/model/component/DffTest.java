package main.ui.component.model.component;

import main.model.Coordinates;
import main.model.Logic;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DffTest {
    private Dff dff;

    private Logic inputLogic;
    private Logic outputLogic;

    @Before
    public void setup() {
        dff = new Dff(new ComponentParameters(new Coordinates(0,0), "test","dff", new ArrayList<>()));
        inputLogic = dff.getInput(0).getLogic();
        outputLogic = dff.getOutput(0).getLogic();
    }

    @Test
    public void checkInitailly0() {
        inputLogic.setValue(true);

        //assertTrue(outputLogic.isUndefined());

        //dff.processGateDelay();

        dff.wireDelay();

        assertFalse(outputLogic.isUndefined());
        assertFalse(outputLogic.value());

        dff.processGateDelay();

        assertFalse(outputLogic.isUndefined());
        assertFalse(outputLogic.value());

        dff.processGateDelay();

        assertFalse(outputLogic.isUndefined());
        assertFalse(outputLogic.value());
    }

    @Test
    public void processClockTick() {
        inputLogic.setUndefined(false);
        inputLogic.setValue(true);

        outputLogic.setUndefined(true);
        outputLogic.setValue(true);

        dff.processClockTick();

        //assertTrue(outputLogic.isUndefined());

        //dff.processGateDelay();

        dff.wireDelay();

        assertFalse(outputLogic.isUndefined());
        assertTrue(outputLogic.value());
    }
}