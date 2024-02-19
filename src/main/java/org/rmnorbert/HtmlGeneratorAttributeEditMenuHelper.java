package org.rmnorbert;

import lombok.Builder;
import org.rmnorbert.data.htmlElement.HtmlElement;
import org.rmnorbert.data.htmlElement.attribute.HtmlAttribute;
import org.rmnorbert.service.cli.InputScanner;
import org.rmnorbert.service.cli.Printer;

import java.util.List;

@Builder
public class HtmlGeneratorAttributeEditMenuHelper {
    @Builder.Default
    private final InputScanner scanner = new InputScanner();
    @Builder.Default
    private final Printer printer = new Printer();

    public void addAttribute(HtmlElement htmlElement) {
        try {
            printer.printEditInfo(htmlElement.getTypeAsString());
            String attributeName = scanner.getUserInput("Set the attribute name to define");
            String attributeValue = scanner.getUserInput("Set the attribute value for " + attributeName);

            HtmlAttribute attribute = createAttributeOf(attributeName, attributeValue);
            boolean result = htmlElement.addAttribute(attribute);
            printer.printOperationResult(result, "add ", attributeName + " to " + htmlElement.getTypeAsString());
        } catch (Exception e) {
            printer.printInvalidInputMessage();
        }
    }

    public HtmlAttribute createAttributeOf(String attributeName, String attributeValue) {
        return HtmlAttribute.builder()
                .name(attributeName)
                .value(attributeValue)
                .build();
    }

    public void updateAttribute(HtmlElement htmlElement) {
        if (!getAttributesOfElement(htmlElement).isEmpty()) {
            try {
                Integer attributeIndex = scanner.getNumericUserInput("Select the attribute to update");
                HtmlAttribute htmlAttribute = getAttribute(htmlElement, attributeIndex);

                String attributeName = htmlAttributeUpdateUserInput(htmlAttribute.getName(), "Leave empty and press enter to skip the update of attribute name or provide the new name");
                String attributeValue = htmlAttributeUpdateUserInput(htmlAttribute.getValue(), "Leave empty and press enter to skip the update of " + attributeName + "value or provide the new value");

                htmlAttribute.updateName(attributeName);
                htmlAttribute.updateValue(attributeValue);
                printer.printOperationResult(true, "update ", attributeName);
            } catch (Exception e) {
                printer.printInvalidInputMessage();
            }
        }
    }

    public void removeAttribute(HtmlElement htmlElement) {
        if (!getAttributesOfElement(htmlElement).isEmpty()) {
            try {
                printer.printEditInfo(htmlElement.getTypeAsString());
                Integer attributeToDelete = scanner.getNumericUserInput("Select the attribute to remove");

                boolean result = htmlElement.removeAttribute(getAttribute(htmlElement, attributeToDelete));
                printer.printOperationResult(result, "update ", htmlElement.getTypeAsString());
            } catch (Exception e) {
                printer.printInvalidInputMessage();
            }
        }
    }

    private List<HtmlAttribute> getAttributesOfElement(HtmlElement htmlElement) {
        return htmlElement.getAttributes().getAttributes();
    }

    private HtmlAttribute getAttribute(HtmlElement htmlElement, Integer index) {
        HtmlAttribute htmlAttribute = getAttributesOfElement(htmlElement).get(index);
        printer.printEditInfo(htmlAttribute.getName());
        return htmlAttribute;
    }

    private String htmlAttributeUpdateUserInput(String htmlAttributeValue, String message) {
        String updateAttributeValue = scanner.getUserInput(message);
        return !updateAttributeValue.isEmpty() ? updateAttributeValue : htmlAttributeValue;
    }

}