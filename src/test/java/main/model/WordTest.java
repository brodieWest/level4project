package main.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class WordTest {

    @Test
    public void setValue() {
        Word word = new Word(16);

        word.setValue(5);

        assertFalse(word.get(0).value());
        assertFalse(word.get(1).value());
        assertFalse(word.get(2).value());
        assertFalse(word.get(3).value());
        assertFalse(word.get(4).value());
        assertFalse(word.get(5).value());
        assertFalse(word.get(6).value());
        assertFalse(word.get(7).value());
        assertFalse(word.get(8).value());
        assertFalse(word.get(9).value());
        assertFalse(word.get(10).value());
        assertFalse(word.get(11).value());
        assertFalse(word.get(12).value());
        assertTrue(word.get(13).value());
        assertFalse(word.get(14).value());
        assertTrue(word.get(15).value());
    }

    @Test
    public void toStringTest() {
        Word word = new Word(16);

        word.setValue(5);

        assertEquals("5", word.toString());
    }

    @Test
    public void toStringHex() {
        Word word = new Word(16);

        word.setValue(10);

        assertEquals("a", word.toString());
    }
}