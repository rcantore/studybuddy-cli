package com.michead.studybuddy.domain;

/**
 * Represents different difficulty levels for educational content
 */
public enum DifficultyLevel {
    BEGINNER("beginner", "Beginner"),
    INTERMEDIATE("intermediate", "Intermediate"), 
    ADVANCED("advanced", "Advanced");

    private final String code;
    private final String displayName;

    DifficultyLevel(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static DifficultyLevel fromCode(String code) {
        for (DifficultyLevel level : values()) {
            if (level.code.equalsIgnoreCase(code)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown difficulty level: " + code);
    }
}