package com.michead.studybuddy.domain;

import com.michead.studybuddy.localization.StudyBuddyLocale;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.List;

/**
 * Request for content summarization with locale support
 */
public record SummarizationRequest(
        @NonNull String content,
        @NonNull StudyBuddyLocale locale,
        String summaryType,
        Integer maxLength,
        List<String> focusAreas,
        @NonNull Instant timestamp
) {
    public SummarizationRequest {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
        if (locale == null) {
            locale = StudyBuddyLocale.getDefault();
        }
    }

    public static SummarizationRequest of(String content, StudyBuddyLocale locale) {
        return new SummarizationRequest(content, locale, null, null, null, Instant.now());
    }

    public static SummarizationRequest of(String content) {
        return of(content, StudyBuddyLocale.getDefault());
    }
}