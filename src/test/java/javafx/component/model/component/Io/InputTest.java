package javafx.component.model.component.Io;

import javafx.component.model.component.ComponentParameters;
import model.Coordinates;
import org.junit.Test;

import static org.junit.Assert.*;

public class InputTest {

    @Test
    public void reset() {
        Input input = new Input( new ComponentParameters(new Coordinates(0,0),"input1","input",0,1));
        input.getOutput(0).getLogic().setUndefined(true);
        input.reset();

        assertFalse(input.getOutput(0).getLogic().isUndefined());
    }
}