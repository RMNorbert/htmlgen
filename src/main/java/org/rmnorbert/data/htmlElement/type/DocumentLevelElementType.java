package org.rmnorbert.data.htmlElement.type;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum DocumentLevelElementType {
    HTML,
    HEAD,
    TITLE,
    BODY,
    META_CHARSET,
    BASE;

    public static Set<String> getDocumentLevelElementTypes() {
        return Arrays.stream(DocumentLevelElementType.values()).map(Enum::toString).collect(Collectors.toSet());
    }

    public static DocumentLevelElementType getByIndex(int index) {
        if (index < 0 || index >= values().length) {
            throw new IllegalArgumentException("Invalid index");
        }
        return values()[index];
    }
}
