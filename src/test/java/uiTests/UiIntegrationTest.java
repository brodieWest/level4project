package uiTests;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.fileIO.Load;
import main.ui.main.MainController;
import main.ui.simulation.MainSimulationController;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.EmptyNodeQueryException;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;

import static org.junit.Assert.*;


public class UiIntegrationTest extends ApplicationTest {

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
    public void buildCircuit() {
        clickOn("Input");

        clickOn("Output");

        moveTo("#output").drag("#output").dropBy(200,0);

        clickOn((Node)lookup("#input").lookup("#buildIcon").query());

        clickOn((Node)lookup("#output").lookup("#buildIcon").query());

        clickOn("#startSimulation");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"0");

        clickOn("#input");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"1");
    }

    @Test (expected = EmptyNodeQueryException.class)
    public void deleteWires() {
        clickOn("Input");

        clickOn("Output");

        moveTo("#output").drag("#output").dropBy(200,0);

        clickOn((Node)lookup("#input").lookup("#buildIcon").query());

        clickOn((Node)lookup("#output").lookup("#buildIcon").query());

        clickOn("Delete Wires");

        clickOn((Node)lookup("#wireGroup").lookup("#wire").query());

        lookup("#wireGroup").lookup("#wire").query();
    }

    @Test(expected = EmptyNodeQueryException.class)
    public void deleteComponent() {
        clickOn("Input");

        clickOn("Delete Components");

        clickOn("#input");

        lookup("#input").query();
    }

    @Test
    public void orGate() {
        //clickOn("#file").clickOn("#load");

        String file = Load.loadTextFromFile("fileExamplesForTesting/or");

        interact(() -> Load.load(MainSimulationController.getForTesting(), file));

        //push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.DOWN).push(KeyCode.ENTER);

        clickOn("#startSimulation");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"U");

        clickOn("#gateDelay");
        moveTo("#file");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"0");

        clickOn("#input");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"U");

        clickOn("#gateDelay");

        assertEquals(lookup("#outputText").queryAs(Text.class).getText(),"1");

    }

    @Test
    public void add4() {

        String file = Load.loadTextFromFile("fileExamplesForTesting/add4");

        interact(() -> Load.load(MainSimulationController.getForTesting(), file));

        clickOn("#startSimulation");

        clickOn("#wordInput").type(KeyCode.DIGIT7);

        assertEquals("U",lookup("#wordOutputText").queryAs(Text.class).getText());

        clickOn("#gateDelay");

        assertEquals("U",lookup("#wordOutputText").queryAs(Text.class).getText());

        clickOn("#gateDelay");
        clickOn("#gateDelay");
        clickOn("#gateDelay");
        clickOn("#gateDelay");
        clickOn("#gateDelay");
        clickOn("#gateDelay");

        assertEquals("U",lookup("#wordOutputText").queryAs(Text.class).getText());

        clickOn("#gateDelay");

        assertEquals("7",lookup("#wordOutputText").queryAs(Text.class).getText());
    }

    @Test
    public void dff() {
        String file = Load.loadTextFromFile("fileExamplesForTesting/dff");

        interact(() -> Load.load(MainSimulationController.getForTesting(), file));

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