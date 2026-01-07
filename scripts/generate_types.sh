#!/bin/bash
set -e

# Ensure we are in the sdk root or handle paths correctly
# This script assumes it's run from the cli/java-sdk directory or we can find the schema relative to it.

SCHEMA_PATH="../../apps/docs/public/api-reference.json"
TEMP_DIR=".openapi-temp"
TYPES_OUTPUT_DIR="src/main/java/dev/unsent/types"

echo "Generating Java types from ${SCHEMA_PATH}..."

# check if schema exists
if [ ! -f "$SCHEMA_PATH" ]; then
    echo "Error: Schema file not found at $SCHEMA_PATH"
    exit 1
fi

# Check if pnpm is available
if ! command -v pnpm &> /dev/null; then
    echo "Error: pnpm not found. Please install Node.js and pnpm."
    exit 1
fi

# Clean up temp directory if it exists
rm -rf "$TEMP_DIR"

# Generate Java client using openapi-generator-cli to temp directory
# We only want the models
pnpm dlx @openapitools/openapi-generator-cli generate \
  -i "$SCHEMA_PATH" \
  -g java \
  -o "$TEMP_DIR" \
  --global-property models,supportingFiles \
  --additional-properties=apiPackage=dev.unsent.api,modelPackage=dev.unsent.types,invokerPackage=dev.unsent,groupId=dev.unsent,artifactId=unsent,artifactVersion=1.0.1,library=okhttp-gson

# Clean output dir
rm -rf "$TYPES_OUTPUT_DIR"
mkdir -p "$TYPES_OUTPUT_DIR"

# Copy generated models
cp -r "$TEMP_DIR/src/main/java/dev/unsent/types/"* "$TYPES_OUTPUT_DIR/"

# Copy necessary supporting files
cp "$TEMP_DIR/src/main/java/dev/unsent/JSON.java" "src/main/java/dev/unsent/"
cp "$TEMP_DIR/src/main/java/dev/unsent/ApiException.java" "src/main/java/dev/unsent/"
cp "$TEMP_DIR/src/main/java/dev/unsent/StringUtil.java" "src/main/java/dev/unsent/"
cp "$TEMP_DIR/src/main/java/dev/unsent/Pair.java" "src/main/java/dev/unsent/"

# Cleanup
rm -rf "$TEMP_DIR"

echo ""
echo "✓ Types generated at ${TYPES_OUTPUT_DIR}"
