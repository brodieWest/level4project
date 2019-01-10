package main.ui.component;

import main.ui.wire.Wire;

public interface OutputControllerInterface {

    void showOutputValue();

    String getUuid();

    Wire getWire();
}
