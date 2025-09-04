package com.michead.studybuddy.domain;

/**
 * Represents different output formats for content generation
 */
public enum OutputFormat {
    PLAIN("plain", "Plain Text"),
    MARKDOWN("markdown", "Markdown");

    private final String code;
    private final String displayName;

    OutputFormat(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static OutputFormat fromCode(String code) {
        for (OutputFormat format : values()) {
            if (format.code.equalsIgnoreCase(code)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown output format: " + code);
    }
}