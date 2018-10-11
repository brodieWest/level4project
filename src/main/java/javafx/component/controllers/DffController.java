package javafx.component.controllers;

import javafx.component.Synchronous;

public class DffController extends ComponentController implements Synchronous{

    @Override
    public void processClockTick() {
        Synchronous synchronous = (Synchronous)componentModel;
        synchronous.processClockTick();
    }

}
