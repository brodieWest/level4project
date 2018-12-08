package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class WordTest {

    @Test
    public void setValue() {
        Word word = new Word(16);

        word.setValue(5);

        assertTrue(word.get(0).value());
        assertFalse(word.get(1).value());
        assertTrue(word.get(2).value());
        assertFalse(word.get(3).value());
    }
}