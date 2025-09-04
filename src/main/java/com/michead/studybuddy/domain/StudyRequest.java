package com.michead.studybuddy.domain;

import com.michead.studybuddy.localization.StudyBuddyLocale;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.List;

/**
 * Base record for study requests with locale support
 */
public record StudyRequest(
        @NonNull String topic,
        @NonNull StudyBuddyLocale locale,
        @NonNull DifficultyLevel level,
        String subject,
        String context,
        List<String> additionalParameters,
        @NonNull Instant timestamp
) {
    public StudyRequest {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
        if (locale == null) {
            locale = StudyBuddyLocale.getDefault();
        }
        if (level == null) {
            level = DifficultyLevel.INTERMEDIATE;
        }
    }

    public static StudyRequest of(String topic, StudyBuddyLocale locale, DifficultyLevel level) {
        return new StudyRequest(topic, locale, level, null, null, null, Instant.now());
    }

    public static StudyRequest of(String topic, StudyBuddyLocale locale) {
        return of(topic, locale, DifficultyLevel.INTERMEDIATE);
    }

    public static StudyRequest of(String topic) {
        return of(topic, StudyBuddyLocale.getDefault());
    }
}