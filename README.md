# StudyBuddy CLI

![Claude](https://img.shields.io/badge/Claude-3.5%20Haiku-FF6B35?style=for-the-badge&logo=anthropic&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

StudyBuddy CLI is an intelligent educational tool that leverages Anthropic's Claude 3.5 Haiku to generate personalized study content in Spanish and English.

> **[Documentación en Español](README-es.md)** | **English Documentation**

## Features

- **Powered by Claude 3.5 Haiku**: Fast and cost-effective responses
- **Bilingual**: Native support for Spanish (default) and English
- **Three modes**:
  - **Summarize**: Topic summaries with difficulty levels
  - **Research**: Academic research with configurable depth
  - **Explain**: Concept explanations with customizable format
- **Optimized CLI**: Fast interface using PicoCLI
- **JVM-based**: Built with Embabel framework (Java 21)

## Quick Start

### Prerequisites

- Java 21+
- Maven 3.6+
- Anthropic API key

### Setup

```bash
# 1. Clone the project
git clone <repository-url>
cd studybuddy-cli

# 2. Configure environment variables
echo "ANTHROPIC_API_KEY=your_key_here" > .env

# 3. Build the application
./mvnw clean package -DskipTests

# 4. Test functionality
./studybuddy.sh --help
```

## Usage

### Available Commands

#### 1. Summarize - Topic Summaries

```bash
# Basic summary in Spanish (default)
./studybuddy.sh summarize "machine learning"

# Intermediate summary in English
./studybuddy.sh summarize "photosynthesis" --lang EN --level INTERMEDIATE

# Advanced summary
./studybuddy.sh summarize "quantum computing" --lang EN --level ADVANCED
```

**Available levels**: `BEGINNER`, `INTERMEDIATE`, `ADVANCED`

#### 2. Research - Academic Research

```bash
# Basic research
./studybuddy.sh research "artificial intelligence" --lang EN --depth BASIC

# Detailed research
./studybuddy.sh research "blockchain technology" --lang EN --depth DETAILED
```

**Available depths**: `BASIC`, `DETAILED`

#### 3. Explain - Concept Explanations

```bash
# Explanation in plain text
./studybuddy.sh explain "gravitation" --lang EN --format PLAIN

# Explanation in Markdown
./studybuddy.sh explain "photosynthesis" --lang EN --format MARKDOWN
```

**Available formats**: `PLAIN`, `MARKDOWN`

### Global Options

- `--lang [ES|EN]`: Output language (default: Spanish)
- `--help`: Show command help

## Architecture

### Main Components

```
StudyBuddy-CLI/
├── StudyBuddyAgent          # Main agent with 3 actions
│   ├── summarizeTopic()     # Educational summaries
│   ├── researchTopic()      # Academic research  
│   └── explainConcept()     # Detailed explanations
├── LocalizationService      # i18n system
├── CLI Commands             # PicoCLI interface
└── Domain Objects           # Type-safe data model
```

### Technologies

- **Framework**: Embabel Agent Framework 0.1.2
- **LLM**: Claude 3.5 Haiku Latest
- **Templates**: Freemarker for localization
- **CLI**: PicoCLI + Spring Boot
- **Build**: Maven + Java 21

### Model Configuration

```properties
# Claude 3.5 Haiku optimized for education
embabel.models.defaultLlm=claude-3-5-haiku-latest
embabel.ai.anthropic.temperature=0.3
embabel.ai.anthropic.max-tokens=2000
```

## Estimated Costs

With Claude 3.5 Haiku:
- **Input**: $0.80 per 1M tokens
- **Output**: $4 per 1M tokens  
- **Prompt Caching**: 90% discount
- **Typical cost per request**: ~$0.007 (less than 1 cent)

## Development

### Project Structure

```
src/main/java/com/michead/studybuddy/
├── agent/
│   └── StudyBuddyAgent.java
├── domain/
│   ├── StudyRequest.java
│   ├── ResearchRequest.java
│   ├── ExplanationRequest.java
│   ├── StudySummary.java
│   ├── ResearchResult.java
│   └── ConceptExplanation.java
├── localization/
│   ├── StudyBuddyLocale.java
│   ├── LocalizationService.java
│   └── LocalizationConfig.java
└── cli/
    ├── StudyBuddyCLI.java
    ├── SummarizeCommand.java
    ├── ResearchCommand.java
    └── ExplainCommand.java
```

### Localization Templates

```
src/main/resources/templates/prompts/
├── es/
│   ├── research.ftl
│   ├── educational_content.ftl
│   └── summarization.ftl
└── en/
    ├── research.ftl
    ├── educational_content.ftl
    └── summarization.ftl
```

### Running Tests

```bash
# Run all tests
./mvnw test

# Specific localization test
./mvnw test -Dtest=LocalizationServiceTest
```

## JVM Advantages Demonstration

This project demonstrates JVM advantages for LLM agents mentioned in Rod Johnson's article:

- **Type Safety**: No magic strings, everything type-safe  
- **Less Code**: More concise than Python equivalents  
- **Production Ready**: Logging, configuration, concurrency handling  
- **Better Integration**: Seamless with Java/Spring ecosystem  
- **Performance**: JVM optimizations + Claude 3.5 Haiku speed

**StudyBuddy-CLI** is the perfect proof-of-concept for LLM agent potential on the JVM.

## Roadmap

- [ ] Support for more languages (French, Italian)
- [ ] Integration with academic databases
- [ ] Export to PDF/Word
- [ ] Complementary Web UI
- [ ] IDE plugins
- [ ] GraalVM native image support

## Contributing

1. Fork the project
2. Create feature branch (`git checkout -b feature/new-feature`)
3. Commit changes (`git commit -am 'Add new feature'`)
4. Push branch (`git push origin feature/new-feature`)
5. Open Pull Request

## License

Licensed under the Apache License, Version 2.0

## Support

If you encounter issues or have questions:

1. Check the [Spanish documentation](README-es.md)
2. Verify your API key configuration
3. Run with `--help` to see all available options

---

> **StudyBuddy-CLI** - Demonstrating the power of LLM agents on the JVM with Claude 3.5 Haiku