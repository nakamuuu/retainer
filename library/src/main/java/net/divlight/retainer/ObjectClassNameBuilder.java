package net.divlight.retainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectClassNameBuilder {
    private static final String RETAINER_CLASS_SUFFIX = "_Retainer";

    private final String targetClassSimpleName;
    private final List<String> enclosingClassSimpleNames = new ArrayList<>();

    public ObjectClassNameBuilder(String targetClassSimpleName) {
        this.targetClassSimpleName = targetClassSimpleName;
    }

    public ObjectClassNameBuilder addEnclosingClassSimpleName(String enclosingClassSimpleName) {
        enclosingClassSimpleNames.add(enclosingClassSimpleName);
        return this;
    }

    public String build() {
        final StringBuilder nameBuilder = new StringBuilder();
        Collections.reverse(enclosingClassSimpleNames);
        for (String enclosingClassSimpleName : enclosingClassSimpleNames) {
            nameBuilder.append(enclosingClassSimpleName).append("$");
        }
        return nameBuilder + targetClassSimpleName + RETAINER_CLASS_SUFFIX;
    }
}
