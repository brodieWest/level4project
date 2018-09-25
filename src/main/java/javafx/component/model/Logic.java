package javafx.component.model;

public enum Logic {
    ZERO(0), ONE(1), UNDEFINDED(2);

    int value;

    Logic(int value) {
    }

    public int getValue() {
        return value;
    }
}
