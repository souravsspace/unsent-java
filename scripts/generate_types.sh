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

# Generate Java client using openapi-generator-cli to temp directory
# We only want the models
pnpm dlx @openapitools/openapi-generator-cli generate \
  -i "$SCHEMA_PATH" \
  -g java \
  -o "$TEMP_DIR" \
  --global-property models,supportingFiles \
  --additional-properties=apiPackage=dev.unsent.api,modelPackage=dev.unsent.types,invokerPackage=dev.unsent,groupId=dev.unsent,artifactId=unsent,artifactVersion=1.0.1,library=okhttp-gson

# Ensure temp dir is removed on exit
trap 'rm -rf "$TEMP_DIR"' EXIT

# Clean output dir
rm -rf "$TYPES_OUTPUT_DIR"
mkdir -p "$TYPES_OUTPUT_DIR"

# Combine all types into a single file
echo "Combining generated types into single file..."

INPUT_DIR="$TEMP_DIR/src/main/java/dev/unsent/types"
TYPES_FILE="$TYPES_OUTPUT_DIR/Types.java"
PACKAGE_NAME="dev.unsent.types"

# Start the file with package declaration
echo "package $PACKAGE_NAME;" > "$TYPES_FILE"
echo "" >> "$TYPES_FILE"

# Collect, filter, sort, and deduplicate imports
# We filter out imports from the same package to avoid circular references/redundancy
if [ -d "$INPUT_DIR" ]; then
    grep -h "^import " "$INPUT_DIR"/*.java | grep -v "${PACKAGE_NAME}." | sort | uniq >> "$TYPES_FILE"
fi

echo "" >> "$TYPES_FILE"
echo "public class Types {" >> "$TYPES_FILE"
echo "" >> "$TYPES_FILE"

# Process each java file to make classes static and strip package/imports
if [ -d "$INPUT_DIR" ]; then
    for f in "$INPUT_DIR"/*.java; do
        if [ -f "$f" ]; then
             # Remove package lines, import lines
             # Convert public class/enum to public static class/enum
             # Append to output file
             cat "$f" | \
             sed '/^package /d' | \
             sed '/^import /d' | \
             sed 's/public class /public static class /g' | \
             sed 's/public abstract class /public static abstract class /g' | \
             sed 's/public enum /public static enum /g' >> "$TYPES_FILE"
             
             echo "" >> "$TYPES_FILE"
        fi
    done
fi

echo "}" >> "$TYPES_FILE"

# Copy necessary supporting files
cp "$TEMP_DIR/src/main/java/dev/unsent/JSON.java" "src/main/java/dev/unsent/"
cp "$TEMP_DIR/src/main/java/dev/unsent/ApiException.java" "src/main/java/dev/unsent/"
cp "$TEMP_DIR/src/main/java/dev/unsent/StringUtil.java" "src/main/java/dev/unsent/"
cp "$TEMP_DIR/src/main/java/dev/unsent/Pair.java" "src/main/java/dev/unsent/"

# Patch supporting files to reference Types.ClassName
# We replace dev.unsent.types.ClassName with dev.unsent.types.Types.ClassName
# Using temp file to be compatible with both GNU and BSD sed
sed 's/dev\.unsent\.types\./dev.unsent.types.Types./g' "src/main/java/dev/unsent/JSON.java" > "src/main/java/dev/unsent/JSON.java.tmp" && mv "src/main/java/dev/unsent/JSON.java.tmp" "src/main/java/dev/unsent/JSON.java"

# Cleanup happens via trap, but we can also do it explicitly here if successful
rm -rf "$TEMP_DIR"

echo ""
echo "✓ Types generated at ${TYPES_OUTPUT_DIR}"
