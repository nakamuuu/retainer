package net.divlight.retainer;

import org.junit.Test;
import static org.junit.Assert.*;

public class ObjectClassNameBuilderTest {
    @Test
    public void testBuild() {
        final String objectClassName = new ObjectClassNameBuilder("TargetClass").build();
        assertEquals("TargetClass_Retainer", objectClassName);
    }

    @Test
    public void testBuildWithInnerClass() {
        final String objectClassName = new ObjectClassNameBuilder("TargetClass")
                .addEnclosingClassSimpleName("OuterClass")
                .build();
        assertEquals("OuterClass$TargetClass_Retainer", objectClassName);

        final String objectClassName2 = new ObjectClassNameBuilder("TargetClass")
                .addEnclosingClassSimpleName("OuterClass")
                .addEnclosingClassSimpleName("OuterClass2")
                .build();
        assertEquals("OuterClass2$OuterClass$TargetClass_Retainer", objectClassName2);
    }
}
