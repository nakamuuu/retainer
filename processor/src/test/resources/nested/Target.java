package net.divlight.retainer.processor;

import net.divlight.retainer.Object;
import net.divlight.retainer.annotation.Retain;

public class Target {
    public class Inner1 {
        @Retain
        Object retainedObject1;

        public class Inner2 {
            @Retain
            Object retainedObject2;
        }
    }
}
