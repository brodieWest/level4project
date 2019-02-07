package main.utils;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TutorialUtils {

    public static void highlightText(MenuItem menu) {
        StackPane group = new StackPane();
        Text text = new Text();
        text.setText(menu.getText());

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(20);
        rectangle.setWidth(35);
        //rectangle.setStroke(Paint.valueOf("yellow"));
        rectangle.setFill(Paint.valueOf("yellow"));

        group.getChildren().add(rectangle);
        group.getChildren().add(text);

        menu.setText("");
        menu.setGraphic(group);
    }

    public static void unhighlightText(MenuItem menu) {
        menu.setText("File");
        menu.setGraphic(null);
    }
}
