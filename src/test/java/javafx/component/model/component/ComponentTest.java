package javafx.component.model.component;

import model.Coordinates;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComponentTest {

    @Test
    public void getUuid() {
        AndGate andGate = new AndGate(new Coordinates(1,1));

        assertEquals("and1", andGate.getUuid());

        AndGate andGate2 = new AndGate(new Coordinates(1,1));

        assertEquals("and2", andGate2.getUuid());

        OrGate orGate = new OrGate(new Coordinates(1,1));

        assertEquals("or1", orGate.getUuid());
    }

    @Test
    public void addNewInput() {
        Component and = new AndGate(new Coordinates(1,1));

        assertEquals(30,and.getInput("input1").getOffset().getX());
        assertEquals(10,and.getInput("input1").getOffset().getY());

        assertEquals(30,and.getInput("input2").getOffset().getX());
        assertEquals(20,and.getInput("input2").getOffset().getY());
    }

    @Test
    public void addNewOutput() {
        Component not = new NotGate(new Coordinates(1,1));

        assertEquals(70,not.getOutput("output1").getOffset().getX());
        assertEquals(10,not.getOutput("output1").getOffset().getY());

    }
}