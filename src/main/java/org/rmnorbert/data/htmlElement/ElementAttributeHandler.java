package org.rmnorbert.data.htmlElement;

import org.rmnorbert.data.htmlElement.attribute.HtmlAttribute;

import java.util.List;

public interface ElementAttributeHandler {
    boolean addAttribute(HtmlAttribute htmlAttribute);

    List<HtmlAttribute> getAttributeList();

    boolean removeAttribute(HtmlAttribute htmlAttribute);
}
