package org.rmnorbert;

import lombok.Builder;
import org.rmnorbert.exceptions.NotFoundException;
import org.rmnorbert.data.htmlElement.HtmlElement;
import org.rmnorbert.service.cli.InputScanner;
import org.rmnorbert.service.cli.Printer;

import java.util.List;
import java.util.Optional;

@Builder
public class HtmlGeneratorEditMenu {
    @Builder.Default
    private final HtmlGeneratorEditMenuHelper editMenuHelperMethods = HtmlGeneratorEditMenuHelper.builder().build();
    @Builder.Default
    private final InputScanner scanner = new InputScanner();
    @Builder.Default
    private final Printer printer = new Printer();

    public boolean invokeElementEditMenu(HtmlElement htmlElement, Integer selectedMenu, List<String> arguments) {
        switch (selectedMenu) {
            case 1:
                editMenuHelperMethods.addElementToHtml(htmlElement);
                break;
            case 2:
                editMenuHelperMethods.removeElement(htmlElement);
                break;
            case 3:
                editMenuHelperMethods.showElementText(htmlElement);
                break;
            case 4:
                editMenuHelperMethods.setElementTextToArgument(htmlElement, arguments);
                break;
            case 5:
                editMenuHelperMethods.editElementText(htmlElement);
                break;
            case 6:
                editMenuHelperMethods.showElementAttribute(htmlElement);
                break;
            case 7:
                editMenuHelperMethods.editElementAttribute(htmlElement);
                break;
            case 8:
                editMenuHelperMethods.showChildren(htmlElement);
                break;
            case 9:
                Optional<HtmlElement> elementToEdit = editMenuHelperMethods.choseChildrenElementToEdit(htmlElement);
                editDocumentElement(elementToEdit, arguments);
                break;
            case 10:
                editMenuHelperMethods.changeInlineState(htmlElement);
            case 11:
                return false;
            default:
                printer.printInvalidInputMessage();
                break;
        }
        return true;
    }

    public void editDocumentElement(Optional<HtmlElement> elementToEdit, List<String> arguments) {
        try {
            HtmlElement htmlElement = elementToEdit.orElseThrow(() -> new NotFoundException("element"));
            boolean editing = true;
            while (editing) {
                printer.printEditInfo(htmlElement.getTypeAsString());
                printer.printMenu("element");
                Integer editCommandIndex = scanner.getNumericUserInput("What would you like to do?");
                editing = invokeElementEditMenu(htmlElement, editCommandIndex, arguments);
            }
        } catch (Exception exception) {
            printer.printInvalidInputMessage();
        }
    }
}