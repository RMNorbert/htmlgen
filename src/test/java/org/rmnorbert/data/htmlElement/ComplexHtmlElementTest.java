package org.rmnorbert.data.htmlElement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.rmnorbert.data.htmlElement.type.DocumentLevelElementType.BODY;
import static org.rmnorbert.data.htmlElement.type.ElementType.*;

class ComplexHtmlElementTest {
    private ComplexHtmlElement htmlElement;

    @BeforeEach
    public void init() {
        htmlElement = ComplexHtmlElement.builder().type(DIV).build();
    }

    @Test
    void addChild_WithValidChildHtmlElement_ShouldAddToChildrenList() {
        ComplexHtmlElement childHtmlElement = ComplexHtmlElement.builder().type(H1).build();
        htmlElement.addChild(childHtmlElement);
        assertEquals(childHtmlElement, htmlElement.getChildren().get(0));
    }

    @Test
    void addChild_WithInValidChildHtmlElement_ShouldNotAddToChildrenList() {
        DocumentLevelHtmlElement childHtmlElement = DocumentLevelHtmlElement.builder().type(BODY).build();
        htmlElement.addChild(childHtmlElement);

        assertEquals(0, htmlElement.getChildren().size());
    }

    @Test
    void removeChild_WithExistingChild_ShouldRemoveFromChildrenList() {
        ComplexHtmlElement childHtmlElement = ComplexHtmlElement.builder().type(H1).build();
        ComplexHtmlElement secondChildHtmlElement = ComplexHtmlElement.builder().type(P).build();
        htmlElement.addChild(childHtmlElement);
        htmlElement.addChild(secondChildHtmlElement);
        htmlElement.removeChild(childHtmlElement);

        assertFalse(htmlElement.getChildren().contains(childHtmlElement));
    }

}