package javafx.component.controllers;

import javafx.component.Synchronous;

public class SynchronousController extends ComponentController{
    public void processClockTick() {
        Synchronous synchronous = (Synchronous)componentModel;
        synchronous.processClockTick();
    }

}
