package javafx.component.model.component;

import model.Coordinates;
import model.Logic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrGateTest {

    private Component or;
    private Logic inputLogic0;
    private Logic inputLogic1;
    private Logic outputLogic;

    @Before
    public void setup() {
        ComponentFactory.initialise();
        or = ComponentFactory.getComponent("or", new Coordinates(0,0), "testor");
        inputLogic0 = or.getInput(0).getLogic();
        inputLogic1 = or.getInput(1).getLogic();
        outputLogic = or.getOutput(0).getLogic();
    }

    @Test
    public void processGateDelay00() {
        inputLogic0.setUndefined(false);
        inputLogic1.setUndefined(false);
        inputLogic0.setValue(false);
        inputLogic1.setValue(false);
        outputLogic.setUndefined(true);

        or.processGateDelay();

        assertFalse(outputLogic.value());
        assertFalse(outputLogic.isUndefined());
    }

    @Test
    public void processGateDelay10() {
        inputLogic0.setUndefined(false);
        inputLogic1.setUndefined(false);
        inputLogic0.setValue(true);
        inputLogic1.setValue(false);

        or.processGateDelay();

        assertTrue(outputLogic.value());
        assertFalse(outputLogic.isUndefined());
    }

    @Test
    public void processGateDelay01() {
        inputLogic0.setUndefined(false);
        inputLogic1.setUndefined(false);
        inputLogic0.setValue(false);
        inputLogic1.setValue(true);

        or.processGateDelay();

        assertTrue(outputLogic.value());
        assertFalse(outputLogic.isUndefined());
    }

    @Test
    public void processGateDelay11() {
        inputLogic0.setUndefined(false);
        inputLogic1.setUndefined(false);
        inputLogic0.setValue(true);
        inputLogic1.setValue(true);

        or.processGateDelay();

        assertTrue(outputLogic.value());
        assertFalse(outputLogic.isUndefined());
    }

    @Test
    public void processGateDelayUndefined0() {
        inputLogic0.setUndefined(false);
        inputLogic1.setUndefined(true);

        or.processGateDelay();

        assertTrue(outputLogic.isUndefined());
    }

    @Test
    public void processGateDelayUndefined1() {
        inputLogic0.setUndefined(true);
        inputLogic1.setUndefined(false);

        or.processGateDelay();

        assertTrue(outputLogic.isUndefined());
    }
}