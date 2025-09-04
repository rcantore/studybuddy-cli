package com.michead.studybuddy.localization;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Configuration class for managing localization settings in StudyBuddy CLI
 */
@Component
@ConfigurationProperties(prefix = "studybuddy.localization")
public class LocalizationConfig {

    private StudyBuddyLocale defaultLocale = StudyBuddyLocale.getDefault();
    private String templatePath = "/templates/prompts";
    private boolean fallbackToEnglish = true;

    public StudyBuddyLocale getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(StudyBuddyLocale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public boolean isFallbackToEnglish() {
        return fallbackToEnglish;
    }

    public void setFallbackToEnglish(boolean fallbackToEnglish) {
        this.fallbackToEnglish = fallbackToEnglish;
    }

    /**
     * Creates and configures a Freemarker Configuration bean for template processing
     */
    @Bean
    @Primary
    public Configuration freemarkerConfiguration() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        
        // Set the template loading from classpath
        cfg.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), templatePath);
        
        // Set the preferred charset template files are stored in
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.name());
        
        // Sets how errors will appear
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        
        // Don't log exceptions inside FreeMarker
        cfg.setLogTemplateExceptions(false);
        
        // Wrap unchecked exceptions thrown during template processing
        cfg.setWrapUncheckedExceptions(true);
        
        return cfg;
    }
}