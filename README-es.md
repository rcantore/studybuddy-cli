# StudyBuddy CLI

StudyBuddy CLI demuestra el poder de construir agentes LLM con Java en lugar de Python, usando el framework Embabel para crear aplicaciones de IA type-safe y listas para producción en el JVM.

## ¿Por qué Java para Agentes LLM?

Este proyecto demuestra que Java con el framework Embabel es una excelente opción para construir agentes LLM, ofreciendo ventajas significativas para aplicaciones de IA en producción:

### Ventajas Clave:

- **Type Safety**: Verificación en tiempo de compilación asegura código robusto
- **Rendimiento**: Optimización JVM y capacidades de multithreading real
- **Listo para Producción**: Monitoreo, logging y despliegue de nivel enterprise
- **Gestión de Dependencias**: Resolución madura de dependencias con Maven
- **Soporte IDE**: Excelente refactoring e inteligencia de código
- **Framework Embabel**: Diseñado específicamente para agentes LLM en Java con integración Spring Boot

### Lo que Hace Genial Este Enfoque:

- **Código Limpio**: Interfaces type-safe y contratos claros
- **Mantenibilidad**: El tipado fuerte hace el refactoring seguro y fácil
- **Listo para Enterprise**: Se integra perfectamente con infraestructura Java existente
- **Ecosistema Spring**: Aprovecha las poderosas características de Spring Boot

## Características

- **Multiidioma**: Soporte completo para español e inglés con localización automática
- **Contenido Educativo**: Genera resúmenes, investigaciones y explicaciones de conceptos
- **CLI Profesional**: Interfaz limpia con colores y estructura clara
- **Rendimiento**: Ejecución rápida usando JAR compilado
- **Niveles Adaptativos**: Contenido adaptado a diferentes niveles de dificultad

## Instalación Rápida

### Prerrequisitos

- Java 21 o superior
- Clave API de Anthropic

### Configuración

1. **Clonar el repositorio**:
   ```bash
   git clone <repository-url>
   cd studybuddy-cli
   ```

2. **Configurar la clave API**:
   ```bash
   # Crear archivo .env con tu clave API de Anthropic
   echo "ANTHROPIC_API_KEY=tu_clave_aqui" > .env
   ```

3. **Construir la aplicación**:
   ```bash
   ./mvnw clean package -DskipTests
   ```

4. **¡Listo para usar!**:
   ```bash
   ./studybuddy.sh --help
   ```

## Uso

StudyBuddy CLI ofrece tres comandos principales:

### Resumir Temas

Genera resúmenes educativos estructurados:

```bash
# Ejemplo básico
./studybuddy.sh summarize "machine learning" --level BEGINNER

# Con configuración específica
./studybuddy.sh summarize "inteligencia artificial" --level ADVANCED --lang ES
```

**Niveles disponibles**: `BEGINNER`, `INTERMEDIATE`, `ADVANCED`

### Investigar Temas

Conduce investigación académica detallada:

```bash
# Investigación básica
./studybuddy.sh research "quantum computing" --depth BASIC

# Investigación detallada
./studybuddy.sh research "computación cuántica" --depth DETAILED --lang ES
```

**Profundidades disponibles**: `BASIC`, `DETAILED`

### Explicar Conceptos

Explica conceptos complejos de manera clara:

```bash
# Explicación en Markdown
./studybuddy.sh explain "neural networks" --format MARKDOWN

# Explicación en texto plano
./studybuddy.sh explain "redes neuronales" --format PLAIN --lang ES
```

**Formatos disponibles**: `MARKDOWN`, `PLAIN`

## Configuración de Idioma

StudyBuddy CLI detecta automáticamente el idioma basado en la configuración por defecto (español), pero puedes especificarlo explícitamente:

```bash
# Forzar español
./studybuddy.sh summarize "artificial intelligence" --lang ES

# Forzar inglés
./studybuddy.sh summarize "inteligencia artificial" --lang EN
```

**Idiomas soportados**: `ES` (Español), `EN` (English)

## Ejemplos Avanzados

### Caso de Uso Educativo

```bash
# Generar material para clase de programación
./studybuddy.sh summarize "programación orientada a objetos" --level INTERMEDIATE

# Investigar para proyecto universitario
./studybuddy.sh research "machine learning applications" --depth DETAILED

# Explicar concepto para estudiantes
./studybuddy.sh explain "algoritmos de búsqueda" --format MARKDOWN
```

### Flujo de Trabajo Completo

```bash
# 1. Comenzar con un resumen
./studybuddy.sh summarize "blockchain" --level BEGINNER

# 2. Profundizar con investigación
./studybuddy.sh research "blockchain technology" --depth DETAILED

# 3. Aclarar conceptos específicos
./studybuddy.sh explain "proof of work" --format MARKDOWN
```

## Configuración Avanzada

### Variables de Entorno

Crea un archivo `.env` para personalizar la configuración:

```bash
# Configuración de API
ANTHROPIC_API_KEY=tu_clave_aqui

# Configuración del modelo
EMBABEL_MODELS_DEFAULT_LLM=claude-3-5-haiku-latest
EMBABEL_AI_ANTHROPIC_TEMPERATURE=0.3
EMBABEL_AI_ANTHROPIC_MAX_TOKENS=2000

# Configuración de idioma
STUDYBUDDY_DEFAULT_LOCALE=es

# Configuración de logging
LOGGING_LEVEL_COM_MICHEAD=INFO
```

### Personalización de Plantillas

Las plantillas de prompts están en `src/main/resources/templates/prompts/`:
- `es/` - Plantillas en español
- `en/` - Plantillas en inglés

## Desarrollo

### Ejecutar Tests

```bash
./mvnw test
```

### Ejecutar en Modo Desarrollo

```bash
# Usando Maven
./mvnw spring-boot:run --

# Usando JAR
./studybuddy.sh
```

### Estructura del Proyecto

```
studybuddy-cli/
├── src/main/java/com/michead/studybuddy/
│   ├── agent/           # Agente principal de StudyBuddy
│   ├── cli/             # Comandos CLI
│   ├── domain/          # Modelos de dominio
│   └── localization/    # Servicios de localización
├── src/main/resources/
│   ├── templates/       # Plantillas Freemarker
│   └── application.properties
└── src/test/           # Tests unitarios
```

## Arquitectura

StudyBuddy CLI está construido con:

- **Spring Boot 3.5.3** - Framework de aplicación
- **Embabel Agent Framework** - Plataforma para agentes LLM
- **Anthropic Claude 3.5 Haiku** - Modelo de lenguaje
- **PicoCLI** - Framework para CLI
- **Freemarker** - Motor de plantillas
- **Java 21** - Plataforma de ejecución

## Próximos Pasos

Posibles mejoras futuras:

- **GraalVM Native Images** - Compilación nativa para startup ultra-rápido
- **Más Idiomas** - Soporte para francés, alemán, portugués
- **Modo Interactivo** - CLI interactivo con menús
- **Exportación** - Exportar resultados a PDF, DOCX
- **Configuración Visual** - Interfaz web para configuración

## Contribuir

¡Las contribuciones son bienvenidas! Por favor:

1. Fork el proyecto
2. Crea una rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## Licencia

Este proyecto es un proof-of-concept para demostrar las capacidades del framework Embabel con agentes LLM en el JVM.

## Soporte

Si encuentras algún problema o tienes preguntas:

1. Revisa la [documentación en inglés](README.md)
2. Verifica tu configuración de clave API
3. Ejecuta con `--help` para ver todas las opciones disponibles

---

**¡Disfruta creando contenido educativo con StudyBuddy CLI!**