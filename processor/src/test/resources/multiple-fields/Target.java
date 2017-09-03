package net.divlight.retainer.processor;

import net.divlight.retainer.Object;
import net.divlight.retainer.annotation.Retain;

public class Target {
    @Retain
    int retainedValue;
    @Retain
    Object retainedObject;
}
