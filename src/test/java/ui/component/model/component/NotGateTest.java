package ui.component.model.component;

import ui.component.model.component.gates.NotGate;
import model.Coordinates;
import model.Logic;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NotGateTest {
    private Component not;

    private Logic notInputLogic;
    private Logic notOutputLogic;

    @Before
    public void setup() {
        not =  new NotGate(new ComponentParameters(new Coordinates(0,0), "testnot","not", new ArrayList<>()));
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