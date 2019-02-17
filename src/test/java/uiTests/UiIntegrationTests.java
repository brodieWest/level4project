package uiTests;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UiIntegrationTests extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/ui/main/fxml/logicsimMain.fxml"));
        primaryStage.setTitle("Hello World");
        Scene mainScene = new Scene(root, 800, 500);
        mainScene.getStylesheets().add(getClass().getResource("/main/ui/main/css/css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    @Test
    public void orGate() {
        clickOn("#file").clickOn("#load").push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.ENTER);

        clickOn("#startSimulation");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"U");

        clickOn("#gateDelay");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"0");

        clickOn("#input");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"U");

        clickOn("#gateDelay");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"1");


    }

    @Test
    public void dff() {
        clickOn("#file").clickOn("#load").push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.ENTER);

        clickOn("#startSimulation");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"0");

        clickOn("#gateDelay");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"0");

        clickOn("#input");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"0");

        clickOn("#gateDelay");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"0");

        clickOn("#clockTick");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"1");

        clickOn("#clockTick");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"1");

        clickOn("#gateDelay");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"1");

    }

}
