package org.rmnorbert.service.verify;

import org.junit.jupiter.api.Test;
import org.rmnorbert.data.htmlElement.DocumentLevelHtmlElement;
import org.rmnorbert.data.htmlElement.HtmlElement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.rmnorbert.data.htmlElement.type.DocumentLevelElementType.BODY;
import static org.rmnorbert.data.htmlElement.type.DocumentLevelElementType.HTML;

class HtmlTypeVerifierServiceTest {

    @Test
    void verifyElementType_ShouldReturnTrueForValidType() {
        assertTrue(HtmlTypeVerifierService.verifyElementType("DIV"));
    }

    @Test
    void verifyElementType_ShouldReturnFalseForInvalidType() {
        assertFalse(HtmlTypeVerifierService.verifyElementType("INVALID"));
    }

    @Test
    void verifyDocumentLevelElementType_ShouldReturnTrueForValidType() {
        assertTrue(HtmlTypeVerifierService.verifyDocumentLevelElementType("HTML"));
    }

    @Test
    void verifyDocumentLevelElementType_ShouldReturnFalseForInvalidType() {
        assertFalse(HtmlTypeVerifierService.verifyDocumentLevelElementType("INVALID"));
    }

    @Test
    void isDocumentLevelElementPresent_ShouldReturnTrueForMatchingTypeInList() {
        List<HtmlElement> htmlElements = new ArrayList<>();
        htmlElements.add(DocumentLevelHtmlElement.builder().type(HTML).build());

        assertTrue(HtmlTypeVerifierService.isDocumentLevelElementPresent(BODY, htmlElements, "HTML"));
    }

    @Test
    void isDocumentLevelElementPresent_ShouldReturnFalseForNoMatchingElement() {
        assertFalse(HtmlTypeVerifierService.isDocumentLevelElementPresent(BODY, new ArrayList<>(), "div"));
    }
}