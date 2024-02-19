package org.rmnorbert.data.htmlElement;

import org.rmnorbert.data.htmlElement.attribute.HtmlAttributes;

import java.util.List;

public interface HtmlElement extends ChildElementHandler, ElementAttributeHandler {
    String getTypeAsString();

    List<HtmlElement> getChildren();

    String getTextElementValue();

    HtmlAttributes getAttributes();

    boolean updateTextElementValue(String text);

    boolean isElementInLine();
}
