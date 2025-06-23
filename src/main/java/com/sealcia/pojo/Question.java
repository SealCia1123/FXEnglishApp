package com.sealcia.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Question {
    private String id;
    private String content;
    private int categoryId;
    private List<Choice> choices;

    public Question(String id, String content, int categoryId) {
        this.id = id;
        this.content = content;
        this.categoryId = categoryId;
    }

    public String getChoiceView() {
        String str = "";
        for (Choice c : this.choices) {
            str += String.format("- %s\n", c.getContent());
        }
        return str;
    }
}
