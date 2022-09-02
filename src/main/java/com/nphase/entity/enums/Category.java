package com.nphase.entity.enums;

public enum Category {
    DRINKS("drinks"),
    FOOD("food");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
