package javafx.component.model.component;

import javafx.component.model.component.gates.AndGate;
import javafx.component.model.component.gates.OrGate;
import model.Coordinates;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComponentTest {

    @Before
    public void setup() {
        ComponentFactory.initialise();
    }

    @Test
    public void getUuid() {
        AndGate andGate = (AndGate)ComponentFactory.getComponent("and", new Coordinates(1,1));

        assertEquals("and1", andGate.getUuid());

        AndGate andGate2 = (AndGate)ComponentFactory.getComponent("and", new Coordinates(1,1));

        assertEquals("and2", andGate2.getUuid());

        OrGate orGate = (OrGate)ComponentFactory.getComponent("or", new Coordinates(1,1));

        assertEquals("or1", orGate.getUuid());
    }

    @Test
    public void addNewInput() {
        Component and = ComponentFactory.getComponent("and", new Coordinates(1,1));

        assertEquals(20,and.getInput(0).getOffset().getX());
        assertEquals(40,and.getInput(0).getOffset().getY());

        assertEquals(20,and.getInput(1).getOffset().getX());
        assertEquals(60,and.getInput(1).getOffset().getY());
    }

    @Test
    public void addNewOutput() {
        Component not = ComponentFactory.getComponent("not", new Coordinates(1,1));

        assertEquals(80,not.getOutput(0).getOffset().getX());
        assertEquals(50,not.getOutput(0).getOffset().getY());

    }
}