package com.sealcia.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Choice {
    private String id;
    private String content;
    private boolean correct;
    private String questionId;
}
