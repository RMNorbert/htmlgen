package org.rmnorbert.service.render;

import org.rmnorbert.data.htmlElement.HtmlElement;
import org.rmnorbert.data.htmlElement.attribute.HtmlAttribute;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.rmnorbert.data.htmlElement.type.ElementType.A;
import static org.rmnorbert.data.htmlElement.type.ElementType.P;

public class StringBuilderRenderEngineImplementation implements RenderEngine {
    private static final String HTML_TAG = "<!DOCTYPE html>\n";
    private static final String INDENTATION_LEVEL = "    ";

    @Override
    public String render(HtmlElement root) {
        int level = 0;
        StringBuilder htmlBuilder = new StringBuilder()
                .append(HTML_TAG)
                .append(renderTree(root, level));
        return htmlBuilder.toString();
    }

    private String renderTree(HtmlElement root, int level) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(renderOpenTag(root, level))
                .append(renderChildren(root.getChildren(), level + 1))
                .append(renderCloseTag(root, level));
        return stringBuilder.toString();
    }

    private String renderOpenTag(HtmlElement root, int level) {
        return new StringBuilder()
                .append(indent(level, root))
                .append("<").append(root.getTypeAsString().toLowerCase()).append(renderAttribute(root.getAttributeList())).append(">")
                .append(renderText(root))
                .toString();
    }

    private String renderCloseTag(HtmlElement root, int level) {
        return new StringBuilder()
                .append(!root.getTextElementValue().isEmpty() ? "" : closeIndent(level, root))
                .append("</").append(root.getTypeAsString().toLowerCase()).append(renderNewLine(root))
                .toString();
    }

    private String renderChildren(List<HtmlElement> children, int level) {
        return children.stream().map(child -> renderTree(child, level)).collect(Collectors.joining());
    }

    private String renderNewLine(HtmlElement element) {
        return element.isElementInLine() ? ">" : ">\n";
    }

    private String renderText(HtmlElement root) {
        return root.getTypeAsString().equals(A.toString()) |
                root.getTypeAsString().equals(P.toString()) |
                !root.getTextElementValue().isEmpty() ? root.getTextElementValue() : "\n";
    }

    private String renderAttribute(List<HtmlAttribute> attributes) {
        return attributes.stream()
                .map(htmlAttribute -> toHtml(htmlAttribute.getName(), htmlAttribute.getValue()))
                .collect(Collectors.joining());
    }

    private String toHtml(String name, String value) {
        return new StringBuilder().append(" ").append(name).append("=").append("\"").append(value).append("\" ").toString();
    }

    private String indent(int level, HtmlElement element) {
        return element.isElementInLine() ? "" : String.join("", Collections.nCopies(level, INDENTATION_LEVEL));
    }

    private String closeIndent(int level, HtmlElement element) {
        boolean isChildInline = element.getChildren().size() == 1 && element.getChildren().get(0).isElementInLine();
        return isChildInline ? "" : indent(level, element);
    }
}
