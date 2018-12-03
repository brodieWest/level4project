package javafx.component.model.component;

import javafx.component.model.component.gates.AndGate;
import javafx.component.model.component.gates.OrGate;
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
        or = new OrGate(new ComponentParameters(new Coordinates(0,0), "testor","or",2,1));
        inputLogic0 = or.getInput(0).getLogic();
        inputLogic1 = or.getInput(1).getLogic();
        outputLogic = or.getOutput(0).getLogic();
    }


    @Test
    public void test4Parameters() {
        OrGate or4 = new OrGate(new ComponentParameters(new Coordinates(0,0), "testor","or",4,1));
        Logic or4inputLogic0 = or4.getInput(0).getLogic();
        Logic or4inputLogic1 = or4.getInput(1).getLogic();
        Logic or4inputLogic2 = or4.getInput(2).getLogic();
        Logic or4inputLogic3 = or4.getInput(3).getLogic();
        Logic or4outputLogic = or4.getOutput(0).getLogic();

        or4inputLogic0.setUndefined(false);
        or4inputLogic1.setUndefined(false);
        or4inputLogic2.setUndefined(false);
        or4inputLogic3.setUndefined(false);

        or4inputLogic0.setValue(false);
        or4inputLogic1.setValue(false);
        or4inputLogic2.setValue(false);
        or4inputLogic3.setValue(true);

        or4.processGateDelay();

        assertTrue(or4outputLogic.value());
        assertFalse(or4outputLogic.isUndefined());
    }

    @Test
    public void test4ParametersUndefined() {
        OrGate or4 = new OrGate(new ComponentParameters(new Coordinates(0,0), "testor","or",4,1));
        Logic or4inputLogic0 = or4.getInput(0).getLogic();
        Logic or4inputLogic1 = or4.getInput(1).getLogic();
        Logic or4inputLogic2 = or4.getInput(2).getLogic();
        Logic or4inputLogic3 = or4.getInput(3).getLogic();
        Logic or4outputLogic = or4.getOutput(0).getLogic();

        or4inputLogic0.setUndefined(false);
        or4inputLogic1.setUndefined(false);
        or4inputLogic2.setUndefined(false);
        or4inputLogic3.setUndefined(true);

        or4inputLogic0.setValue(false);
        or4inputLogic1.setValue(false);
        or4inputLogic2.setValue(false);
        or4inputLogic3.setValue(true);

        or4.processGateDelay();

        assertTrue(or4outputLogic.isUndefined());
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