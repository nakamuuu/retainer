package net.divlight.retainer.processor;

import java.lang.Override;
import net.divlight.retainer.Object;

public class Target$Inner1$Inner2_Retainer<T extends Target.Inner1.Inner2> implements Object<T> {
    private Object retainedObject2;

    @Override
    public void save(T target) {
        this.retainedObject2 = target.retainedObject2;
    }

    @Override
    public void restore(T target) {
        target.retainedObject2 = this.retainedObject2;
    }
}
