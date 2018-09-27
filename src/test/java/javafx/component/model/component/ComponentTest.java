package javafx.component.model.component;

import javafx.component.model.Coordinate;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComponentTest {

    @Test
    public void getUuid() {
        AndGate andGate = new AndGate(new Coordinate(1,1));

        assertEquals("and1", andGate.getUuid());

        AndGate andGate2 = new AndGate(new Coordinate(1,1));

        assertEquals("and2", andGate2.getUuid());

        OrGate orGate = new OrGate(new Coordinate(1,1));

        assertEquals("or1", orGate.getUuid());
    }
}