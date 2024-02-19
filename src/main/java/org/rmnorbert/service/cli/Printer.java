package org.rmnorbert.service.cli;

import org.rmnorbert.data.htmlElement.HtmlElement;
import org.rmnorbert.data.htmlElement.type.DocumentLevelElementType;
import org.rmnorbert.data.htmlElement.type.ElementType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Printer {
    private final Map<String, String> menu;
    private final Map<String, String> elementMenu;
    private final Map<String, String> attributeEditMenu;

    private final Map<Integer, ElementType> elementTypes =
            IntStream.range(0, ElementType.values().length)
                    .boxed()
                    .collect(Collectors.toMap(i -> i, i -> ElementType.values()[i]));
    private final Map<Integer, DocumentLevelElementType> documentLevelTypes =
            IntStream.range(0, DocumentLevelElementType.values().length)
                    .boxed()
                    .collect(Collectors.toMap(i -> i, i -> DocumentLevelElementType.values()[i]));

    public Printer() {
        this.menu = new LinkedHashMap<String, String>() {{
            put("1", "Add document level element to html");
            put("2", "Edit document level element in html");
            put("3", "Remove document level element from html");
            put("4", "Show current form of the html");
            put("5", "Finish editing");
            put("6", "Generate task form html");
            put("0", "Exit");
        }};
        this.elementMenu = new LinkedHashMap<String, String>() {{
            put("1", "Add element");
            put("2", "Remove element");
            put("3", "Show element text");
            put("4", "Set element text to argument");
            put("5", "Edit element text");
            put("6", "Show element attributes");
            put("7", "Edit element attributes");
            put("8", "Show children of element");
            put("9", "Edit children of element");
            put("10", "Change inline state of element");
            put("11", "Finish editing");
        }};
        this.attributeEditMenu = new LinkedHashMap<String, String>() {{
            put("1", "Add attribute to element");
            put("2", "Update attribute of element");
            put("3", "Remove attribute of element");
            put("4", "Finish editing");
        }};
    }

    public void printMenu(String selectedMenuToPrint) {
        switch (selectedMenuToPrint) {
            case "menu":
                printMap(menu);
                break;
            case "element":
                printMap(elementMenu);
                break;
            case "attribute":
                printMap(attributeEditMenu);
                break;
            default:
                printInvalidInputMessage();
                break;
        }
    }

    public void printTypes() {
        elementTypes.forEach((key, value) -> printMessage(key + " : " + value));
    }

    public void printAlreadyAddedDocumentLevelElements(List<String> alreadyAddedDocumentLevelElements) {
        IntStream.range(0, alreadyAddedDocumentLevelElements.size())
                .forEach(index -> printMessage(index + " : " + alreadyAddedDocumentLevelElements.get(index)));
    }

    public void printAvailableDocumentLevelTypes(List<String> alreadyAddedDocumentLevelElements) {
        documentLevelTypes.entrySet().stream()
                .filter(entry -> !alreadyAddedDocumentLevelElements.contains(entry.getValue().toString()))
                .forEach(entry -> printMessage(entry.getKey() + " : " + entry.getValue()));
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    private void printMap(Map<String, String> mapToPrint) {
        mapToPrint.forEach((key, value) -> printMessage(key + " : " + value));
    }

    public void printList(List<String> list) {
        IntStream.range(0, list.size())
                .forEach(index -> printMessage(index + " : " + list.get(index)));
    }

    public void printChildrenList(List<HtmlElement> children) {
        IntStream.range(0, children.size())
                .forEach(index -> printMessage(index + " : " + children.get(index).getTypeAsString()));
    }

    public void printInvalidInputMessage() {
        printMessage("Invalid menu item selection!\n");
    }

    public void printOperationResult(boolean result, String operation, String element) {
        if (result) {
            printMessage("Successfully " + operation + " " + element + "\n");
        } else {
            printMessage(operation + " on: " + element + " cannot be duplicated\n");
        }
    }

    public void printEditInfo(String elementType) {
        printMessage("\nEditing: " + elementType + "\n");
    }
}

