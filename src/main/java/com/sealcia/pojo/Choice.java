package com.sealcia.pojo;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PUBLIC)
public class Choice {
    private String id;
    private String content;
    private boolean correct;
    private String questionId;

    private Choice(Builder builder) {
        this.id = builder.id;
        this.content = builder.content;
        this.correct = builder.correct;
        this.questionId = builder.questionId;
    }

    public static class Builder {
        private String id;
        private String content;
        private boolean correct;
        private String questionId;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder questionId(String questionId) {
            this.questionId = questionId;
            return this;
        }

        public Builder correct(boolean correct) {
            this.correct = correct;
            return this;
        }

        public Choice build() {
            return new Choice(this);
        }
    }
}
