package org.rmnorbert;

import lombok.Builder;
import org.rmnorbert.data.htmlElement.HtmlElement;
import org.rmnorbert.service.cli.InputScanner;
import org.rmnorbert.service.cli.Printer;

@Builder
public class HtmlGeneratorAttributeEditMenu {
    @Builder.Default
    private final HtmlGeneratorAttributeEditMenuHelper editHelper = HtmlGeneratorAttributeEditMenuHelper.builder().build();
    @Builder.Default
    private final InputScanner scanner = new InputScanner();
    @Builder.Default
    private final Printer printer = new Printer();

    public boolean invokeAttributeEditMenu(HtmlElement htmlElement, Integer selectedMenu) {
        switch (selectedMenu) {
            case 1:
                editHelper.addAttribute(htmlElement);
                break;
            case 2:
                editHelper.updateAttribute(htmlElement);
                break;
            case 3:
                editHelper.removeAttribute(htmlElement);
                break;
            case 4:
                return false;
            default:
                printer.printInvalidInputMessage();
                break;
        }
        return true;
    }

    public void editElementAttribute(HtmlElement htmlElement) {
        try {
            boolean editing = true;
            while (editing) {
                printer.printMenu("attribute");
                printer.printEditInfo(htmlElement.getTypeAsString());
                Integer editCommandIndex = scanner.getNumericUserInput("What would you like to do?");
                editing = invokeAttributeEditMenu(htmlElement, editCommandIndex);
            }
        } catch (Exception e) {
            printer.printInvalidInputMessage();
        }
    }
}