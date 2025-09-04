#!/bin/bash
# StudyBuddy CLI - Fast JAR execution

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Load environment variables from .env if it exists
if [ -f .env ]; then
    echo -e "${GREEN}✓ Loading environment from .env${NC}"
    export $(cat .env | grep -v '^#' | xargs)
else
    echo -e "${YELLOW}⚠ No .env file found. Using system environment variables.${NC}"
fi

# Check if Anthropic API key is set
if [ -z "$ANTHROPIC_API_KEY" ]; then
    echo -e "${RED}❌ ANTHROPIC_API_KEY is not set in .env file${NC}"
    exit 1
fi

echo -e "${GREEN}✓ Anthropic API key configured${NC}"

# Check if JAR exists, if not build it
JAR_FILE="target/studybuddy-cli-0.1.0-SNAPSHOT.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo -e "${YELLOW}→ Building StudyBuddy CLI JAR...${NC}"
    ./mvnw clean package -q -DskipTests
    if [ $? -ne 0 ]; then
        echo -e "${RED}❌ Failed to build JAR${NC}"
        exit 1
    fi
fi

echo -e "${GREEN}→ Running StudyBuddy CLI...${NC}"

# Run the JAR directly - much cleaner!
java -jar "$JAR_FILE" "$@"