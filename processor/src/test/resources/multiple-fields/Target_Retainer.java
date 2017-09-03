package net.divlight.retainer.processor;

import java.lang.Override;
import net.divlight.retainer.Object;

public class Target_Retainer<T extends Target> implements Object<T> {
    private int retainedValue;
    private Object retainedObject;

    @Override
    public void save(T target) {
        this.retainedValue = target.retainedValue;
        this.retainedObject = target.retainedObject;
    }

    @Override
    public void restore(T target) {
        target.retainedValue = this.retainedValue;
        target.retainedObject = this.retainedObject;
    }
}
