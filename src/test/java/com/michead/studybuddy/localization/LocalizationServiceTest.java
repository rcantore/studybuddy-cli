package com.michead.studybuddy.localization;

import freemarker.template.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(LocalizationServiceTest.TestConfig.class)
class LocalizationServiceTest {

    private LocalizationService localizationService;

    @TestConfiguration
    static class TestConfig {
        
        @Bean
        public Configuration freemarkerConfiguration() {
            Configuration config = new Configuration(Configuration.VERSION_2_3_32);
            config.setClassForTemplateLoading(this.getClass(), "/templates/prompts");
            config.setDefaultEncoding("UTF-8");
            return config;
        }
        
        @Bean
        public LocalizationConfig localizationConfig() {
            LocalizationConfig config = new LocalizationConfig();
            config.setDefaultLocale(StudyBuddyLocale.SPANISH);
            config.setFallbackToEnglish(true);
            return config;
        }
        
        @Bean
        public LocalizationService localizationService(Configuration freemarkerConfig, LocalizationConfig localizationConfig) {
            return new LocalizationService(freemarkerConfig, localizationConfig);
        }
    }

    @BeforeEach
    void setUp() {
        Configuration freemarkerConfig = new Configuration(Configuration.VERSION_2_3_32);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/prompts");
        freemarkerConfig.setDefaultEncoding("UTF-8");
        
        LocalizationConfig config = new LocalizationConfig();
        config.setDefaultLocale(StudyBuddyLocale.SPANISH);
        config.setFallbackToEnglish(true);
        
        localizationService = new LocalizationService(freemarkerConfig, config);
    }

    @Test
    void testGenerateResearchPromptSpanish() {
        String topic = "Inteligencia Artificial";
        String subject = "Ciencias de la Computación";
        String context = "Estudiante universitario";
        List<String> questions = Arrays.asList("¿Qué es el machine learning?", "¿Cómo funciona?");

        String prompt = localizationService.generateResearchPrompt(
            StudyBuddyLocale.SPANISH, topic, subject, context, questions
        );

        assertNotNull(prompt);
        assertTrue(prompt.contains("asistente de investigación académica"));
        assertTrue(prompt.contains(topic));
        assertTrue(prompt.contains(subject));
        assertTrue(prompt.contains("¿Qué es el machine learning?"));
    }

    @Test
    void testGenerateResearchPromptEnglish() {
        String topic = "Artificial Intelligence";
        String subject = "Computer Science";
        String context = "University student";
        List<String> questions = Arrays.asList("What is machine learning?", "How does it work?");

        String prompt = localizationService.generateResearchPrompt(
            StudyBuddyLocale.ENGLISH, topic, subject, context, questions
        );

        assertNotNull(prompt);
        assertTrue(prompt.contains("academic research assistant"));
        assertTrue(prompt.contains(topic));
        assertTrue(prompt.contains(subject));
        assertTrue(prompt.contains("What is machine learning?"));
    }

    @Test
    void testGenerateSummarizationPromptSpanish() {
        String content = "Este es el contenido para resumir...";
        String summaryType = "resumen ejecutivo";
        Integer maxLength = 200;

        String prompt = localizationService.generateSummarizationPrompt(
            StudyBuddyLocale.SPANISH, content, summaryType, maxLength, null
        );

        assertNotNull(prompt);
        assertTrue(prompt.contains("asistente de estudio"));
        assertTrue(prompt.contains(content));
        assertTrue(prompt.contains("200"));
    }

    @Test
    void testGenerateEducationalContentPromptEnglish() {
        String topic = "Quantum Computing";
        String subject = "Physics";
        List<String> objectives = Arrays.asList("Understand quantum bits", "Learn quantum gates");

        String prompt = localizationService.generateEducationalContentPrompt(
            StudyBuddyLocale.ENGLISH, topic, subject, null, null, objectives, null, null
        );

        assertNotNull(prompt);
        assertTrue(prompt.contains("expert educator"));
        assertTrue(prompt.contains(topic));
        assertTrue(prompt.contains("Understand quantum bits"));
    }

    @Test
    void testGetDefaultLocale() {
        StudyBuddyLocale defaultLocale = localizationService.getDefaultLocale();
        assertEquals(StudyBuddyLocale.SPANISH, defaultLocale);
    }
}