package com.michead.studybuddy.domain;

import com.michead.studybuddy.localization.StudyBuddyLocale;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.List;

/**
 * Request for research assistance with locale support
 */
public record ResearchRequest(
        @NonNull String topic,
        @NonNull StudyBuddyLocale locale,
        @NonNull ResearchDepth depth,
        String subject,
        String context,
        List<String> specificQuestions,
        @NonNull Instant timestamp
) {
    public ResearchRequest {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
        if (locale == null) {
            locale = StudyBuddyLocale.getDefault();
        }
        if (depth == null) {
            depth = ResearchDepth.BASIC;
        }
    }

    public static ResearchRequest of(String topic, StudyBuddyLocale locale, ResearchDepth depth) {
        return new ResearchRequest(topic, locale, depth, null, null, null, Instant.now());
    }

    public static ResearchRequest of(String topic, StudyBuddyLocale locale) {
        return of(topic, locale, ResearchDepth.BASIC);
    }

    public static ResearchRequest of(String topic) {
        return of(topic, StudyBuddyLocale.getDefault());
    }

    public StudyRequest toStudyRequest() {
        return new StudyRequest(topic, locale, DifficultyLevel.INTERMEDIATE, subject, context, specificQuestions, timestamp);
    }
}