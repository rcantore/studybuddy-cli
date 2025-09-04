# StudyBuddy CLI Localization Guide

This document explains how localization works in StudyBuddy CLI, how to customize it, and how to add new languages.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Supported Languages](#supported-languages)
- [Language Detection](#language-detection)
- [Template System](#template-system)
- [Adding New Languages](#adding-new-languages)
- [Customizing Templates](#customizing-templates)
- [Configuration](#configuration)
- [Testing Localization](#testing-localization)

## Overview

StudyBuddy CLI provides comprehensive multilingual support with:

- **Automatic language detection** based on configuration
- **Template-driven prompts** using Freemarker
- **Fallback system** to English when translations are missing
- **Consistent localization** across all CLI output
- **Easy extensibility** for new languages

## Architecture

### Core Components

The localization system consists of several key components:

```
Localization System
├── StudyBuddyLocale          # Language enumeration
├── LocalizationService       # Core localization service
├── LocalizationConfig        # Configuration settings
└── Freemarker Templates      # Language-specific prompts
```

### Data Flow

1. **User Input**: Command with optional `--lang` parameter
2. **Language Detection**: Determine target language (user preference or default)
3. **Template Selection**: Choose appropriate Freemarker template
4. **Prompt Generation**: Generate localized prompt for AI
5. **Output Formatting**: Format response with localized labels

## Supported Languages

Currently supported languages:

| Language | Code | Locale | Status |
|----------|------|--------|---------|
| Spanish  | ES   | es     | Full Support |
| English  | EN   | en     | Full Support |

### Language Features

**Spanish (ES) - Default**
- Complete template coverage
- Localized field names and labels
- Natural Spanish AI responses
- Cultural context awareness

**English (EN)**
- Complete template coverage
- Professional English output
- Technical terminology focus
- International context

## Language Detection

### Default Language

The system defaults to Spanish (`ES`) based on the configuration:

```properties
studybuddy.default-locale=es
```

### Language Selection Priority

1. **Command Line**: `--lang EN` (highest priority)
2. **Environment Variable**: `STUDYBUDDY_DEFAULT_LOCALE=en`
3. **Application Config**: `studybuddy.default-locale=es`
4. **System Default**: Spanish (`ES`) (fallback)

### Examples

```bash
# Use default language (Spanish)
./studybuddy.sh summarize "machine learning"

# Force English
./studybuddy.sh summarize "machine learning" --lang EN

# Force Spanish (explicit)
./studybuddy.sh summarize "machine learning" --lang ES
```

## Template System

### Template Structure

Templates are organized by language in the resources directory:

```
src/main/resources/templates/prompts/
├── es/                           # Spanish templates
│   ├── educational_content.ftl   # Educational content generation
│   ├── research.ftl              # Academic research
│   └── summarization.ftl         # Content summarization
└── en/                           # English templates
    ├── educational_content.ftl   # Educational content generation
    ├── research.ftl              # Academic research
    └── summarization.ftl         # Content summarization
```

### Template Types

**1. educational_content.ftl**
- Used for: `summarize` and `explain` commands
- Purpose: Generate educational explanations and summaries
- Variables: `topic`, `subject`, `contentType`, `targetAudience`, `learningObjectives`, `difficulty`, `additionalRequirements`

**2. research.ftl**
- Used for: `research` command
- Purpose: Conduct academic research
- Variables: `topic`, `subject`, `context`, `specificQuestions`

**3. summarization.ftl**
- Used for: Content summarization (future feature)
- Purpose: Summarize existing content
- Variables: `content`, `summaryType`, `maxLength`, `focusAreas`

### Template Variables

Common variables available in templates:

| Variable | Type | Description |
|----------|------|-------------|
| `topic` | String | Main topic or concept |
| `subject` | String | Academic subject area |
| `contentType` | String | Type of content (summary, explanation) |
| `targetAudience` | String | Intended audience |
| `learningObjectives` | List | Learning goals |
| `difficulty` | String | Difficulty level |
| `additionalRequirements` | String | Special requirements |

## Adding New Languages

### Step 1: Add Language Enum

Add the new language to `StudyBuddyLocale.java`:

```java
public enum StudyBuddyLocale {
    SPANISH("es", "Español"),
    ENGLISH("en", "English"),
    FRENCH("fr", "Français");  // New language
    
    // ... rest of the enum
}
```

### Step 2: Create Template Directory

Create a new directory for the language templates:

```
src/main/resources/templates/prompts/fr/
├── educational_content.ftl
├── research.ftl
└── summarization.ftl
```

### Step 3: Translate Templates

Copy existing templates and translate them. For example, `fr/educational_content.ftl`:

```freemarker
Vous êtes un éducateur expert spécialisé en ${subject!"études générales"}.

Votre objectif est de générer du contenu éducatif attrayant, informatif et approprié pour le niveau de l'étudiant.

## Instructions IMPORTANTES:
- VOUS DEVEZ répondre entièrement en français, sans exception
- Créez du contenu éducatif clair et bien structuré
- Adaptez le niveau de complexité au contexte de l'étudiant
- Incluez des exemples pratiques et des études de cas quand c'est approprié
- Utilisez des méthodologies pédagogiques efficaces
- Assurez-vous que le contenu soit précis et pédagogiquement solide
- TOUT le contenu doit être en français, même si le sujet est en anglais

<#if contentType??>
## Type de contenu: ${contentType}
</#if>

## Sujet/Concept:
${topic}

Veuillez générer du contenu éducatif de haute qualité en FRANÇAIS qui aide les étudiants à apprendre et comprendre efficacement ce sujet.
```

### Step 4: Update Configuration

Update domain model methods if needed to support the new language:

```java
private String getLevelDescription(DifficultyLevel level, StudyBuddyLocale locale) {
    return switch (locale) {
        case SPANISH -> switch (level) {
            case BEGINNER -> "principiante, con explicaciones básicas y ejemplos simples";
            // ... other levels
        };
        case ENGLISH -> switch (level) {
            case BEGINNER -> "beginner level with basic explanations and simple examples";
            // ... other levels
        };
        case FRENCH -> switch (level) {
            case BEGINNER -> "niveau débutant avec explications de base et exemples simples";
            // ... other levels
        };
    };
}
```

### Step 5: Add Tests

Create tests for the new language in `LocalizationServiceTest.java`:

```java
@Test
void testGenerateResearchPromptFrench() {
    String topic = "Intelligence Artificielle";
    String subject = "Informatique";
    
    String prompt = localizationService.generateResearchPrompt(
        StudyBuddyLocale.FRENCH, topic, subject, null, Collections.emptyList()
    );
    
    assertNotNull(prompt);
    assertTrue(prompt.contains("assistant de recherche académique"));
    assertTrue(prompt.contains(topic));
}
```

## Customizing Templates

### Template Syntax

StudyBuddy CLI uses Freemarker template syntax:

```freemarker
<#-- Comments -->
${variable}                    <!-- Variable substitution -->
${variable!"default"}         <!-- Variable with default -->
<#if condition??></#if>       <!-- Conditional -->
<#list items as item></#list> <!-- Iteration -->
```

### Customization Examples

**1. Adding Custom Instructions**

```freemarker
## Instructions IMPORTANTES:
- DEBES responder completamente en español, sin excepción
- Crea contenido educativo claro y bien estructurado
- TODO el contenido debe estar en español, incluso si el tema está en inglés
- Usa un tono profesional pero accesible        <!-- Custom addition -->
- Incluye ejemplos del contexto latinoamericano <!-- Custom addition -->
```

**2. Adding Conditional Sections**

```freemarker
<#if targetAudience??>
## Audiencia objetivo: ${targetAudience}
<#if targetAudience == "universitarios">
Ajusta el contenido para estudiantes universitarios con conocimientos previos.
</#if>
</#if>
```

**3. Custom Variable Processing**

```freemarker
<#if learningObjectives?? && learningObjectives?size > 0>
## Objetivos de aprendizaje:
<#list learningObjectives as objective>
${objective?counter}. ${objective}
</#list>
</#if>
```

## Configuration

### Application Properties

Configure localization behavior in `application.properties`:

```properties
# Default locale
studybuddy.default-locale=es

# Fallback configuration
studybuddy.localization.fallback-to-english=true

# Template configuration
studybuddy.localization.template-encoding=UTF-8
studybuddy.localization.cache-templates=true
```

### Environment Variables

Override configuration with environment variables:

```bash
# Set default locale
export STUDYBUDDY_DEFAULT_LOCALE=en

# Disable fallback
export STUDYBUDDY_LOCALIZATION_FALLBACK_TO_ENGLISH=false
```

### Programmatic Configuration

Configure in `LocalizationConfig.java`:

```java
@ConfigurationProperties(prefix = "studybuddy.localization")
public class LocalizationConfig {
    private StudyBuddyLocale defaultLocale = StudyBuddyLocale.SPANISH;
    private boolean fallbackToEnglish = true;
    private String templateEncoding = "UTF-8";
    
    // getters and setters
}
```

## Testing Localization

### Unit Tests

Test the localization service:

```bash
# Run all localization tests
./mvnw test -Dtest=LocalizationServiceTest

# Test specific language
./mvnw test -Dtest=LocalizationServiceTest#testGenerateResearchPromptSpanish
```

### Manual Testing

Test different languages manually:

```bash
# Spanish output (default)
./studybuddy.sh summarize "machine learning"

# English output
./studybuddy.sh summarize "machine learning" --lang EN

# Verify language consistency
./studybuddy.sh research "artificial intelligence" --lang ES
./studybuddy.sh explain "neural networks" --lang EN
```

### Template Validation

Validate templates by checking:

1. **Variable substitution**: All variables are properly used
2. **Conditional logic**: All conditions work correctly  
3. **Language consistency**: Output is in the expected language
4. **Content quality**: Generated content is appropriate

### Debugging Templates

To debug template issues:

1. **Check template syntax**: Verify Freemarker syntax is correct
2. **Validate variables**: Ensure all required variables are provided
3. **Test fallback**: Verify fallback to English works
4. **Review output**: Check that generated prompts produce expected results

## Best Practices

### Template Development

1. **Use consistent terminology** across templates in the same language
2. **Include explicit language instructions** for AI models
3. **Provide fallback values** for optional variables
4. **Test with real data** to ensure quality output

### Language Support

1. **Native speaker review** for new language templates
2. **Cultural context awareness** in examples and references
3. **Technical term accuracy** for specialized domains
4. **Consistent style** across all templates

### Maintenance

1. **Version control templates** like source code
2. **Document template changes** in commit messages
3. **Test after modifications** to prevent regressions
4. **Keep templates synchronized** across languages for feature parity

---

For more information about implementation details, see the source code in:
- `src/main/java/com/michead/studybuddy/localization/`
- `src/main/resources/templates/prompts/`