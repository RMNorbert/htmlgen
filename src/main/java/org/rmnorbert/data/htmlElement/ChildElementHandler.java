package org.rmnorbert.data.htmlElement;

import java.util.Optional;

public interface ChildElementHandler {
    boolean addChild(HtmlElement htmlElement);

    Optional<HtmlElement> getChildByType(String type);

    boolean removeChild(HtmlElement htmlElement);
}
