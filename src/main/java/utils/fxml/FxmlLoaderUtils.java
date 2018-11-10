package utils.fxml;

import javafx.Controller;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class FxmlLoaderUtils {

    public static Fxml loadFxml(String fileName) {

        FXMLLoader fxmlLoader = new FXMLLoader(FxmlLoaderUtils.class.getClassLoader().getResource(fileName));

        Parent componentNode;

        try {
            componentNode = fxmlLoader.load();
        } catch (IOException e) {
            // TODO better error handling
            e.printStackTrace();
            componentNode = new VBox();
            Platform.exit();
        }

        Controller controller = fxmlLoader.getController();

        return new Fxml(controller, componentNode);
    }

    public static void loadFxml(String fileName, Controller controller) {

        FXMLLoader fxmlLoader = new FXMLLoader(FxmlLoaderUtils.class.getClassLoader().getResource(fileName));

        fxmlLoader.setController(controller);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            // TODO better error handling
            e.printStackTrace();
            Platform.exit();
        }
    }
}
