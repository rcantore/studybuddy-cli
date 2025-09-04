package com.michead;

import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.config.annotation.LoggingThemes;
import com.michead.template.cli.StudyBuddyCLI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import picocli.CommandLine;
import picocli.spring.PicocliSpringFactory;

@SpringBootApplication(excludeName = {
    "com.embabel.agent.shell.*",
    "org.springframework.shell.*",
    "org.springframework.ai.bedrock.*",
    "org.springframework.ai.model.bedrock.*"
})
@EnableAgents(loggingTheme = LoggingThemes.STAR_WARS)
public class StudyBuddyApplication implements CommandLineRunner {
    
    private final ApplicationContext applicationContext;
    private final StudyBuddyCLI studyBuddyCLI;

    public StudyBuddyApplication(ApplicationContext applicationContext, StudyBuddyCLI studyBuddyCLI) {
        this.applicationContext = applicationContext;
        this.studyBuddyCLI = studyBuddyCLI;
    }

    public static void main(String[] args) {
        // Disable Spring Boot banner and web server for clean CLI output
        System.setProperty("spring.main.banner-mode", "off");
        System.setProperty("spring.main.web-application-type", "none");
        System.setProperty("spring.main.log-startup-info", "false");
        
        // Try to reduce some logging
        System.setProperty("logging.level.org.springframework.jcl", "OFF");
        
        SpringApplication app = new SpringApplication(StudyBuddyApplication.class);
        app.setLogStartupInfo(false);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Skip if no arguments provided (Spring Boot startup)
        if (args.length == 0) {
            return;
        }
        
        // Create PicoCLI command line with Spring factory for dependency injection
        CommandLine commandLine = new CommandLine(studyBuddyCLI, new PicocliSpringFactory(applicationContext));
        
        // Configure error handling
        commandLine.setExecutionExceptionHandler((ex, cmd, parseResult) -> {
            System.err.println("Error: " + ex.getMessage());
            if (ex.getCause() != null) {
                System.err.println("Cause: " + ex.getCause().getMessage());
            }
            return 1;
        });

        // Execute the command and exit immediately
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}