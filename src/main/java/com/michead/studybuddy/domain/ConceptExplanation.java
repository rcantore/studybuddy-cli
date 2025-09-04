package com.michead.studybuddy.domain;

import com.michead.studybuddy.localization.StudyBuddyLocale;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Result of a concept explanation
 */
public record ConceptExplanation(
        @NonNull String concept,
        @NonNull String explanation,
        @NonNull List<String> examples,
        @NonNull List<String> relatedConcepts,
        @NonNull OutputFormat format,
        @NonNull StudyBuddyLocale locale
) {
    public ConceptExplanation {
        if (examples == null) {
            examples = List.of();
        }
        if (relatedConcepts == null) {
            relatedConcepts = List.of();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        if (format == OutputFormat.MARKDOWN) {
            sb.append("# ").append(concept).append("\n\n");
            
            // Localized field names for markdown format
            if (locale == StudyBuddyLocale.SPANISH) {
                sb.append("**Idioma:** ").append(locale.getDisplayName()).append("\n\n");
                sb.append("## Explicación\n");
            } else {
                sb.append("**Language:** ").append(locale.getDisplayName()).append("\n\n");
                sb.append("## Explanation\n");
            }
            
            sb.append(explanation).append("\n\n");
            
            if (!examples.isEmpty()) {
                if (locale == StudyBuddyLocale.SPANISH) {
                    sb.append("## Ejemplos\n");
                } else {
                    sb.append("## Examples\n");
                }
                examples.forEach(example -> sb.append("- ").append(example).append("\n"));
                sb.append("\n");
            }
            
            if (!relatedConcepts.isEmpty()) {
                if (locale == StudyBuddyLocale.SPANISH) {
                    sb.append("## Conceptos Relacionados\n");
                } else {
                    sb.append("## Related Concepts\n");
                }
                relatedConcepts.forEach(concept -> sb.append("- ").append(concept).append("\n"));
            }
        } else {
            // Text format with localization
            sb.append(concept.toUpperCase()).append("\n");
            sb.append("=".repeat(concept.length())).append("\n\n");
            
            if (locale == StudyBuddyLocale.SPANISH) {
                sb.append("Idioma: ").append(locale.getDisplayName()).append("\n\n");
                sb.append("EXPLICACIÓN\n");
                sb.append("-----------\n");
            } else {
                sb.append("Language: ").append(locale.getDisplayName()).append("\n\n");
                sb.append("EXPLANATION\n");
                sb.append("-----------\n");
            }
            
            sb.append(explanation).append("\n\n");
            
            if (!examples.isEmpty()) {
                if (locale == StudyBuddyLocale.SPANISH) {
                    sb.append("EJEMPLOS\n");
                    sb.append("--------\n");
                } else {
                    sb.append("EXAMPLES\n");
                    sb.append("--------\n");
                }
                examples.forEach(example -> sb.append("* ").append(example).append("\n"));
                sb.append("\n");
            }
            
            if (!relatedConcepts.isEmpty()) {
                if (locale == StudyBuddyLocale.SPANISH) {
                    sb.append("CONCEPTOS RELACIONADOS\n");
                    sb.append("----------------------\n");
                } else {
                    sb.append("RELATED CONCEPTS\n");
                    sb.append("----------------\n");
                }
                relatedConcepts.forEach(concept -> sb.append("* ").append(concept).append("\n"));
            }
        }
        
        return sb.toString();
    }
}