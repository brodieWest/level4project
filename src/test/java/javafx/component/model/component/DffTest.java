package javafx.component.model.component;

import model.Coordinates;
import model.Logic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DffTest {
    private Dff dff;

    private Logic inputLogic;
    private Logic outputLogic;

    @Before
    public void setup() {
        dff = new Dff(new ComponentParameters(new Coordinates(0,0), "test","dff", 1,1));
        inputLogic = dff.getInput(0).getLogic();
        outputLogic = dff.getOutput(0).getLogic();
    }

    @Test
    public void checkInitailly0() {
        inputLogic.setValue(true);

        assertTrue(outputLogic.isUndefined());

        dff.processGateDelay();

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

        assertTrue(outputLogic.isUndefined());

        dff.processGateDelay();

        assertFalse(outputLogic.isUndefined());
        assertTrue(outputLogic.value());
    }
}