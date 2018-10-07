package model;

public class Logic {

    private boolean value;

    private boolean undefined = true;

    public Logic() {
    }

    public boolean value() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean isUndefined() {
        return undefined;
    }

    public void setUndefined(boolean undefined) {
        this.undefined = undefined;
    }

    public void copy(Logic logic) {
        value = logic.value();
        undefined = logic.isUndefined();
    }
}
