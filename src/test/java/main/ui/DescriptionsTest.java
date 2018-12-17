package main.ui;

import javafx.scene.Parent;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class DescriptionsTest extends ApplicationTest {

    @Test
    public void getDescription() {
        Parent andDescription = Descriptions.getDescription("and");

        assertNotNull(andDescription);
    }
}