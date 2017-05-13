package net.divlight.retainer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes that the annotated element will be automatically preserved by Retainer.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface Retain {
}
