package net.divlight.retainer.processor;

import java.lang.Override;
import net.divlight.retainer.Object;

public class Target$Inner1_Retainer<T extends Target.Inner1> implements Object<T> {
    private Object retainedObject1;

    @Override
    public void save(T target) {
        this.retainedObject1 = target.retainedObject1;
    }

    @Override
    public void restore(T target) {
        target.retainedObject1 = this.retainedObject1;
    }
}
