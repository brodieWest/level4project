package main.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ComponentInternalWindow {

    private static Stage reusableInternalStage = new Stage();

    public static void newWindow(Scene scene, String title) {
        reusableInternalStage.setTitle(title);

        reusableInternalStage.setScene(scene);
        reusableInternalStage.show();
    }

}
