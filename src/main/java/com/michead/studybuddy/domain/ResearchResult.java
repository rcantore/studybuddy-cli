package com.michead.studybuddy.domain;

import com.michead.studybuddy.localization.StudyBuddyLocale;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Result of a research operation
 */
public record ResearchResult(
        @NonNull String topic,
        @NonNull String overview,
        @NonNull List<String> keyFindings,
        @NonNull List<String> sources,
        @NonNull List<String> relatedTopics,
        @NonNull ResearchDepth depth,
        @NonNull StudyBuddyLocale locale
) {
    public ResearchResult {
        if (keyFindings == null) {
            keyFindings = List.of();
        }
        if (sources == null) {
            sources = List.of();
        }
        if (relatedTopics == null) {
            relatedTopics = List.of();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Localized field names
        if (locale == StudyBuddyLocale.SPANISH) {
            sb.append("# Investigación: ").append(topic).append("\n\n");
            sb.append("**Profundidad:** ").append(depth.getDisplayName()).append("\n");
            sb.append("**Idioma:** ").append(locale.getDisplayName()).append("\n\n");
            sb.append("## Resumen General\n");
        } else {
            sb.append("# Research: ").append(topic).append("\n\n");
            sb.append("**Depth:** ").append(depth.getDisplayName()).append("\n");
            sb.append("**Language:** ").append(locale.getDisplayName()).append("\n\n");
            sb.append("## Overview\n");
        }
        
        sb.append(overview).append("\n\n");
        
        if (!keyFindings.isEmpty()) {
            if (locale == StudyBuddyLocale.SPANISH) {
                sb.append("## Hallazgos Principales\n");
            } else {
                sb.append("## Key Findings\n");
            }
            keyFindings.forEach(finding -> sb.append("- ").append(finding).append("\n"));
            sb.append("\n");
        }
        
        if (!sources.isEmpty()) {
            if (locale == StudyBuddyLocale.SPANISH) {
                sb.append("## Fuentes\n");
            } else {
                sb.append("## Sources\n");
            }
            sources.forEach(source -> sb.append("- ").append(source).append("\n"));
            sb.append("\n");
        }
        
        if (!relatedTopics.isEmpty()) {
            if (locale == StudyBuddyLocale.SPANISH) {
                sb.append("## Temas Relacionados\n");
            } else {
                sb.append("## Related Topics\n");
            }
            relatedTopics.forEach(topic -> sb.append("- ").append(topic).append("\n"));
        }
        
        return sb.toString();
    }
}