package main.ui.simulation;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import main.fxml.FxmlLoaderUtils;
import main.ui.Controller;
import main.ui.main.Mainfx;

class BackgroundController implements Controller {

    @FXML
    private VBox vBox;

    @FXML
    private Group background;

    private static String BACKGROUND_FXML_PATH = "fxml/background.fxml";
    private static String BACKGROUND_LINE_COLOUR = "lightgray";


    private static int SCREEN_SIZE = 10000;
    private static int BACKGROUND_BOX_SIZE = 100;

    BackgroundController() {
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(BACKGROUND_FXML_PATH),this);

        for (int i = 0; i <= SCREEN_SIZE; i += BACKGROUND_BOX_SIZE) {
            Line line1 = new Line(0, i, SCREEN_SIZE, i);
            line1.setStroke(Paint.valueOf(BACKGROUND_LINE_COLOUR));

            Line line2 = new Line(i, 0, i, SCREEN_SIZE);
            line2.setStroke(Paint.valueOf(BACKGROUND_LINE_COLOUR));
            background.getChildren().addAll(line1, line2);
        }
    }


    VBox getvBox() {
        return vBox;
    }
}
