package com.michead.studybuddy.domain;

import com.michead.studybuddy.localization.StudyBuddyLocale;
import org.springframework.lang.NonNull;

import java.time.Instant;

/**
 * Request for concept explanation with formatting options
 */
public record ExplanationRequest(
        @NonNull String concept,
        @NonNull StudyBuddyLocale locale,
        @NonNull OutputFormat format,
        String context,
        @NonNull Instant timestamp
) {
    public ExplanationRequest {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
        if (locale == null) {
            locale = StudyBuddyLocale.getDefault();
        }
        if (format == null) {
            format = OutputFormat.MARKDOWN;
        }
    }

    public static ExplanationRequest of(String concept, StudyBuddyLocale locale, OutputFormat format) {
        return new ExplanationRequest(concept, locale, format, null, Instant.now());
    }

    public static ExplanationRequest of(String concept, StudyBuddyLocale locale) {
        return of(concept, locale, OutputFormat.MARKDOWN);
    }

    public static ExplanationRequest of(String concept) {
        return of(concept, StudyBuddyLocale.getDefault(), OutputFormat.MARKDOWN);
    }
}