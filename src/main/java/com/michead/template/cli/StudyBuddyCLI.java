package com.michead.template.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import org.springframework.stereotype.Component;

/**
 * Main CLI command for StudyBuddy-CLI application.
 * Provides educational content generation through various subcommands.
 */
@Component
@Command(
    name = "studybuddy-cli",
    description = {
        "StudyBuddy CLI - Your AI-powered study companion",
        "Generate summaries, conduct research, and explain concepts with AI assistance"
    },
    subcommands = {
        SummarizeCommand.class,
        ResearchCommand.class,
        ExplainCommand.class,
        HelpCommand.class
    },
    mixinStandardHelpOptions = true,
    version = "1.0.0",
    headerHeading = "StudyBuddy CLI%n",
    synopsisHeading = "%nUsage: ",
    descriptionHeading = "%nDescription:%n",
    parameterListHeading = "%nParameters:%n",
    optionListHeading = "%nOptions:%n",
    commandListHeading = "%nCommands:%n",
    footerHeading = "%nExamples:%n",
    footer = {
        "  studybuddy-cli summarize fotosintesis",
        "  studybuddy-cli research \"quantum computing\" --depth DETAILED", 
        "  studybuddy-cli explain \"machine learning\" --lang EN",
        "  studybuddy-cli summarize \"inteligencia artificial\""
    }
)
public class StudyBuddyCLI implements Runnable {

    @Override
    public void run() {
        // When no subcommand is provided, show help
        CommandLine.usage(this, System.out);
    }
}