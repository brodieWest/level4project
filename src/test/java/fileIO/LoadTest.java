package fileIO;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoadTest {

    @Test
    public void loadFromFile() {
        String fileText = Load.loadTextFromFile("/exampleText");
        assertEquals("test", fileText);
    }
}