package com.michead.template.cli;

import com.embabel.agent.api.common.OperationContext;
import com.michead.studybuddy.agent.StudyBuddyAgent;
import com.michead.studybuddy.domain.StudyRequest;
import com.michead.studybuddy.domain.StudySummary;
import com.michead.studybuddy.domain.DifficultyLevel;
import com.michead.studybuddy.localization.StudyBuddyLocale;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CLI command for generating topic summaries.
 */
@Component
@Command(
    name = "summarize",
    description = {
        "Generate a summary of a given topic",
        "Provides a concise overview with key concepts and takeaways"
    },
    mixinStandardHelpOptions = true,
    headerHeading = "StudyBuddy CLI - Summarize%n",
    synopsisHeading = "%nUsage: ",
    descriptionHeading = "%nDescription:%n",
    parameterListHeading = "%nParameters:%n",
    optionListHeading = "%nOptions:%n",
    footerHeading = "%nExamples:%n",
    footer = {
        "  studybuddy-cli summarize fotosintesis",
        "  studybuddy-cli summarize \"machine learning\" --lang EN --level ADVANCED",
        "  studybuddy-cli summarize \"inteligencia artificial\" --level BEGINNER"
    }
)
public class SummarizeCommand implements Runnable {

    @Parameters(
        index = "0",
        description = "The topic to summarize",
        paramLabel = "TOPIC"
    )
    private String topic;

    @Option(
        names = {"--lang", "--language"},
        description = "Language for the summary (valid values: ${COMPLETION-CANDIDATES})",
        defaultValue = "ES"
    )
    private Language language = Language.ES;

    @Option(
        names = {"--level"},
        description = "Complexity level for the summary (valid values: ${COMPLETION-CANDIDATES})",
        defaultValue = "INTERMEDIATE"
    )
    private Level level = Level.INTERMEDIATE;

    @Autowired
    private StudyBuddyAgent studyBuddyAgent;

    @Autowired
    private OperationContext operationContext;

    @Override
    public void run() {
        try {
            StudyBuddyLocale locale = language == Language.EN ? StudyBuddyLocale.ENGLISH : StudyBuddyLocale.SPANISH;
            DifficultyLevel difficultyLevel = DifficultyLevel.fromCode(level.getCode());
            
            StudyRequest request = StudyRequest.of(topic, locale, difficultyLevel);
            StudySummary result = studyBuddyAgent.summarizeTopic(request, operationContext);

            System.out.println(result.toString());
        } catch (Exception e) {
            System.err.println("Error generating summary: " + e.getMessage());
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

    public enum Level {
        BEGINNER("beginner", "Beginner"),
        INTERMEDIATE("intermediate", "Intermediate"),
        ADVANCED("advanced", "Advanced");

        private final String code;
        private final String displayName;

        Level(String code, String displayName) {
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