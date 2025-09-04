package com.michead.studybuddy.localization;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Service for handling localized prompt generation using Freemarker templates
 */
@Service
public class LocalizationService {

    private final Configuration freemarkerConfig;
    private final LocalizationConfig localizationConfig;

    public LocalizationService(Configuration freemarkerConfig, LocalizationConfig localizationConfig) {
        this.freemarkerConfig = freemarkerConfig;
        this.localizationConfig = localizationConfig;
    }

    /**
     * Generate a localized prompt using the specified template and parameters
     * 
     * @param templateName Name of the template file (without .ftl extension)
     * @param locale The target locale
     * @param parameters Template parameters
     * @return Generated prompt text
     */
    public String generatePrompt(String templateName, StudyBuddyLocale locale, Map<String, Object> parameters) {
        try {
            Template template = getTemplate(templateName, locale);
            StringWriter writer = new StringWriter();
            template.process(parameters, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new LocalizationException("Failed to generate prompt for template: " + templateName, e);
        }
    }

    /**
     * Generate a research prompt with the specified parameters
     */
    public String generateResearchPrompt(StudyBuddyLocale locale, String topic, String subject, 
                                       String context, java.util.List<String> specificQuestions) {
        Map<String, Object> params = Map.of(
            "topic", topic,
            "subject", subject != null ? subject : "",
            "context", context != null ? context : "",
            "specificQuestions", specificQuestions != null ? specificQuestions : java.util.Collections.emptyList()
        );
        return generatePrompt("research", locale, params);
    }

    /**
     * Generate a summarization prompt with the specified parameters
     */
    public String generateSummarizationPrompt(StudyBuddyLocale locale, String content, String summaryType,
                                            Integer maxLength, java.util.List<String> focusAreas) {
        Map<String, Object> params = Map.of(
            "content", content,
            "summaryType", summaryType != null ? summaryType : "",
            "maxLength", maxLength != null ? maxLength : "",
            "focusAreas", focusAreas != null ? focusAreas : java.util.Collections.emptyList()
        );
        return generatePrompt("summarization", locale, params);
    }

    /**
     * Generate an educational content prompt with the specified parameters
     */
    public String generateEducationalContentPrompt(StudyBuddyLocale locale, String topic, String subject,
                                                  String contentType, String targetAudience,
                                                  java.util.List<String> learningObjectives, String difficulty,
                                                  String additionalRequirements) {
        Map<String, Object> params = Map.of(
            "topic", topic,
            "subject", subject != null ? subject : "",
            "contentType", contentType != null ? contentType : "",
            "targetAudience", targetAudience != null ? targetAudience : "",
            "learningObjectives", learningObjectives != null ? learningObjectives : java.util.Collections.emptyList(),
            "difficulty", difficulty != null ? difficulty : "",
            "additionalRequirements", additionalRequirements != null ? additionalRequirements : ""
        );
        return generatePrompt("educational_content", locale, params);
    }

    /**
     * Get the appropriate template, with fallback logic
     */
    private Template getTemplate(String templateName, StudyBuddyLocale locale) throws IOException {
        String templatePath = locale.getCode() + "/" + templateName + ".ftl";
        
        try {
            return freemarkerConfig.getTemplate(templatePath);
        } catch (IOException e) {
            // Fallback to English if configured and not already English
            if (localizationConfig.isFallbackToEnglish() && locale != StudyBuddyLocale.ENGLISH) {
                String fallbackPath = StudyBuddyLocale.ENGLISH.getCode() + "/" + templateName + ".ftl";
                try {
                    return freemarkerConfig.getTemplate(fallbackPath);
                } catch (IOException fallbackException) {
                    throw new LocalizationException(
                        "Template not found for locale " + locale.getCode() + " and fallback failed", 
                        fallbackException
                    );
                }
            }
            throw new LocalizationException("Template not found: " + templatePath, e);
        }
    }

    /**
     * Get the current default locale
     */
    public StudyBuddyLocale getDefaultLocale() {
        return localizationConfig.getDefaultLocale();
    }
}