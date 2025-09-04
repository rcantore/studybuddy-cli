package com.michead.studybuddy.domain;

import com.michead.studybuddy.localization.StudyBuddyLocale;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Result of a study topic summarization
 */
public record StudySummary(
        @NonNull String topic,
        @NonNull String title,
        @NonNull String summary,
        @NonNull List<String> keyConcepts,
        @NonNull List<String> examples,
        @NonNull List<String> practicalApplications,
        @NonNull DifficultyLevel level,
        @NonNull StudyBuddyLocale locale
) {
    public StudySummary {
        if (keyConcepts == null) {
            keyConcepts = List.of();
        }
        if (examples == null) {
            examples = List.of();
        }
        if (practicalApplications == null) {
            practicalApplications = List.of();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("# ").append(title).append("\n\n");
        
        // Localized field names
        if (locale == StudyBuddyLocale.SPANISH) {
            sb.append("**Tema:** ").append(topic).append("\n");
            sb.append("**Nivel:** ").append(level.getDisplayName()).append("\n");
            sb.append("**Idioma:** ").append(locale.getDisplayName()).append("\n\n");
            sb.append("## Resumen\n");
        } else {
            sb.append("**Topic:** ").append(topic).append("\n");
            sb.append("**Level:** ").append(level.getDisplayName()).append("\n");
            sb.append("**Language:** ").append(locale.getDisplayName()).append("\n\n");
            sb.append("## Summary\n");
        }
        
        sb.append(summary).append("\n\n");
        
        if (!keyConcepts.isEmpty()) {
            if (locale == StudyBuddyLocale.SPANISH) {
                sb.append("## Conceptos Clave\n");
            } else {
                sb.append("## Key Concepts\n");
            }
            keyConcepts.forEach(concept -> sb.append("- ").append(concept).append("\n"));
            sb.append("\n");
        }
        
        if (!examples.isEmpty()) {
            if (locale == StudyBuddyLocale.SPANISH) {
                sb.append("## Ejemplos\n");
            } else {
                sb.append("## Examples\n");
            }
            examples.forEach(example -> sb.append("- ").append(example).append("\n"));
            sb.append("\n");
        }
        
        if (!practicalApplications.isEmpty()) {
            if (locale == StudyBuddyLocale.SPANISH) {
                sb.append("## Aplicaciones Prácticas\n");
            } else {
                sb.append("## Practical Applications\n");
            }
            practicalApplications.forEach(application -> sb.append("- ").append(application).append("\n"));
        }
        
        return sb.toString();
    }
}