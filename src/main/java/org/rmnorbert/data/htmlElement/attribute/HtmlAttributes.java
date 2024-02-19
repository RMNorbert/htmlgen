package org.rmnorbert.data.htmlElement.attribute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HtmlAttributes {
    @Builder.Default
    private List<HtmlAttribute> attributes = new LinkedList<>();

    public boolean addAttribute(HtmlAttribute htmlAttribute) {
        return attributes.add(htmlAttribute);
    }

    public boolean removeAttribute(HtmlAttribute htmlAttribute) {
        return attributes.remove(htmlAttribute);
    }

}
