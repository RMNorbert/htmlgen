package org.rmnorbert;

import lombok.Builder;
import org.rmnorbert.exceptions.NotFoundException;
import org.rmnorbert.data.htmlElement.DocumentLevelHtmlElement;
import org.rmnorbert.data.htmlElement.HtmlElement;
import org.rmnorbert.service.cli.InputScanner;
import org.rmnorbert.service.cli.Printer;
import org.rmnorbert.service.formgenerator.TaskFormGenerator;
import org.rmnorbert.service.render.StringBuilderRenderEngineImplementation;

import java.util.List;
import java.util.Optional;

import static org.rmnorbert.data.htmlElement.type.DocumentLevelElementType.getByIndex;

@Builder
public class HtmlGeneratorMenuHelper {
    @Builder.Default
    private final TaskFormGenerator taskFormGenerator = new TaskFormGenerator();
    @Builder.Default
    private final StringBuilderRenderEngineImplementation renderEngine = new StringBuilderRenderEngineImplementation();
    @Builder.Default
    private final InputScanner scanner = new InputScanner();
    @Builder.Default
    private final Printer printer = new Printer();
    @Builder.Default
    private final HtmlGeneratorEditMenu editMenuHelperMethods = HtmlGeneratorEditMenu.builder().build();

    public void addDocumentLevelElementToHtml(DocumentLevelHtmlElement html) {
        try {
            List<String> elementTypes = html.getCreatedDocumentLevelElementTypes();
            if (!elementTypes.isEmpty()) {
                printer.printAvailableDocumentLevelTypes(elementTypes);
                Integer numberInput = scanner.getNumericUserInput("Which type of element would you like to add to the html?");
                boolean isChildAdded = addDocumentChildByIndex(html, numberInput);
                printer.printOperationResult(isChildAdded, "add ", getByIndex(numberInput).toString());
            }
        } catch (Exception e) {
            printer.printInvalidInputMessage();
        }
    }

    public Optional<HtmlElement> choseDocumentLevelElementToEdit(DocumentLevelHtmlElement html) {
        try {
            List<String> alreadyAddedElementList = html.getCreatedDocumentLevelElementTypes();
            if (!alreadyAddedElementList.isEmpty()) {
                printer.printAlreadyAddedDocumentLevelElements(alreadyAddedElementList);
                Integer indexOfElement = scanner.getNumericUserInput("Which type of element would you like to edit?");
                return getChildElementByType(html, indexOfElement);
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void editDocumentElement(Optional<HtmlElement> elementToEdit, List<String> arguments) {
        editMenuHelperMethods.editDocumentElement(elementToEdit, arguments);
    }

    public void removeDocumentElement(DocumentLevelHtmlElement html) {
        try {
            List<String> alreadyAddedElementList = html.getCreatedDocumentLevelElementTypes();
            printer.printAlreadyAddedDocumentLevelElements(alreadyAddedElementList);
            Integer numberInput = scanner.getNumericUserInput("Select the element to remove or return by pressing 9");
            if (numberInput < 9) {
                HtmlElement htmlElementToRemove = getChildElementByType(html, numberInput)
                        .orElseThrow(() -> new NotFoundException("child"));

                boolean result = removeChildElement(html, htmlElementToRemove);
                printer.printOperationResult(result, "remove ", htmlElementToRemove.getTypeAsString());
            }
        } catch (Exception e) {
            printer.printInvalidInputMessage();
        }
    }

    public void showCurrentForm(HtmlElement htmlElement) {
        printer.printMessage(renderEngine.render(htmlElement));
    }

    public void generateTaskForm(DocumentLevelHtmlElement html, List<String> arguments) {
        boolean result = taskFormGenerator.formHtml(html, arguments);
        printer.printOperationResult(result, "task form generation ", "HTML");
    }

    private Optional<HtmlElement> getChildElementByType(DocumentLevelHtmlElement htmlElement, Integer indexOfElement) {
        return htmlElement.getChildByType(htmlElement.getCreatedDocumentLevelElementTypes().get(indexOfElement));
    }

    private boolean addDocumentChildByIndex(HtmlElement htmlElement, Integer index) {
        return htmlElement.addChild(DocumentLevelHtmlElement.builder().type(getByIndex(index)).build());
    }

    private boolean removeChildElement(HtmlElement htmlElement, HtmlElement htmlElementToRemove) {
        return htmlElement.removeChild(htmlElementToRemove);
    }

}
