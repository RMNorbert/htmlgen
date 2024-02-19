package org.rmnorbert.service.verify;

import org.rmnorbert.data.htmlElement.HtmlElement;
import org.rmnorbert.data.htmlElement.type.DocumentLevelElementType;

import java.util.List;
import java.util.Set;

import static org.rmnorbert.data.htmlElement.type.DocumentLevelElementType.getDocumentLevelElementTypes;
import static org.rmnorbert.data.htmlElement.type.ElementType.getElementTypes;

public class HtmlTypeVerifierService {
    private final static Set<String> elementTypes = getElementTypes();
    private final static Set<String> documentLevelElementTypes = getDocumentLevelElementTypes();

    public static boolean verifyElementType(String type) {
        return elementTypes.contains(type);
    }

    public static boolean verifyDocumentLevelElementType(String type) {
        return documentLevelElementTypes.contains(type);
    }

    public static boolean isDocumentLevelElementPresent(DocumentLevelElementType elementType,
                                                        List<HtmlElement> htmlHtmlElementList,
                                                        String typeToCheck) {
        return elementType.toString().equals(typeToCheck) ||
                htmlHtmlElementList.stream()
                        .anyMatch(htmlElement -> htmlElement.getTypeAsString().equals(typeToCheck));
    }
}
