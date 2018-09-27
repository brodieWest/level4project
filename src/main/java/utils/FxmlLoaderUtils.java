package utils;

import javafx.Controller;
import javafx.component.ComponentController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.simulation.model.Simulator;

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
            return null;
        }

        Controller controller = fxmlLoader.getController();

        return new Fxml(controller, componentNode);
    }
}
