package com.michead.studybuddy.domain;

import com.michead.studybuddy.localization.StudyBuddyLocale;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.List;

/**
 * Request for educational content generation with locale support
 */
public record EducationalContentRequest(
        @NonNull String topic,
        @NonNull StudyBuddyLocale locale,
        String subject,
        String contentType,
        String targetAudience,
        List<String> learningObjectives,
        String difficulty,
        String additionalRequirements,
        @NonNull Instant timestamp
) {
    public EducationalContentRequest {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
        if (locale == null) {
            locale = StudyBuddyLocale.getDefault();
        }
    }

    public static EducationalContentRequest of(String topic, StudyBuddyLocale locale) {
        return new EducationalContentRequest(topic, locale, null, null, null, null, null, null, Instant.now());
    }

    public static EducationalContentRequest of(String topic) {
        return of(topic, StudyBuddyLocale.getDefault());
    }

    public StudyRequest toStudyRequest() {
        DifficultyLevel level = difficulty != null ? DifficultyLevel.fromCode(difficulty) : DifficultyLevel.INTERMEDIATE;
        return new StudyRequest(topic, locale, level, subject, null, learningObjectives, timestamp);
    }
}