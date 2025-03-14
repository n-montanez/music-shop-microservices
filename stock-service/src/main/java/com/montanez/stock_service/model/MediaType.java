package com.montanez.stock_service.model;

public enum MediaType {
    CD("cd"),
    ONE_LP("1lp"),
    VINYL_2LP("2lp");

    private final String value;

    MediaType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static MediaType fromValue(String value) {
        for (MediaType type : values()) {
            if (type.value.equalsIgnoreCase(value))
                return type;
        }
        throw new IllegalArgumentException("Unknown media type: " + value);
    }
}
