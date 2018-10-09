package javafx.component.controllers;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

class IoController extends ComponentController{

    static String LOGIC_0 = "0";
    static String LOGIC_1 = "1";
    static String LOGIC_UNDEFINED = "U";
    static String LOGIC_0_COLOUR = "white";
    static String LOGIC_1_COLOUR = "yellow";
    static String LOGIC_UNDEFINED_COLOUR = "lightgrey";

    void ioShowValue(String textString, String colour) {
        Shape shape = (Shape)svgGroup.getChildren().get(0);
        text.setText(textString);
        shape.setFill(Paint.valueOf(colour));
    }
}
