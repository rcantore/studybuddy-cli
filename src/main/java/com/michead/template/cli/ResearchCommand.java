package com.michead.template.cli;

import com.embabel.agent.api.common.OperationContext;
import com.michead.studybuddy.agent.StudyBuddyAgent;
import com.michead.studybuddy.domain.ResearchRequest;
import com.michead.studybuddy.domain.ResearchResult;
import com.michead.studybuddy.domain.ResearchDepth;
import com.michead.studybuddy.localization.StudyBuddyLocale;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CLI command for conducting research on topics.
 */
@Component
@Command(
    name = "research",
    description = {
        "Conduct comprehensive research on a given topic",
        "Provides in-depth analysis with multiple perspectives and sources"
    },
    mixinStandardHelpOptions = true,
    headerHeading = "StudyBuddy CLI - Research%n",
    synopsisHeading = "%nUsage: ",
    descriptionHeading = "%nDescription:%n",
    parameterListHeading = "%nParameters:%n",
    optionListHeading = "%nOptions:%n",
    footerHeading = "%nExamples:%n",
    footer = {
        "  studybuddy-cli research \"quantum computing\" --lang en --depth detailed",
        "  studybuddy-cli research \"inteligencia artificial\" --lang es --depth basic"
    }
)
public class ResearchCommand implements Runnable {

    @Parameters(
        index = "0",
        description = "The topic to research",
        paramLabel = "TOPIC"
    )
    private String topic;

    @Option(
        names = {"--lang", "--language"},
        description = "Language for the research (valid values: ${COMPLETION-CANDIDATES})",
        defaultValue = "ES"
    )
    private Language language = Language.ES;

    @Option(
        names = {"--depth"},
        description = "Research depth level (valid values: ${COMPLETION-CANDIDATES})",
        defaultValue = "DETAILED"
    )
    private Depth depth = Depth.DETAILED;

    @Autowired
    private StudyBuddyAgent studyBuddyAgent;

    @Autowired
    private OperationContext operationContext;

    @Override
    public void run() {
        try {
            StudyBuddyLocale locale = language == Language.EN ? StudyBuddyLocale.ENGLISH : StudyBuddyLocale.SPANISH;
            ResearchDepth researchDepth = ResearchDepth.fromCode(depth.getCode());
            
            ResearchRequest request = ResearchRequest.of(topic, locale, researchDepth);
            ResearchResult result = studyBuddyAgent.researchTopic(request, operationContext);

            System.out.println(result.toString());
        } catch (Exception e) {
            System.err.println("Error conducting research: " + e.getMessage());
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

    public enum Depth {
        BASIC("basic", "Basic Overview"),
        DETAILED("detailed", "Detailed Analysis");

        private final String code;
        private final String displayName;

        Depth(String code, String displayName) {
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