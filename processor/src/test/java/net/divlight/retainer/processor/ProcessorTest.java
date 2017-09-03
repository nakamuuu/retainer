package net.divlight.retainer.processor;

import com.google.common.io.Resources;
import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;

import org.junit.Test;

public class ProcessorTest {
    @Test
    public void testSimpleCase() {
        Truth.assert_().about(JavaSourceSubjectFactory.javaSource())
                .that(JavaFileObjects.forResource(Resources.getResource("simple/Target.java")))
                .processedWith(new RetainProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(Resources.getResource("simple/Target_Retainer.java")));
    }

    @Test
    public void testNestedCase() {
        Truth.assert_().about(JavaSourceSubjectFactory.javaSource())
                .that(JavaFileObjects.forResource(Resources.getResource("nested/Target.java")))
                .processedWith(new RetainProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(Resources.getResource("nested/Target$Inner1_Retainer.java")),
                        JavaFileObjects.forResource(Resources.getResource("nested/Target$Inner1$Inner2_Retainer.java")));
    }

    @Test
    public void testMultipleFieldsCase() {
        Truth.assert_().about(JavaSourceSubjectFactory.javaSource())
                .that(JavaFileObjects.forResource(Resources.getResource("multiple-fields/Target.java")))
                .processedWith(new RetainProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(JavaFileObjects.forResource(Resources.getResource("multiple-fields/Target_Retainer.java")));
    }
}
