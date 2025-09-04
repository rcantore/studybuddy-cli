# Guía de Localización de StudyBuddy CLI

Este documento explica cómo funciona la localización en StudyBuddy CLI, cómo personalizarla y cómo agregar nuevos idiomas.

## Índice

- [Resumen](#resumen)
- [Arquitectura](#arquitectura)
- [Idiomas Soportados](#idiomas-soportados)
- [Detección de Idioma](#detección-de-idioma)
- [Sistema de Plantillas](#sistema-de-plantillas)
- [Agregar Nuevos Idiomas](#agregar-nuevos-idiomas)
- [Personalizar Plantillas](#personalizar-plantillas)
- [Configuración](#configuración)
- [Pruebas de Localización](#pruebas-de-localización)

## Resumen

StudyBuddy CLI proporciona soporte multiidioma completo con:

- **Detección automática de idioma** basada en configuración
- **Prompts dirigidos por plantillas** usando Freemarker
- **Sistema de respaldo** al inglés cuando faltan traducciones
- **Localización consistente** en toda la salida del CLI
- **Fácil extensibilidad** para nuevos idiomas

## Arquitectura

### Componentes Principales

El sistema de localización consiste en varios componentes clave:

```
Sistema de Localización
├── StudyBuddyLocale          # Enumeración de idiomas
├── LocalizationService       # Servicio central de localización
├── LocalizationConfig        # Configuración de ajustes
└── Plantillas Freemarker     # Prompts específicos por idioma
```

### Flujo de Datos

1. **Entrada del Usuario**: Comando con parámetro opcional `--lang`
2. **Detección de Idioma**: Determinar idioma objetivo (preferencia del usuario o por defecto)
3. **Selección de Plantilla**: Elegir plantilla Freemarker apropiada
4. **Generación de Prompt**: Generar prompt localizado para IA
5. **Formato de Salida**: Formatear respuesta con etiquetas localizadas

## Idiomas Soportados

Idiomas actualmente soportados:

| Idioma  | Código | Locale | Estado |
|---------|--------|--------|---------|
| Español | ES     | es     | Soporte Completo |
| Inglés  | EN     | en     | Soporte Completo |

### Características de Idiomas

**Español (ES) - Por Defecto**
- Cobertura completa de plantillas
- Nombres de campos y etiquetas localizadas
- Respuestas naturales de IA en español
- Conciencia de contexto cultural

**Inglés (EN)**
- Cobertura completa de plantillas
- Salida profesional en inglés
- Enfoque en terminología técnica
- Contexto internacional

## Detección de Idioma

### Idioma Por Defecto

El sistema por defecto usa español (`ES`) basado en la configuración:

```properties
studybuddy.default-locale=es
```

### Prioridad de Selección de Idioma

1. **Línea de Comandos**: `--lang EN` (máxima prioridad)
2. **Variable de Entorno**: `STUDYBUDDY_DEFAULT_LOCALE=en`
3. **Configuración de Aplicación**: `studybuddy.default-locale=es`
4. **Por Defecto del Sistema**: Español (`ES`) (respaldo)

### Ejemplos

```bash
# Usar idioma por defecto (español)
./studybuddy.sh summarize "machine learning"

# Forzar inglés
./studybuddy.sh summarize "machine learning" --lang EN

# Forzar español (explícito)
./studybuddy.sh summarize "machine learning" --lang ES
```

## Sistema de Plantillas

### Estructura de Plantillas

Las plantillas están organizadas por idioma en el directorio de recursos:

```
src/main/resources/templates/prompts/
├── es/                           # Plantillas en español
│   ├── educational_content.ftl   # Generación de contenido educativo
│   ├── research.ftl              # Investigación académica
│   └── summarization.ftl         # Resumen de contenido
└── en/                           # Plantillas en inglés
    ├── educational_content.ftl   # Generación de contenido educativo
    ├── research.ftl              # Investigación académica
    └── summarization.ftl         # Resumen de contenido
```

### Tipos de Plantillas

**1. educational_content.ftl**
- Usado para: comandos `summarize` y `explain`
- Propósito: Generar explicaciones y resúmenes educativos
- Variables: `topic`, `subject`, `contentType`, `targetAudience`, `learningObjectives`, `difficulty`, `additionalRequirements`

**2. research.ftl**
- Usado para: comando `research`
- Propósito: Conducir investigación académica
- Variables: `topic`, `subject`, `context`, `specificQuestions`

**3. summarization.ftl**
- Usado para: Resumen de contenido (característica futura)
- Propósito: Resumir contenido existente
- Variables: `content`, `summaryType`, `maxLength`, `focusAreas`

### Variables de Plantillas

Variables comunes disponibles en plantillas:

| Variable | Tipo | Descripción |
|----------|------|-------------|
| `topic` | String | Tema o concepto principal |
| `subject` | String | Área de materia académica |
| `contentType` | String | Tipo de contenido (resumen, explicación) |
| `targetAudience` | String | Audiencia objetivo |
| `learningObjectives` | List | Objetivos de aprendizaje |
| `difficulty` | String | Nivel de dificultad |
| `additionalRequirements` | String | Requisitos especiales |

## Agregar Nuevos Idiomas

### Paso 1: Agregar Enumeración de Idioma

Agregar el nuevo idioma a `StudyBuddyLocale.java`:

```java
public enum StudyBuddyLocale {
    SPANISH("es", "Español"),
    ENGLISH("en", "English"),
    FRENCH("fr", "Français");  // Nuevo idioma
    
    // ... resto de la enumeración
}
```

### Paso 2: Crear Directorio de Plantillas

Crear un nuevo directorio para las plantillas del idioma:

```
src/main/resources/templates/prompts/fr/
├── educational_content.ftl
├── research.ftl
└── summarization.ftl
```

### Paso 3: Traducir Plantillas

Copiar plantillas existentes y traducirlas. Por ejemplo, `fr/educational_content.ftl`:

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

### Paso 4: Actualizar Configuración

Actualizar métodos del modelo de dominio si es necesario para soportar el nuevo idioma:

```java
private String getLevelDescription(DifficultyLevel level, StudyBuddyLocale locale) {
    return switch (locale) {
        case SPANISH -> switch (level) {
            case BEGINNER -> "principiante, con explicaciones básicas y ejemplos simples";
            // ... otros niveles
        };
        case ENGLISH -> switch (level) {
            case BEGINNER -> "beginner level with basic explanations and simple examples";
            // ... otros niveles
        };
        case FRENCH -> switch (level) {
            case BEGINNER -> "niveau débutant avec explications de base et exemples simples";
            // ... otros niveles
        };
    };
}
```

### Paso 5: Agregar Pruebas

Crear pruebas para el nuevo idioma en `LocalizationServiceTest.java`:

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

## Personalizar Plantillas

### Sintaxis de Plantillas

StudyBuddy CLI usa sintaxis de plantillas Freemarker:

```freemarker
<#-- Comentarios -->
${variable}                    <!-- Sustitución de variable -->
${variable!"defecto"}         <!-- Variable con valor por defecto -->
<#if condicion??></#if>       <!-- Condicional -->
<#list items as item></#list> <!-- Iteración -->
```

### Ejemplos de Personalización

**1. Agregar Instrucciones Personalizadas**

```freemarker
## Instrucciones IMPORTANTES:
- DEBES responder completamente en español, sin excepción
- Crea contenido educativo claro y bien estructurado
- TODO el contenido debe estar en español, incluso si el tema está en inglés
- Usa un tono profesional pero accesible        <!-- Adición personalizada -->
- Incluye ejemplos del contexto latinoamericano <!-- Adición personalizada -->
```

**2. Agregar Secciones Condicionales**

```freemarker
<#if targetAudience??>
## Audiencia objetivo: ${targetAudience}
<#if targetAudience == "universitarios">
Ajusta el contenido para estudiantes universitarios con conocimientos previos.
</#if>
</#if>
```

**3. Procesamiento de Variables Personalizadas**

```freemarker
<#if learningObjectives?? && learningObjectives?size > 0>
## Objetivos de aprendizaje:
<#list learningObjectives as objective>
${objective?counter}. ${objective}
</#list>
</#if>
```

## Configuración

### Propiedades de Aplicación

Configurar comportamiento de localización en `application.properties`:

```properties
# Locale por defecto
studybuddy.default-locale=es

# Configuración de respaldo
studybuddy.localization.fallback-to-english=true

# Configuración de plantillas
studybuddy.localization.template-encoding=UTF-8
studybuddy.localization.cache-templates=true
```

### Variables de Entorno

Sobrescribir configuración con variables de entorno:

```bash
# Configurar locale por defecto
export STUDYBUDDY_DEFAULT_LOCALE=en

# Deshabilitar respaldo
export STUDYBUDDY_LOCALIZATION_FALLBACK_TO_ENGLISH=false
```

### Configuración Programática

Configurar en `LocalizationConfig.java`:

```java
@ConfigurationProperties(prefix = "studybuddy.localization")
public class LocalizationConfig {
    private StudyBuddyLocale defaultLocale = StudyBuddyLocale.SPANISH;
    private boolean fallbackToEnglish = true;
    private String templateEncoding = "UTF-8";
    
    // getters y setters
}
```

## Pruebas de Localización

### Pruebas Unitarias

Probar el servicio de localización:

```bash
# Ejecutar todas las pruebas de localización
./mvnw test -Dtest=LocalizationServiceTest

# Probar idioma específico
./mvnw test -Dtest=LocalizationServiceTest#testGenerateResearchPromptSpanish
```

### Pruebas Manuales

Probar diferentes idiomas manualmente:

```bash
# Salida en español (por defecto)
./studybuddy.sh summarize "machine learning"

# Salida en inglés
./studybuddy.sh summarize "machine learning" --lang EN

# Verificar consistencia de idioma
./studybuddy.sh research "artificial intelligence" --lang ES
./studybuddy.sh explain "neural networks" --lang EN
```

### Validación de Plantillas

Validar plantillas verificando:

1. **Sustitución de variables**: Todas las variables se usan correctamente
2. **Lógica condicional**: Todas las condiciones funcionan correctamente  
3. **Consistencia de idioma**: La salida está en el idioma esperado
4. **Calidad del contenido**: El contenido generado es apropiado

### Depuración de Plantillas

Para depurar problemas de plantillas:

1. **Verificar sintaxis de plantilla**: Verificar que la sintaxis de Freemarker es correcta
2. **Validar variables**: Asegurar que todas las variables requeridas se proporcionan
3. **Probar respaldo**: Verificar que el respaldo al inglés funciona
4. **Revisar salida**: Verificar que los prompts generados producen resultados esperados

## Mejores Prácticas

### Desarrollo de Plantillas

1. **Usar terminología consistente** en todas las plantillas del mismo idioma
2. **Incluir instrucciones explícitas de idioma** para modelos de IA
3. **Proporcionar valores de respaldo** para variables opcionales
4. **Probar con datos reales** para asegurar salida de calidad

### Soporte de Idiomas

1. **Revisión por hablante nativo** para plantillas de nuevos idiomas
2. **Conciencia de contexto cultural** en ejemplos y referencias
3. **Precisión de términos técnicos** para dominios especializados
4. **Estilo consistente** en todas las plantillas

### Mantenimiento

1. **Control de versiones de plantillas** como código fuente
2. **Documentar cambios de plantillas** en mensajes de commit
3. **Probar después de modificaciones** para prevenir regresiones
4. **Mantener plantillas sincronizadas** entre idiomas para paridad de características

---

Para más información sobre detalles de implementación, ver el código fuente en:
- `src/main/java/com/michead/studybuddy/localization/`
- `src/main/resources/templates/prompts/`