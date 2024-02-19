package org.rmnorbert.service.formgenerator;

import org.rmnorbert.data.htmlElement.ComplexHtmlElement;
import org.rmnorbert.data.htmlElement.DocumentLevelHtmlElement;
import org.rmnorbert.data.htmlElement.HtmlElement;
import org.rmnorbert.data.htmlElement.attribute.HtmlAttribute;
import org.rmnorbert.data.htmlElement.attribute.HtmlAttributes;
import org.rmnorbert.data.htmlElement.text.HtmlTextElement;
import org.rmnorbert.data.htmlElement.type.ElementType;

import java.util.LinkedList;
import java.util.List;

import static org.rmnorbert.data.htmlElement.type.DocumentLevelElementType.*;
import static org.rmnorbert.data.htmlElement.type.ElementType.*;

public class TaskFormGenerator {
    private static final String REPOSITORY_URL = "https://github.com/RMNorbert/htmlgen";
    private boolean firstGeneration = true;

    public boolean formHtml(DocumentLevelHtmlElement element, List<String> arguments) {
        if (firstGeneration) {
            initializeHead(element, arguments);
            initializeBody(element, arguments);
            firstGeneration = false;
            return true;
        }
        return false;
    }

    private void initializeHead(DocumentLevelHtmlElement element, List<String> arguments) {
        String name = arguments.get(0);
        DocumentLevelHtmlElement head = DocumentLevelHtmlElement.builder().type(HEAD).build();
        head.addChild(DocumentLevelHtmlElement.builder().type(TITLE)
                .textElement(HtmlTextElement.builder().value(name + " - Teszt Feladat").build())
                .build());
        element.addChild(head);
    }

    private void initializeBody(DocumentLevelHtmlElement element, List<String> arguments) {
        String name = arguments.get(0);
        String email = arguments.get(1);
        HtmlElement body = DocumentLevelHtmlElement.builder().type(BODY).build();
        ComplexHtmlElement elementP = initializeComplexElement(P, "");
        ComplexHtmlElement elementPAnchor = initializeHref(REPOSITORY_URL, "Megoldás");
        elementPAnchor.changeElementInLineState();
        elementP.addChild(elementPAnchor);
        ComplexHtmlElement table = buildTaskTable(name, email);
        ComplexHtmlElement anchor = initializeHref("http://lpsolutions.hu", "L&P Solutions");

        body.addChild(initializeComplexElement(H1, "Teszt Feladat"));
        body.addChild(elementP);
        body.addChild(initializeComplexElement(P, "A feladat elkészítőjének adatai"));
        body.addChild(table);
        body.addChild(anchor);
        element.addChild(body);
    }

    private ComplexHtmlElement buildTaskTable(String name, String email) {
        ComplexHtmlElement table = initializeComplexElement(TABLE, "",
                initializeAttributes("border", "1px solid black"));
        ComplexHtmlElement firstTr = initializeComplexElement(TR, "");
        ComplexHtmlElement firstTrFirstTd = initializeComplexElement(TD, "Név");
        ComplexHtmlElement firstTrSecondTd = initializeComplexElement(TD, name);
        ComplexHtmlElement secondTr = initializeComplexElement(TR, "");
        ComplexHtmlElement secondTrFirstTd = initializeComplexElement(TD, "Elérhetőség");
        ComplexHtmlElement secondTrSecondTd = initializeComplexElement(TD, email);

        firstTr.addChild(firstTrFirstTd);
        firstTr.addChild(firstTrSecondTd);
        secondTr.addChild(secondTrFirstTd);
        secondTr.addChild(secondTrSecondTd);
        table.addChild(firstTr);
        table.addChild(secondTr);
        return table;
    }

    private ComplexHtmlElement initializeComplexElement(ElementType elementType,
                                                        String text,
                                                        HtmlAttributes elementAttributes) {
        return text.isEmpty() && elementAttributes.getAttributes().isEmpty() ?
                ComplexHtmlElement.builder()
                        .type(elementType)
                        .build() :

                elementAttributes.getAttributes().isEmpty() ?
                        ComplexHtmlElement.builder()
                                .type(elementType)
                                .textElement(HtmlTextElement.builder().value(text).build()).build() :

                        ComplexHtmlElement.builder()
                                .type(elementType)
                                .textElement(HtmlTextElement.builder().value(text).build())
                                .attributes(elementAttributes)
                                .build();
    }

    private ComplexHtmlElement initializeComplexElement(ElementType elementType, String text) {
        return ComplexHtmlElement.builder()
                .type(elementType)
                .textElement(HtmlTextElement.builder().value(text).build())
                .build();
    }

    private ComplexHtmlElement initializeHref(String hrefValue, String text) {
        return ComplexHtmlElement.builder().type(A)
                .attributes(initializeAttributes("href", hrefValue))
                .textElement(HtmlTextElement.builder().value(text).build()).build();
    }

    private HtmlAttributes initializeAttributes(String attributeName, String attributeValue) {
        List<HtmlAttribute> attributeList = new LinkedList<>();
        attributeList.add(HtmlAttribute.builder().name(attributeName).value(attributeValue).build());
        return HtmlAttributes.builder().attributes(attributeList).build();
    }
}
