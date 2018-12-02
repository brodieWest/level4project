package javafx.component.model.component;

import javafx.component.model.component.gates.AndGate;
import javafx.component.model.component.gates.NotGate;
import javafx.component.model.component.gates.OrGate;
import model.Coordinates;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComponentTest {

    @Before
    public void setup() {
    }


    @Test
    public void addNewInput() {
        Component and = new AndGate(new ComponentParameters(new Coordinates(1,1), "testand","and", 2,1));

        assertEquals(50,and.getInput(0).getOffset().getX());
        assertEquals(40,and.getInput(0).getOffset().getY());

        assertEquals(50,and.getInput(1).getOffset().getX());
        assertEquals(60,and.getInput(1).getOffset().getY());
    }

    @Test
    public void addNewOutput() {
        Component not = new NotGate(new ComponentParameters(new Coordinates(1,1), "testnot","not", 2,1));

        assertEquals(50,not.getOutput(0).getOffset().getX());
        assertEquals(50,not.getOutput(0).getOffset().getY());

    }
}