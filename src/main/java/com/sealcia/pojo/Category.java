package com.sealcia.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Category {
    private int id;
    private String name;

    @Override
    public String toString() {
        return this.name;
    }
}
