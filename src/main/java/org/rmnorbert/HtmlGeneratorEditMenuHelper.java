package org.rmnorbert;

import lombok.Builder;
import org.rmnorbert.data.htmlElement.ComplexHtmlElement;
import org.rmnorbert.data.htmlElement.HtmlElement;
import org.rmnorbert.data.htmlElement.attribute.HtmlAttribute;
import org.rmnorbert.data.htmlElement.type.ElementType;
import org.rmnorbert.service.cli.InputScanner;
import org.rmnorbert.service.cli.Printer;

import java.util.List;
import java.util.Optional;

@Builder
public class HtmlGeneratorEditMenuHelper {
    @Builder.Default
    private final HtmlGeneratorAttributeEditMenu attributeEditMenu = HtmlGeneratorAttributeEditMenu.builder().build();
    @Builder.Default
    private final InputScanner scanner = new InputScanner();
    @Builder.Default
    private final Printer printer = new Printer();

    public void addElementToHtml(HtmlElement htmlElement) {
        try {
            printer.printTypes();
            Integer numberInput = scanner.getNumericUserInput("Which type of element would you like to add to the html?");
            boolean result = addChildToHtmlElement(htmlElement, numberInput);
            printer.printOperationResult(result, "add ", getTypeByIndex(numberInput).toString());
        } catch (Exception e) {
            printer.printInvalidInputMessage();
        }
    }

    public void removeElement(HtmlElement htmlElement) {
        try {
            List<HtmlElement> elementsChildrenList = htmlElement.getChildren();
            printer.printChildrenList(elementsChildrenList);
            Integer numberInput = scanner.getNumericUserInput("Select the element to remove or return by pressing 99");
            if (numberInput < 99) {
                HtmlElement htmlElementToRemove = elementsChildrenList.get(numberInput);

                boolean result = removeChildElement(htmlElement, htmlElementToRemove);
                printer.printOperationResult(result, "remove ", htmlElementToRemove.getTypeAsString());
            }
        } catch (Exception e) {
            printer.printInvalidInputMessage();
        }
    }

    public Optional<HtmlElement> choseChildrenElementToEdit(HtmlElement htmlElement) {
        try {
            List<HtmlElement> childrenList = htmlElement.getChildren();
            if (!childrenList.isEmpty()) {
                printer.printChildrenList(childrenList);
                Integer indexOfElement = scanner.getNumericUserInput("Which element would you like to edit?");
                return getChildrenByIndex(htmlElement, indexOfElement);
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void showElementText(HtmlElement htmlElement) {
        printer.printEditInfo(htmlElement.getTypeAsString());
        printer.printMessage(htmlElement.getTextElementValue());
    }

    public void setElementTextToArgument(HtmlElement htmlElement, List<String> arguments) {
        printer.printList(arguments);
        Integer selectedArgument = scanner.getNumericUserInput("Which argument would you like to use?");
        boolean result = htmlElement.updateTextElementValue(arguments.get(selectedArgument));
        printer.printOperationResult(result, "updated text ", htmlElement.getTypeAsString());
    }

    public void editElementText(HtmlElement htmlElement) {
        try {
            printer.printEditInfo(htmlElement.getTypeAsString());
            String input = scanner.getUserInput("Set the text of the element ");
            boolean result = htmlElement.updateTextElementValue(input);
            printer.printOperationResult(result, "updated text ", htmlElement.getTypeAsString());
        } catch (Exception e) {
            printer.printInvalidInputMessage();
        }
    }

    public void showElementAttribute(HtmlElement htmlElement) {
        printer.printEditInfo(htmlElement.getTypeAsString());
        getAttributesOfElement(htmlElement)
                .forEach(htmlAttribute -> printer.printMessage(htmlAttribute.getName() + "=" + htmlAttribute.getValue() + " "));
    }

    public void editElementAttribute(HtmlElement htmlElement) {
        attributeEditMenu.editElementAttribute(htmlElement);
    }

    public void showChildren(HtmlElement htmlElement) {
        printer.printChildrenList(htmlElement.getChildren());
    }

    public void changeInlineState(HtmlElement htmlElement) {
        if (htmlElement instanceof ComplexHtmlElement) {
            ComplexHtmlElement complexHtmlElement = (ComplexHtmlElement) htmlElement;
            complexHtmlElement.changeElementInLineState();
            printer.printMessage(htmlElement.getTypeAsString() + "inline state changed to " + htmlElement.isElementInLine());
        } else {
            printer.printMessage("Inline state cannot be changed");
        }
    }

    private boolean addChildToHtmlElement(HtmlElement htmlElement, Integer index) {
        return htmlElement.addChild(ComplexHtmlElement.builder().type(getTypeByIndex(index)).build());
    }

    private List<HtmlAttribute> getAttributesOfElement(HtmlElement htmlElement) {
        return htmlElement.getAttributes().getAttributes();
    }

    private Optional<HtmlElement> getChildrenByIndex(HtmlElement htmlElement, Integer indexOfElement) {
        return Optional.of(htmlElement.getChildren().get(indexOfElement));
    }

    private ElementType getTypeByIndex(Integer numberInput) {
        return ElementType.getByIndex(numberInput);
    }

    private boolean removeChildElement(HtmlElement htmlElement, HtmlElement htmlElementToRemove) {
        return htmlElement.removeChild(htmlElementToRemove);
    }

}