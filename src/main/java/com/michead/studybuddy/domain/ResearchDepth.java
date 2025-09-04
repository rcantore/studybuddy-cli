package com.michead.studybuddy.domain;

/**
 * Represents different depths of research to perform
 */
public enum ResearchDepth {
    BASIC("basic", "Basic"),
    DETAILED("detailed", "Detailed");

    private final String code;
    private final String displayName;

    ResearchDepth(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ResearchDepth fromCode(String code) {
        for (ResearchDepth depth : values()) {
            if (depth.code.equalsIgnoreCase(code)) {
                return depth;
            }
        }
        throw new IllegalArgumentException("Unknown research depth: " + code);
    }
}