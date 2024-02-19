package org.rmnorbert.service.render;

import org.junit.jupiter.api.Test;
import org.rmnorbert.data.htmlElement.ComplexHtmlElement;
import org.rmnorbert.data.htmlElement.DocumentLevelHtmlElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.rmnorbert.data.htmlElement.type.DocumentLevelElementType.BODY;
import static org.rmnorbert.data.htmlElement.type.DocumentLevelElementType.HTML;
import static org.rmnorbert.data.htmlElement.type.ElementType.DIV;

class StringBuilderRenderEngineImplementationTest {
    private final StringBuilderRenderEngineImplementation stringBuilder = new StringBuilderRenderEngineImplementation();

    @Test
    void render_WithEmptyHtmlElement_ShouldReturnExpectedHtml() {
        DocumentLevelHtmlElement htmlElement = DocumentLevelHtmlElement.builder().type(HTML).build();
        DocumentLevelHtmlElement htmlBodyElement = DocumentLevelHtmlElement.builder().type(BODY).build();
        ComplexHtmlElement htmlDivElement = ComplexHtmlElement.builder().type(DIV).build();
        htmlBodyElement.addChild(htmlDivElement);
        htmlElement.addChild(htmlBodyElement);

        String actual = stringBuilder.render(htmlElement);
        String expected = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <body>\n" +
                "        <div>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>\n";

        assertEquals(expected, actual);
    }
}