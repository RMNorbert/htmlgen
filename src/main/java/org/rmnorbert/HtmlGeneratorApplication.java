package org.rmnorbert;

import org.rmnorbert.data.htmlElement.DocumentLevelHtmlElement;
import org.rmnorbert.data.htmlElement.HtmlElement;
import org.rmnorbert.service.cli.InputScanner;
import org.rmnorbert.service.cli.Printer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.rmnorbert.data.htmlElement.type.DocumentLevelElementType.HTML;

public class HtmlGeneratorApplication {
    private final HtmlGeneratorMenuHelper menuHelperMethods;
    private final DocumentLevelHtmlElement html;
    private final InputScanner scanner;
    private final Printer printer;
    private final List<String> argumentList;

    public HtmlGeneratorApplication(String name, String email) {
        this.menuHelperMethods = HtmlGeneratorMenuHelper.builder().build();
        this.scanner = new InputScanner();
        this.printer = new Printer();
        this.html = DocumentLevelHtmlElement.builder().type(HTML).build();
        this.argumentList = Arrays.asList(name, email);
    }

    public void run() {
        boolean keepAppRunning = true;
        while (keepAppRunning) {
            printer.printMenu("menu");
            Integer numericUserInput = scanner.getNumericUserInput("Please select a menu item!");
            keepAppRunning = invokeMenuItem(numericUserInput);
        }
    }

    private boolean invokeMenuItem(Integer selectedMenu) {
        switch (selectedMenu) {
            case 0:
                return false;
            case 1:
                menuHelperMethods.addDocumentLevelElementToHtml(html);
                break;
            case 2:
                Optional<HtmlElement> element = menuHelperMethods.choseDocumentLevelElementToEdit(html);
                menuHelperMethods.editDocumentElement(element, argumentList);
                break;
            case 3:
                menuHelperMethods.removeDocumentElement(html);
                break;
            case 4:
                menuHelperMethods.showCurrentForm(html);
                break;
            case 5:
                menuHelperMethods.showCurrentForm(html);
                return false;
            case 6:
                menuHelperMethods.generateTaskForm(html, argumentList);
                break;
            default:
                printer.printInvalidInputMessage();
                break;
        }
        return true;
    }


}
