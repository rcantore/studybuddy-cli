# Referencia de StudyBuddy CLI

Este documento proporciona una referencia completa para todos los comandos, opciones y patrones de uso de StudyBuddy CLI.

## Índice

- [Estructura de Comandos](#estructura-de-comandos)
- [Opciones Globales](#opciones-globales)
- [Comandos](#comandos)
  - [summarize](#summarize)
  - [research](#research)
  - [explain](#explain)
- [Formatos de Salida](#formatos-de-salida)
- [Códigos de Salida](#códigos-de-salida)
- [Configuración](#configuración)
- [Solución de Problemas](#solución-de-problemas)

## Estructura de Comandos

```
./studybuddy.sh <comando> <tema> [opciones]
```

### Sintaxis Básica

- `<comando>`: Uno de `summarize`, `research`, `explain`
- `<tema>`: El tema o concepto a procesar (usar comillas para temas de múltiples palabras)
- `[opciones]`: Opciones específicas del comando y globales

### Ejemplos

```bash
./studybuddy.sh summarize "machine learning"
./studybuddy.sh research "computación cuántica" --depth DETAILED
./studybuddy.sh explain "redes neuronales" --format MARKDOWN --lang ES
```

## Opciones Globales

Estas opciones están disponibles para todos los comandos:

### `--lang <idioma>`

Especifica el idioma de salida.

- **Valores**: `ES` (Español), `EN` (Inglés)
- **Por defecto**: `ES` (Español)
- **Ejemplo**: `--lang EN`

### `--help`

Muestra información de ayuda para el comando.

- **Uso**: Puede usarse con cualquier comando o globalmente
- **Ejemplos**:
  - `./studybuddy.sh --help` - Ayuda general
  - `./studybuddy.sh summarize --help` - Ayuda específica del comando

## Comandos

### summarize

Genera resúmenes educativos estructurados de temas.

#### Sintaxis

```bash
./studybuddy.sh summarize "<tema>" [--level <nivel>] [--lang <idioma>]
```

#### Opciones

**`--level <nivel>`**
- **Descripción**: Nivel de dificultad para el resumen
- **Valores**: 
  - `BEGINNER` - Explicaciones básicas con ejemplos simples
  - `INTERMEDIATE` - Mayor profundidad con ejemplos prácticos  
  - `ADVANCED` - Análisis detallado con conceptos complejos
- **Por defecto**: `BEGINNER`

#### Ejemplos

```bash
# Resumen básico para principiantes en español (por defecto)
./studybuddy.sh summarize "inteligencia artificial"

# Nivel intermedio en inglés
./studybuddy.sh summarize "machine learning" --level INTERMEDIATE --lang EN

# Resumen de nivel avanzado
./studybuddy.sh summarize "deep learning" --level ADVANCED
```

#### Estructura de Salida

El resumen incluye:
- **Título**: Título del tema formateado
- **Tema**: Tema original
- **Nivel**: Nivel de dificultad
- **Idioma**: Idioma de salida
- **Resumen**: Explicación del contenido principal
- **Conceptos Clave**: Lista de conceptos importantes
- **Ejemplos**: Ejemplos prácticos
- **Aplicaciones Prácticas**: Aplicaciones del mundo real

### research

Conduce investigación académica completa sobre temas.

#### Sintaxis

```bash
./studybuddy.sh research "<tema>" [--depth <profundidad>] [--lang <idioma>]
```

#### Opciones

**`--depth <profundidad>`**
- **Descripción**: Profundidad y nivel de detalle de la investigación
- **Valores**:
  - `BASIC` - Visión general con puntos clave
  - `DETAILED` - Análisis en profundidad con múltiples perspectivas y fuentes
- **Por defecto**: `BASIC`

#### Ejemplos

```bash
# Investigación básica en español (por defecto)
./studybuddy.sh research "tecnología blockchain"

# Investigación detallada en inglés
./studybuddy.sh research "quantum computing" --depth DETAILED --lang EN

# Investigación básica sobre tema en español
./studybuddy.sh research "inteligencia artificial" --depth BASIC
```

#### Estructura de Salida

La investigación incluye:
- **Título**: Tema de investigación
- **Profundidad**: Nivel de profundidad de la investigación
- **Idioma**: Idioma de salida
- **Resumen General**: Resumen general
- **Hallazgos Principales**: Principales hallazgos de investigación
- **Fuentes**: Fuentes de referencia
- **Temas Relacionados**: Conceptos conectados

### explain

Proporciona explicaciones claras de conceptos específicos.

#### Sintaxis

```bash
./studybuddy.sh explain "<concepto>" [--format <formato>] [--lang <idioma>]
```

#### Opciones

**`--format <formato>`**
- **Descripción**: Estilo de formato de salida
- **Valores**:
  - `PLAIN` - Texto plano sin formato especial
  - `MARKDOWN` - Formateado en Markdown con encabezados, listas, énfasis
- **Por defecto**: `MARKDOWN`

#### Ejemplos

```bash
# Explicación en Markdown en español (por defecto)
./studybuddy.sh explain "redes neuronales"

# Explicación en texto plano en inglés
./studybuddy.sh explain "photosynthesis" --format PLAIN --lang EN

# Explicación en Markdown con idioma específico
./studybuddy.sh explain "redes neuronales" --format MARKDOWN --lang ES
```

#### Estructura de Salida

La explicación incluye:
- **Título**: Nombre del concepto
- **Idioma**: Idioma de salida
- **Explicación**: Explicación detallada del concepto
- **Ejemplos**: Ejemplos ilustrativos
- **Conceptos Relacionados**: Temas conectados

## Formatos de Salida

### Salida por Consola

Todos los comandos muestran salida directamente en la consola con:
- **Mensajes de estado coloreados**: Verde para éxito, rojo para errores
- **Formato limpio**: Salida estructurada sin información de debug
- **Indicadores de progreso**: Mensajes de carga durante el procesamiento

### Formato Markdown

Al usar `--format MARKDOWN`, la salida incluye:
- Encabezados (`#`, `##`, `###`)
- Texto en negrita (`**negrita**`)
- Listas (`-`, `1.`)
- Bloques de código (cuando sea relevante)

### Formato Texto Plano

Al usar `--format PLAIN`, la salida incluye:
- Estructura de texto simple
- Organización básica
- Sin sintaxis especial de markdown

## Códigos de Salida

StudyBuddy CLI usa códigos de salida estándar:

- **0**: Éxito
- **1**: Error general (argumentos inválidos, problemas de API)
- **2**: Uso inválido del comando

## Configuración

### Variables de Entorno

Configura estas en tu archivo `.env`:

```bash
# Requerido
ANTHROPIC_API_KEY=tu_clave_api_aqui

# Opcional
STUDYBUDDY_DEFAULT_LOCALE=es          # Idioma por defecto (es/en)
EMBABEL_MODELS_DEFAULT_LLM=claude-3-5-haiku-latest
EMBABEL_AI_ANTHROPIC_TEMPERATURE=0.3
EMBABEL_AI_ANTHROPIC_MAX_TOKENS=2000
```

### Idioma Por Defecto

El idioma por defecto es español (`ES`). Para cambiar el idioma por defecto:
1. Configura `STUDYBUDDY_DEFAULT_LOCALE=en` en `.env`
2. O siempre especifica `--lang EN` en los comandos

## Solución de Problemas

### Problemas Comunes

**1. Clave API No Encontrada**
```
Error: Anthropic API key not configured
```
- **Solución**: Crear archivo `.env` con `ANTHROPIC_API_KEY=tu_clave`

**2. Comando Inválido**
```
Unknown command: sumary
```
- **Solución**: Verificar ortografía del comando: `summarize`, `research`, `explain`

**3. Tema Faltante**
```
Error: Topic is required
```
- **Solución**: Proporcionar tema entre comillas: `"machine learning"`

**4. Opciones Inválidas**
```
Invalid level: beginer
```
- **Solución**: Usar valores exactos: `BEGINNER`, `INTERMEDIATE`, `ADVANCED`

### Modo Debug

Para depurar problemas:

1. **Verificar ayuda**: `./studybuddy.sh --help`
2. **Verificar configuración**: Asegurar que Java 21+ y clave API estén configurados
3. **Probar conexión**: Intentar un comando simple primero
4. **Revisar logs**: Buscar mensajes de error en la salida

### Obtener Ayuda

- **Ayuda de comando**: `./studybuddy.sh <comando> --help`
- **Ayuda global**: `./studybuddy.sh --help`
- **Documentación**: Revisar [README-es.md](README-es.md) para configuración

## Uso Avanzado

### Scripts

StudyBuddy CLI puede usarse en scripts:

```bash
#!/bin/bash

# Generar múltiples resúmenes
topics=("machine learning" "inteligencia artificial" "redes neuronales")

for topic in "${topics[@]}"; do
    echo "Generando resumen para: $topic"
    ./studybuddy.sh summarize "$topic" --level INTERMEDIATE
    echo "---"
done
```

### Procesamiento por Lotes

```bash
# Investigar múltiples temas
./studybuddy.sh research "computación cuántica" --depth DETAILED > investigacion_cuantica.md
./studybuddy.sh research "blockchain" --depth DETAILED > investigacion_blockchain.md
./studybuddy.sh research "machine learning" --depth DETAILED > investigacion_ml.md
```

### Flujos de Trabajo en Múltiples Idiomas

```bash
# Mismo tema en diferentes idiomas
./studybuddy.sh summarize "artificial intelligence" --lang EN > resumen_ia_en.md
./studybuddy.sh summarize "artificial intelligence" --lang ES > resumen_ia_es.md
```

---

Para más información, consulta la documentación principal [README-es.md](README-es.md).