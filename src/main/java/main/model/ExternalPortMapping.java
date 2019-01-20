package main.model;

import java.util.Objects;

public class ExternalPortMapping implements Comparable<ExternalPortMapping>{

    private String internalPort;

    private Direction direction;

    private int position;

    public ExternalPortMapping(String internalPort, Direction direction, int position) {
        this.internalPort = internalPort;
        this.direction = direction;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExternalPortMapping that = (ExternalPortMapping) o;
        return position == that.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public int compareTo(ExternalPortMapping other) {
        return other.position - this.position;
    }
}
