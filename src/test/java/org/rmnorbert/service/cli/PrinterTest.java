package org.rmnorbert.service.cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.rmnorbert.data.htmlElement.ComplexHtmlElement;
import org.rmnorbert.data.htmlElement.HtmlElement;
import org.rmnorbert.data.htmlElement.type.ElementType;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrinterTest {
    private Printer printer;
    private final PrintStream standardOut = System.out;
    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        this.printer = new Printer();
        this.outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @ParameterizedTest
    @MethodSource(value = "provideMenuTypesAndReturnValue")
    void testPrintMenu_WithMenuInput(String menuType, String printedValue) {
        printer.printMenu(menuType);
        assertEquals(printedValue, outputStreamCaptor.toString().trim());
    }

    @Test
    void printAlreadyAddedDocumentLevelElements_WithoutElements_ShouldNotPrintAnything() {
        List<String> alreadyAddedDocumentLevelElements = new ArrayList<>();

        printer.printAlreadyAddedDocumentLevelElements(alreadyAddedDocumentLevelElements);

        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    void printAlreadyAddedDocumentLevelElements_WithElements_ShouldPrintAlreadyAddedElementTypes() {
        List<String> alreadyAddedDocumentLevelElements = new ArrayList<>();
        alreadyAddedDocumentLevelElements.add("HTML");
        alreadyAddedDocumentLevelElements.add("BODY");

        printer.printAlreadyAddedDocumentLevelElements(alreadyAddedDocumentLevelElements);

        assertEquals("0 : HTML\n1 : BODY", outputStreamCaptor.toString().trim());
    }

    @ParameterizedTest
    @MethodSource(value = "provideAlreadyAddedDocumentLevelListAndExpectedValue")
    void printAvailableDocumentLevelTypes_ShouldPrintExpectedDocumentLevelTypes(List<String> addedList, String expected) {
        printer.printAvailableDocumentLevelTypes(addedList);

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    void printList_WithEmptyList_ShouldNotPrintAnything() {
        List<String> list = new ArrayList<>();

        printer.printList(list);

        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    void printList_WithSingleElement_ShouldPrintElementAtWithIndex() {
        List<String> list = new ArrayList<>();
        list.add("Element");

        printer.printList(list);
        String expected = "0 : Element";

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    void printChildrenList_WithChildren_ShouldPrintChildrenWithIndex() {
        List<HtmlElement> childrenList = new ArrayList<>();
        childrenList.add(ComplexHtmlElement.builder().type(ElementType.DIV).build());

        printer.printChildrenList(childrenList);
        String expected = "0 : DIV";

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    void printChildrenList_WithoutChildren_ShouldNotPrintAnything() {
        List<HtmlElement> childrenList = new ArrayList<>();

        printer.printChildrenList(childrenList);
        assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    void printInvalidInputMessage() {
        printer.printInvalidInputMessage();
        String expected = "Invalid menu item selection!";

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @ParameterizedTest
    @MethodSource(value = "provideResultOfOperationAndNameOfOperationAndElementAndExpectedValue")
    void printOperationResult_ShouldPrintCorrectOutput(boolean resultOfOperation, String operationName,
                                                       String element, String expected) {
        printer.printOperationResult(resultOfOperation, operationName, element);
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    void printEditInfo_PrintEditingMessageWithGivenType() {
        printer.printEditInfo("type");
        String expected = "Editing: type";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    private static Stream<Arguments> provideAlreadyAddedDocumentLevelListAndExpectedValue() {
        return Stream.of(
                Arguments.of(new ArrayList<>(),
                        "0 : HTML\n1 : HEAD\n2 : TITLE\n3 : BODY\n4 : META_CHARSET\n5 : BASE"),
                Arguments.of(Collections.singletonList("HTML"),
                        "1 : HEAD\n2 : TITLE\n3 : BODY\n4 : META_CHARSET\n5 : BASE"));
    }

    private static Stream<Arguments> provideResultOfOperationAndNameOfOperationAndElementAndExpectedValue() {
        return Stream.of(
                Arguments.of(false, "testOperation", "element", "testOperation on: element cannot be duplicated"),
                Arguments.of(true, "added", "element", "Successfully added element"));
    }

    private static Stream<Arguments> provideMenuTypesAndReturnValue() {
        return Stream.of(
                Arguments.of("menu",
                        "1 : Add document level element to html\n" +
                                "2 : Edit document level element in html\n" +
                                "3 : Remove document level element from html\n" +
                                "4 : Show current form of the html\n" +
                                "5 : Finish editing\n" +
                                "6 : Generate task form html\n" +
                                "0 : Exit"),
                Arguments.of("element",
                        "1 : Add element\n" +
                                "2 : Remove element\n" +
                                "3 : Show element text\n" +
                                "4 : Set element text to argument\n" +
                                "5 : Edit element text\n" +
                                "6 : Show element attributes\n" +
                                "7 : Edit element attributes\n" +
                                "8 : Show children of element\n" +
                                "9 : Edit children of element\n" +
                                "10 : Change inline state of element\n" +
                                "11 : Finish editing"),
                Arguments.of("attribute",
                        "1 : Add attribute to element\n" +
                                "2 : Update attribute of element\n" +
                                "3 : Remove attribute of element\n" + "4 : Finish editing"));
    }
}