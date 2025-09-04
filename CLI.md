# StudyBuddy CLI Reference

This document provides comprehensive reference for all StudyBuddy CLI commands, options, and usage patterns.

## Table of Contents

- [Command Structure](#command-structure)
- [Global Options](#global-options)
- [Commands](#commands)
  - [summarize](#summarize)
  - [research](#research)
  - [explain](#explain)
- [Output Formats](#output-formats)
- [Exit Codes](#exit-codes)
- [Configuration](#configuration)
- [Troubleshooting](#troubleshooting)

## Command Structure

```
./studybuddy.sh <command> <topic> [options]
```

### Basic Syntax

- `<command>`: One of `summarize`, `research`, `explain`
- `<topic>`: The topic or concept to process (use quotes for multi-word topics)
- `[options]`: Command-specific and global options

### Examples

```bash
./studybuddy.sh summarize "machine learning"
./studybuddy.sh research "quantum computing" --depth DETAILED
./studybuddy.sh explain "neural networks" --format MARKDOWN --lang EN
```

## Global Options

These options are available for all commands:

### `--lang <language>`

Specify the output language.

- **Values**: `ES` (Spanish), `EN` (English)
- **Default**: `ES` (Spanish)
- **Example**: `--lang EN`

### `--help`

Display help information for the command.

- **Usage**: Can be used with any command or globally
- **Examples**:
  - `./studybuddy.sh --help` - General help
  - `./studybuddy.sh summarize --help` - Command-specific help

## Commands

### summarize

Generate structured educational summaries of topics.

#### Syntax

```bash
./studybuddy.sh summarize "<topic>" [--level <level>] [--lang <language>]
```

#### Options

**`--level <level>`**
- **Description**: Difficulty level for the summary
- **Values**: 
  - `BEGINNER` - Basic explanations with simple examples
  - `INTERMEDIATE` - More depth with practical examples  
  - `ADVANCED` - Detailed analysis with complex concepts
- **Default**: `BEGINNER`

#### Examples

```bash
# Basic beginner summary in Spanish (default)
./studybuddy.sh summarize "artificial intelligence"

# Intermediate level in English
./studybuddy.sh summarize "machine learning" --level INTERMEDIATE --lang EN

# Advanced level summary
./studybuddy.sh summarize "deep learning" --level ADVANCED
```

#### Output Structure

The summary includes:
- **Title**: Formatted topic title
- **Topic**: Original topic
- **Level**: Difficulty level
- **Language**: Output language
- **Summary**: Main content explanation
- **Key Concepts**: Important concepts list
- **Examples**: Practical examples
- **Practical Applications**: Real-world applications

### research

Conduct comprehensive academic research on topics.

#### Syntax

```bash
./studybuddy.sh research "<topic>" [--depth <depth>] [--lang <language>]
```

#### Options

**`--depth <depth>`**
- **Description**: Research depth and detail level
- **Values**:
  - `BASIC` - General overview with key points
  - `DETAILED` - In-depth analysis with multiple perspectives and sources
- **Default**: `BASIC`

#### Examples

```bash
# Basic research in Spanish (default)
./studybuddy.sh research "blockchain technology"

# Detailed research in English
./studybuddy.sh research "quantum computing" --depth DETAILED --lang EN

# Basic research on Spanish topic
./studybuddy.sh research "inteligencia artificial" --depth BASIC
```

#### Output Structure

The research includes:
- **Title**: Research topic
- **Depth**: Research depth level
- **Language**: Output language
- **Overview**: General summary
- **Key Findings**: Main research findings
- **Sources**: Reference sources
- **Related Topics**: Connected concepts

### explain

Provide clear explanations of specific concepts.

#### Syntax

```bash
./studybuddy.sh explain "<concept>" [--format <format>] [--lang <language>]
```

#### Options

**`--format <format>`**
- **Description**: Output format style
- **Values**:
  - `PLAIN` - Plain text without special formatting
  - `MARKDOWN` - Markdown formatted with headers, lists, emphasis
- **Default**: `MARKDOWN`

#### Examples

```bash
# Markdown explanation in Spanish (default)
./studybuddy.sh explain "neural networks"

# Plain text explanation in English
./studybuddy.sh explain "photosynthesis" --format PLAIN --lang EN

# Markdown explanation with specific language
./studybuddy.sh explain "redes neuronales" --format MARKDOWN --lang ES
```

#### Output Structure

The explanation includes:
- **Title**: Concept name
- **Language**: Output language
- **Explanation**: Detailed concept explanation
- **Examples**: Illustrative examples
- **Related Concepts**: Connected topics

## Output Formats

### Console Output

All commands output directly to the console with:
- **Colored status messages**: Green for success, red for errors
- **Clean formatting**: Structured output without debug information
- **Progress indicators**: Loading messages during processing

### Markdown Formatting

When using `--format MARKDOWN`, output includes:
- Headers (`#`, `##`, `###`)
- Bold text (`**bold**`)
- Lists (`-`, `1.`)
- Code blocks (when relevant)

### Plain Text Formatting

When using `--format PLAIN`, output includes:
- Simple text structure
- Basic organization
- No special markdown syntax

## Exit Codes

StudyBuddy CLI uses standard exit codes:

- **0**: Success
- **1**: General error (invalid arguments, API issues)
- **2**: Invalid command usage

## Configuration

### Environment Variables

Set these in your `.env` file:

```bash
# Required
ANTHROPIC_API_KEY=your_api_key_here

# Optional
STUDYBUDDY_DEFAULT_LOCALE=es          # Default language (es/en)
EMBABEL_MODELS_DEFAULT_LLM=claude-3-5-haiku-latest
EMBABEL_AI_ANTHROPIC_TEMPERATURE=0.3
EMBABEL_AI_ANTHROPIC_MAX_TOKENS=2000
```

### Default Language

The default language is Spanish (`ES`). To change the default:
1. Set `STUDYBUDDY_DEFAULT_LOCALE=en` in `.env`
2. Or always specify `--lang EN` in commands

## Troubleshooting

### Common Issues

**1. API Key Not Found**
```
Error: Anthropic API key not configured
```
- **Solution**: Create `.env` file with `ANTHROPIC_API_KEY=your_key`

**2. Invalid Command**
```
Unknown command: sumary
```
- **Solution**: Check command spelling: `summarize`, `research`, `explain`

**3. Missing Topic**
```
Error: Topic is required
```
- **Solution**: Provide topic in quotes: `"machine learning"`

**4. Invalid Options**
```
Invalid level: beginer
```
- **Solution**: Use exact values: `BEGINNER`, `INTERMEDIATE`, `ADVANCED`

### Debug Mode

For debugging issues:

1. **Check help**: `./studybuddy.sh --help`
2. **Verify setup**: Ensure Java 21+ and API key are configured
3. **Test connection**: Try a simple command first
4. **Check logs**: Look for error messages in output

### Getting Help

- **Command help**: `./studybuddy.sh <command> --help`
- **Global help**: `./studybuddy.sh --help`
- **Documentation**: Check [README.md](README.md) for setup

## Advanced Usage

### Scripting

StudyBuddy CLI can be used in scripts:

```bash
#!/bin/bash

# Generate multiple summaries
topics=("machine learning" "artificial intelligence" "neural networks")

for topic in "${topics[@]}"; do
    echo "Generating summary for: $topic"
    ./studybuddy.sh summarize "$topic" --level INTERMEDIATE
    echo "---"
done
```

### Batch Processing

```bash
# Research multiple topics
./studybuddy.sh research "quantum computing" --depth DETAILED > quantum_research.md
./studybuddy.sh research "blockchain" --depth DETAILED > blockchain_research.md
./studybuddy.sh research "machine learning" --depth DETAILED > ml_research.md
```

### Language Workflows

```bash
# Same topic in different languages
./studybuddy.sh summarize "artificial intelligence" --lang EN > ai_summary_en.md
./studybuddy.sh summarize "artificial intelligence" --lang ES > ai_summary_es.md
```

---

For more information, see the main [README.md](README.md) documentation.