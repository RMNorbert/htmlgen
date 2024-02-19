package org.rmnorbert.data.htmlElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.rmnorbert.data.htmlElement.text.HtmlTextElement;
import org.rmnorbert.data.htmlElement.attribute.HtmlAttribute;
import org.rmnorbert.data.htmlElement.attribute.HtmlAttributes;
import org.rmnorbert.data.htmlElement.type.DocumentLevelElementType;
import org.rmnorbert.service.verify.HtmlTypeVerifierService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.rmnorbert.data.htmlElement.type.DocumentLevelElementType.BODY;
import static org.rmnorbert.data.htmlElement.type.DocumentLevelElementType.TITLE;


@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class DocumentLevelHtmlElement implements HtmlElement {
    private final DocumentLevelElementType type;
    @Builder.Default
    private final boolean isElementInLine = false;
    @Builder.Default
    private List<HtmlElement> children = new LinkedList<>();
    @Builder.Default
    private HtmlAttributes attributes = new HtmlAttributes();
    @Builder.Default
    private HtmlTextElement textElement = new HtmlTextElement("");

    @Override
    public String getTextElementValue() {
        return textElement.getValue();
    }

    @Override
    public boolean updateTextElementValue(String updatedText) {
        if (elementCanContainText()) {
            this.textElement.setValue(updatedText);
            return true;
        }
        return false;
    }

    @Override
    public boolean addChild(HtmlElement htmlElement) {
        if (isTypeValidForComplexElement(htmlElement.getTypeAsString()) ||
                isTypeValidForDocumentLevelElement(htmlElement.getTypeAsString())) {
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
    public List<HtmlAttribute> getAttributeList() {
        return attributes.getAttributes();
    }

    @Override
    public boolean addAttribute(HtmlAttribute htmlAttribute) {
        return attributes.addAttribute(htmlAttribute);
    }

    @Override
    public boolean removeAttribute(HtmlAttribute htmlAttribute) {
        return attributes.removeAttribute(htmlAttribute);
    }

    @Override
    public String getTypeAsString() {
        return type.toString();
    }

    public List<String> getCreatedDocumentLevelElementTypes() {
        List<String> created = new ArrayList<>();
        created.add(this.type.toString());
        created.addAll(children.stream()
                .map(HtmlElement::getTypeAsString)
                .filter(HtmlTypeVerifierService::verifyDocumentLevelElementType)
                .collect(Collectors.toList()));

        return created;
    }

    private boolean isTypeValidForComplexElement(String type) {
        return HtmlTypeVerifierService.verifyElementType(type);
    }

    private boolean isTypeValidForDocumentLevelElement(String type) {
        return HtmlTypeVerifierService.verifyDocumentLevelElementType(type) &&
                !HtmlTypeVerifierService.isDocumentLevelElementPresent(this.type, children, type);
    }

    private boolean elementCanContainText() {
        return this.type.equals(TITLE) || this.type.equals(BODY);
    }

}
