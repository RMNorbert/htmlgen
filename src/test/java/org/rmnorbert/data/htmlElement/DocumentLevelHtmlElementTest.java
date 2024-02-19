package org.rmnorbert.data.htmlElement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.rmnorbert.data.htmlElement.type.DocumentLevelElementType.*;

class DocumentLevelHtmlElementTest {
    private DocumentLevelHtmlElement htmlElement;

    @BeforeEach
    public void init() {
        htmlElement = DocumentLevelHtmlElement.builder().type(HTML).build();
    }

    @Test
    void addChild_WithValidChildHtmlElement_ShouldAddToChildrenList() {
        DocumentLevelHtmlElement childHtmlElement = DocumentLevelHtmlElement.builder().type(BODY).build();
        htmlElement.addChild(childHtmlElement);
        assertEquals(childHtmlElement, htmlElement.getChildren().get(0));
    }

    @Test
    void addChild_WithInValidChildHtmlElement_ShouldNotAddToChildrenList() {
        DocumentLevelHtmlElement childHtmlElement = DocumentLevelHtmlElement.builder().type(HTML).build();
        htmlElement.addChild(childHtmlElement);
        assertEquals(0, htmlElement.getChildren().size());
    }

    @Test
    void removeChild_WithExistingChild_ShouldRemoveFromChildrenList() {
        DocumentLevelHtmlElement childHtmlElement = DocumentLevelHtmlElement.builder().type(HEAD).build();
        DocumentLevelHtmlElement secondChildHtmlElement = DocumentLevelHtmlElement.builder().type(BODY).build();
        htmlElement.addChild(childHtmlElement);
        htmlElement.addChild(secondChildHtmlElement);
        htmlElement.removeChild(childHtmlElement);

        assertFalse(htmlElement.getChildren().contains(childHtmlElement));
    }

}