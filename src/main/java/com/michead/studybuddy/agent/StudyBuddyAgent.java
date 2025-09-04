package com.michead.studybuddy.agent;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Export;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.prompt.persona.RoleGoalBackstory;
import com.embabel.common.ai.model.LlmOptions;
import com.michead.studybuddy.domain.*;
import com.michead.studybuddy.localization.LocalizationService;
import com.michead.studybuddy.localization.StudyBuddyLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Main StudyBuddy agent that provides educational content generation
 * with multi-language support using Claude 3.5 Haiku
 */
@Agent(description = "Generate educational content and study materials for students")
@Component
public class StudyBuddyAgent {
    
    private static final Logger logger = LoggerFactory.getLogger(StudyBuddyAgent.class);

    private final LocalizationService localizationService;

    // Personas for different educational tasks
    private static final RoleGoalBackstory EDUCATOR = RoleGoalBackstory
            .withRole("Educational Content Specialist")
            .andGoal("Generate clear, engaging educational content adapted to student level")
            .andBackstory("Expert educator with 15+ years experience in creating educational materials for diverse learning levels and multilingual environments");

    private static final RoleGoalBackstory RESEARCHER = RoleGoalBackstory
            .withRole("Academic Researcher")
            .andGoal("Conduct thorough research and provide accurate, well-structured information")
            .andBackstory("PhD-level researcher specialized in information synthesis and academic content creation");

    private static final RoleGoalBackstory EXPLAINER = RoleGoalBackstory
            .withRole("Concept Clarifier")
            .andGoal("Explain complex concepts in simple, understandable terms")
            .andBackstory("Science communicator with expertise in making complex topics accessible to learners");

    public StudyBuddyAgent(LocalizationService localizationService) {
        this.localizationService = localizationService;
    }

    /**
     * Generate a comprehensive summary of a topic with specified difficulty level
     */
    @Action
    public StudySummary summarizeTopic(StudyRequest request, OperationContext context) {
        logger.info("Summarizing topic '{}' for level '{}' in locale '{}'", 
                   request.topic(), request.level(), request.locale().getCode());
        
        String prompt = localizationService.generateEducationalContentPrompt(
            request.locale(),
            request.topic(),
            null, // subject - will be auto-determined
            "summary",
            getLevelDescription(request.level(), request.locale()),
            Collections.emptyList(),
            request.level().name().toLowerCase(),
            "Focus on key concepts, provide examples, and include practical applications"
        );

        StudySummaryResponse response = context.ai()
            .withLlm(createOptimizedLlmOptions())
            .withPromptContributor(EDUCATOR)
            .createObject(prompt, StudySummaryResponse.class);

        return new StudySummary(
            request.topic(),
            response.title(),
            response.summary(),
            response.keyConcepts(),
            response.examples(),
            response.practicalApplications(),
            request.level(),
            request.locale()
        );
    }

    /**
     * Conduct comprehensive research on a topic with specified depth
     */
    @Action
    public ResearchResult researchTopic(ResearchRequest request, OperationContext context) {
        logger.info("Researching topic '{}' with depth '{}' in locale '{}'", 
                   request.topic(), request.depth(), request.locale().getCode());

        String prompt = localizationService.generateResearchPrompt(
            request.locale(),
            request.topic(),
            null,
            getDepthDescription(request.depth(), request.locale()),
            Collections.emptyList()
        );

        ResearchResponse response = context.ai()
            .withLlm(createOptimizedLlmOptions())
            .withPromptContributor(RESEARCHER)
            .createObject(prompt, ResearchResponse.class);

        return new ResearchResult(
            request.topic(),
            response.overview(),
            response.keyFindings(),
            response.sources(),
            response.relatedTopics(),
            request.depth(),
            request.locale()
        );
    }

    /**
     * Explain a concept in detail with specified format
     */
    @AchievesGoal(
        description = "Educational concept explanation has been generated and formatted",
        export = @Export(remote = true, name = "explainConcept")
    )
    @Action
    public ConceptExplanation explainConcept(ExplanationRequest request, OperationContext context) {
        logger.info("Explaining concept '{}' in format '{}' and locale '{}'", 
                   request.concept(), request.format(), request.locale().getCode());

        String formatInstructions = getFormatInstructions(request.format(), request.locale());
        
        String prompt = localizationService.generateEducationalContentPrompt(
            request.locale(),
            request.concept(),
            null,
            "explanation",
            "general audience",
            Collections.emptyList(),
            "intermediate",
            formatInstructions
        );

        ConceptResponse response = context.ai()
            .withLlm(createOptimizedLlmOptions())
            .withPromptContributor(EXPLAINER)
            .createObject(prompt, ConceptResponse.class);

        return new ConceptExplanation(
            request.concept(),
            response.explanation(),
            response.examples(),
            response.relatedConcepts(),
            request.format(),
            request.locale()
        );
    }

    /**
     * Create optimized LLM options for Claude 3.5 Haiku
     */
    private LlmOptions createOptimizedLlmOptions() {
        return LlmOptions.withAutoLlm()
            .withTemperature(0.3)
            .withMaxTokens(2000);
    }

    /**
     * Get level description based on locale
     */
    private String getLevelDescription(DifficultyLevel level, StudyBuddyLocale locale) {
        boolean isSpanish = locale == StudyBuddyLocale.SPANISH;
        
        return switch (level) {
            case BEGINNER -> isSpanish ? "principiante, con explicaciones básicas y ejemplos simples" 
                                      : "beginner level with basic explanations and simple examples";
            case INTERMEDIATE -> isSpanish ? "intermedio, con mayor profundidad y ejemplos prácticos" 
                                          : "intermediate level with more depth and practical examples";
            case ADVANCED -> isSpanish ? "avanzado, con análisis detallado y conceptos complejos" 
                                      : "advanced level with detailed analysis and complex concepts";
        };
    }

    /**
     * Get depth description based on locale
     */
    private String getDepthDescription(ResearchDepth depth, StudyBuddyLocale locale) {
        boolean isSpanish = locale == StudyBuddyLocale.SPANISH;
        
        return switch (depth) {
            case BASIC -> isSpanish ? "Proporciona una visión general básica con puntos clave"
                                   : "Provide a basic overview with key points";
            case DETAILED -> isSpanish ? "Incluye análisis detallado, múltiples perspectivas y fuentes académicas"
                                      : "Include detailed analysis, multiple perspectives, and academic sources";
        };
    }

    /**
     * Get format instructions based on locale
     */
    private String getFormatInstructions(OutputFormat format, StudyBuddyLocale locale) {
        boolean isSpanish = locale == StudyBuddyLocale.SPANISH;
        
        return switch (format) {
            case MARKDOWN -> isSpanish ? "Formatear la respuesta usando sintaxis Markdown con títulos, listas y énfasis apropiados"
                                      : "Format the response using Markdown syntax with proper headings, lists, and emphasis";
            case PLAIN -> isSpanish ? "Usar texto plano sin formato especial, manteniendo la estructura clara"
                                   : "Use plain text without special formatting, maintaining clear structure";
        };
    }

    // Response classes for structured LLM output
    public record StudySummaryResponse(
        String title,
        String summary,
        List<String> keyConcepts,
        List<String> examples,
        List<String> practicalApplications
    ) {}

    public record ResearchResponse(
        String overview,
        List<String> keyFindings,
        List<String> sources,
        List<String> relatedTopics
    ) {}

    public record ConceptResponse(
        String explanation,
        List<String> examples,
        List<String> relatedConcepts
    ) {}
}