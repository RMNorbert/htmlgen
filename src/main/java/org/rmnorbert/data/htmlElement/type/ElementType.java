package org.rmnorbert.data.htmlElement.type;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum ElementType {
    DIV,
    FORM,
    INPUT,
    BUTTON,
    UL,
    OL,
    LI,
    H1,
    H2,
    H3,
    H4,
    H5,
    H6,
    P,
    TABLE,
    THEAD,
    TBODY,
    TFOOT,
    TR,
    TH,
    TD,
    A;

    public static Set<String> getElementTypes() {
        return Arrays.stream(ElementType.values()).map(Enum::toString).collect(Collectors.toSet());
    }

    public static ElementType getByIndex(int index) {
        if (index < 0 || index >= values().length) {
            throw new IllegalArgumentException("Invalid index");
        }
        return values()[index];
    }
}
