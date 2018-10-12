package javafx.component.model.component;

import model.Coordinates;
import model.Logic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotGateTest {
    private Component not;

    private Logic notInputLogic;
    private Logic notOutputLogic;

    @Before
    public void setup() {
        ComponentFactory.initialise();
        not =  ComponentFactory.getComponent("not", new Coordinates(0,0), "testnot");
        notInputLogic = not.getInput(0).getLogic();
        notOutputLogic = not.getOutput(0).getLogic();
    }

    @Test
    public void test0() {
        notInputLogic.setValue(false);
        notInputLogic.setUndefined(false);

        not.processGateDelay();

        assertTrue(notOutputLogic.value());
        assertFalse(notOutputLogic.isUndefined());
    }

    @Test
    public void test1() {
        notInputLogic.setValue(true);
        notInputLogic.setUndefined(false);

        not.processGateDelay();

        assertFalse(notOutputLogic.value());
        assertFalse(notOutputLogic.isUndefined());
    }

    @Test
    public void testUndefined() {
        notInputLogic.setValue(false);
        notInputLogic.setUndefined(true);

        not.processGateDelay();

        assertTrue(notOutputLogic.isUndefined());
    }

    @Test
    public void testUndefinedWithNoValue() {
        notInputLogic.setUndefined(true);

        not.processGateDelay();

        assertTrue(notOutputLogic.isUndefined());
    }
}