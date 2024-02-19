package org.rmnorbert.data.htmlElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.rmnorbert.data.htmlElement.attribute.HtmlAttribute;
import org.rmnorbert.data.htmlElement.attribute.HtmlAttributes;
import org.rmnorbert.data.htmlElement.text.HtmlTextElement;
import org.rmnorbert.data.htmlElement.type.ElementType;
import org.rmnorbert.service.verify.HtmlTypeVerifierService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ComplexHtmlElement implements HtmlElement {
    private final ElementType type;
    @Builder.Default
    private boolean isElementInLine = false;
    @Builder.Default
    private HtmlTextElement textElement = new HtmlTextElement("");
    @Builder.Default
    private List<HtmlElement> children = new LinkedList<>();
    @Builder.Default
    private HtmlAttributes attributes = new HtmlAttributes();

    public void changeElementInLineState() {
        isElementInLine = true;
    }

    @Override
    public String getTextElementValue() {
        return textElement.getValue();
    }

    @Override
    public String getTypeAsString() {
        return type.toString();
    }

    @Override
    public boolean updateTextElementValue(String text) {
        textElement.setValue(text);
        return true;
    }

    @Override
    public boolean addChild(HtmlElement htmlElement) {
        if (isTypeValidForComplexElement(htmlElement.getTypeAsString())) {
            children.add(htmlElement);
            return true;
        }
        return false;
    }

    @Override
    public Optional<HtmlElement> getChildByType(String type) {
        return children.stream()
                .filter(element -> element.getTypeAsString().equals(type))
                .findFirst();
    }

    @Override
    public boolean removeChild(HtmlElement htmlElement) {
        return children.remove(htmlElement);
    }

    @Override
    public boolean addAttribute(HtmlAttribute htmlAttribute) {
        return attributes.addAttribute(htmlAttribute);
    }

    @Override
    public List<HtmlAttribute> getAttributeList() {
        return attributes.getAttributes();
    }

    @Override
    public boolean removeAttribute(HtmlAttribute htmlAttribute) {
        return attributes.removeAttribute(htmlAttribute);
    }

    private boolean isTypeValidForComplexElement(String type) {
        return HtmlTypeVerifierService.verifyElementType(type);
    }
}
