package main.ui.utils;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

public class AlertUtils {

    public static void information(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        alert.showAndWait();

    }
}
