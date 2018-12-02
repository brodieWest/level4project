package javafx.component.model.component;

import javafx.component.model.component.gates.AndGate;
import model.Coordinates;
import model.Logic;
import model.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AndGateTest {

    private Component and;
    private Logic inputLogic0;
    private Logic inputLogic1;
    private Logic outputLogic;

    @Before
    public void setup() {
        and = new AndGate(new ComponentParameters(new Coordinates(0,0), "testand","and",2,1));
        inputLogic0 = and.getInput(0).getLogic();
        inputLogic1 = and.getInput(1).getLogic();
        outputLogic = and.getOutput(0).getLogic();
    }

    @Test
    public void processGateDelay00() {
        inputLogic0.setUndefined(false);
        inputLogic1.setUndefined(false);
        inputLogic0.setValue(false);
        inputLogic1.setValue(false);
        outputLogic.setUndefined(true);

        and.processGateDelay();

        assertFalse(outputLogic.value());
        assertFalse(outputLogic.isUndefined());
    }

    @Test
    public void processGateDelay10() {
        inputLogic0.setUndefined(false);
        inputLogic1.setUndefined(false);
        inputLogic0.setValue(true);
        inputLogic1.setValue(false);

        and.processGateDelay();

        assertFalse(outputLogic.value());
        assertFalse(outputLogic.isUndefined());
    }

    @Test
    public void processGateDelay01() {
        inputLogic0.setUndefined(false);
        inputLogic1.setUndefined(false);
        inputLogic0.setValue(false);
        inputLogic1.setValue(true);

        and.processGateDelay();

        assertFalse(outputLogic.value());
        assertFalse(outputLogic.isUndefined());
    }

    @Test
    public void processGateDelay11() {
        inputLogic0.setUndefined(false);
        inputLogic1.setUndefined(false);
        inputLogic0.setValue(true);
        inputLogic1.setValue(true);

        and.processGateDelay();

        assertTrue(outputLogic.value());
        assertFalse(outputLogic.isUndefined());
    }

    @Test
    public void processGateDelayUndefined0() {
        inputLogic0.setUndefined(false);
        inputLogic1.setUndefined(true);

        and.processGateDelay();

        assertTrue(outputLogic.isUndefined());
    }

    @Test
    public void processGateDelayUndefined1() {
        inputLogic0.setUndefined(true);
        inputLogic1.setUndefined(false);

        and.processGateDelay();

        assertTrue(outputLogic.isUndefined());
    }
}