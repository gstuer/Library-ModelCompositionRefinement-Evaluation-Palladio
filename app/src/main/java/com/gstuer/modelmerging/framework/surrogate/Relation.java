package com.gstuer.modelmerging.framework.surrogate;

import java.util.Objects;

public class Relation<T extends Replaceable, S extends Replaceable> extends Replaceable {
    private final T source;
    private final S destination;

    protected Relation(T source, S destination, boolean isPlaceholder) {
        super(isPlaceholder);
        this.source = Objects.requireNonNull(source);
        this.destination = Objects.requireNonNull(destination);
    }

    public T getSource() {
        return source;
    }

    public S getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Relation<?, ?> relation = (Relation<?, ?>) object;
        return source.equals(relation.source) && destination.equals(relation.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }
}