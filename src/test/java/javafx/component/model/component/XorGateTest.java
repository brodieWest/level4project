package javafx.component.model.component;

import javafx.component.model.component.gates.AndGate;
import javafx.component.model.component.gates.XorGate;
import model.Coordinates;
import model.Logic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class XorGateTest {

    private Component and;
    private Logic inputLogic0;
    private Logic inputLogic1;
    private Logic outputLogic;

    @Before
    public void setup() {
        and = new XorGate(new ComponentParameters(new Coordinates(0,0), "testand","xor",2,1));
        inputLogic0 = and.getInput(0).getLogic();
        inputLogic1 = and.getInput(1).getLogic();
        outputLogic = and.getOutput(0).getLogic();
    }

    @Test
    public void test4Parameters() {
        XorGate and4 = new XorGate(new ComponentParameters(new Coordinates(0,0), "testand","xor",4,1));
        Logic and4inputLogic0 = and4.getInput(0).getLogic();
        Logic and4inputLogic1 = and4.getInput(1).getLogic();
        Logic and4inputLogic2 = and4.getInput(2).getLogic();
        Logic and4inputLogic3 = and4.getInput(3).getLogic();
        Logic and4outputLogic = and4.getOutput(0).getLogic();

        and4inputLogic0.setUndefined(false);
        and4inputLogic1.setUndefined(false);
        and4inputLogic2.setUndefined(false);
        and4inputLogic3.setUndefined(false);

        and4inputLogic0.setValue(true);
        and4inputLogic1.setValue(true);
        and4inputLogic2.setValue(true);
        and4inputLogic3.setValue(false);

        and4.processGateDelay();

        assertTrue(and4outputLogic.value());
        assertFalse(and4outputLogic.isUndefined());
    }

    @Test
    public void test4ParametersUndefined() {
        AndGate and4 = new AndGate(new ComponentParameters(new Coordinates(0,0), "testand","and",4,1));
        Logic and4inputLogic0 = and4.getInput(0).getLogic();
        Logic and4inputLogic1 = and4.getInput(1).getLogic();
        Logic and4inputLogic2 = and4.getInput(2).getLogic();
        Logic and4inputLogic3 = and4.getInput(3).getLogic();
        Logic and4outputLogic = and4.getOutput(0).getLogic();

        and4inputLogic0.setUndefined(false);
        and4inputLogic1.setUndefined(false);
        and4inputLogic2.setUndefined(false);
        and4inputLogic3.setUndefined(true);

        and4inputLogic0.setValue(true);
        and4inputLogic1.setValue(true);
        and4inputLogic2.setValue(true);
        and4inputLogic3.setValue(false);

        and4.processGateDelay();

        assertTrue(and4outputLogic.isUndefined());
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

        assertTrue(outputLogic.value());
        assertFalse(outputLogic.isUndefined());
    }

    @Test
    public void processGateDelay01() {
        inputLogic0.setUndefined(false);
        inputLogic1.setUndefined(false);
        inputLogic0.setValue(false);
        inputLogic1.setValue(true);

        and.processGateDelay();

        assertTrue(outputLogic.value());
        assertFalse(outputLogic.isUndefined());
    }

    @Test
    public void processGateDelay11() {
        inputLogic0.setUndefined(false);
        inputLogic1.setUndefined(false);
        inputLogic0.setValue(true);
        inputLogic1.setValue(true);

        and.processGateDelay();

        assertFalse(outputLogic.value());
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