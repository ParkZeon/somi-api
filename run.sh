#!/bin/bash
# Start the SOMI MCP server on http://127.0.0.1:8080/mcp
# JAVA_HOME defaults to the user-local Temurin 21 in ~/.jdks.
set -e
cd "$(dirname "$0")"
if [ -z "$JAVA_HOME" ]; then
  JAVA_HOME=$(ls -d ~/.jdks/jdk-21*/Contents/Home 2>/dev/null | tail -1)
  export JAVA_HOME
fi
exec ./mvnw -q spring-boot:run
