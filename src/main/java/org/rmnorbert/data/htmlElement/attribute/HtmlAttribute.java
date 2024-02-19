package org.rmnorbert.data.htmlElement.attribute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HtmlAttribute {
    private String name;
    private String value;

    public void updateName(String updateName) {
        this.name = updateName;
    }

    public void updateValue(String updateValue) {
        this.value = updateValue;
    }
}
