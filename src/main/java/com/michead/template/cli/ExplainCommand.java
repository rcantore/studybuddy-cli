package com.michead.template.cli;

import com.embabel.agent.api.common.OperationContext;
import com.michead.studybuddy.agent.StudyBuddyAgent;
import com.michead.studybuddy.domain.ExplanationRequest;
import com.michead.studybuddy.domain.ConceptExplanation;
import com.michead.studybuddy.domain.OutputFormat;
import com.michead.studybuddy.localization.StudyBuddyLocale;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CLI command for explaining concepts.
 */
@Component
@Command(
    name = "explain",
    description = {
        "Explain a concept or term in detail",
        "Provides clear explanations with examples and context"
    },
    mixinStandardHelpOptions = true,
    headerHeading = "StudyBuddy CLI - Explain%n",
    synopsisHeading = "%nUsage: ",
    descriptionHeading = "%nDescription:%n",
    parameterListHeading = "%nParameters:%n",
    optionListHeading = "%nOptions:%n",
    footerHeading = "%nExamples:%n",
    footer = {
        "  studybuddy-cli explain \"photosynthesis\" --lang en --format markdown",
        "  studybuddy-cli explain \"gravitación\" --lang es --format plain"
    }
)
public class ExplainCommand implements Runnable {

    @Parameters(
        index = "0",
        description = "The concept to explain",
        paramLabel = "CONCEPT"
    )
    private String concept;

    @Option(
        names = {"--lang", "--language"},
        description = "Language for the explanation (valid values: ${COMPLETION-CANDIDATES})",
        defaultValue = "ES"
    )
    private Language language = Language.ES;

    @Option(
        names = {"--format"},
        description = "Output format (valid values: ${COMPLETION-CANDIDATES})",
        defaultValue = "MARKDOWN"
    )
    private Format format = Format.MARKDOWN;

    @Autowired
    private StudyBuddyAgent studyBuddyAgent;

    @Autowired
    private OperationContext operationContext;

    @Override
    public void run() {
        try {
            StudyBuddyLocale locale = language == Language.EN ? StudyBuddyLocale.ENGLISH : StudyBuddyLocale.SPANISH;
            OutputFormat outputFormat = OutputFormat.fromCode(format.getCode());
            
            ExplanationRequest request = ExplanationRequest.of(concept, locale, outputFormat);
            ConceptExplanation result = studyBuddyAgent.explainConcept(request, operationContext);

            System.out.println(result.toString());
        } catch (Exception e) {
            System.err.println("Error explaining concept: " + e.getMessage());
            System.exit(1);
        }
    }

    public enum Language {
        EN("en", "English"),
        ES("es", "Español");

        private final String code;
        private final String displayName;

        Language(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public String getCode() {
            return code;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum Format {
        MARKDOWN("markdown", "Markdown"),
        PLAIN("plain", "Plain Text");

        private final String code;
        private final String displayName;

        Format(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public String getCode() {
            return code;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}