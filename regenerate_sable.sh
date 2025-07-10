#!/bin/bash
# Regenerate SableCC-generated code and move .dat files to resources

set -e

# Set working directories
GEN_DIR="/workspaces/arborate-java-compiler/src/main/java/org/linguate/arboratecompiler"
RES_LEXER="/workspaces/arborate-java-compiler/src/main/resources/org/linguate/arboratecompiler/lexer"
RES_PARSER="/workspaces/arborate-java-compiler/src/main/resources/org/linguate/arboratecompiler/parser"
SABLECC_JAR="/workspaces/arborate-java-compiler/lib/sablecc.jar"
SPEC_FILE="../resources/org/linguate/arboratecompiler/arborate_specification.txt"

# Remove generated folders
for d in analysis lexer node parser; do
  rm -rf "$GEN_DIR/$d"
done

# Remove old .dat files from resources
rm -f "$RES_LEXER/lexer.dat"
rm -f "$RES_PARSER/parser.dat"

# Run SableCC from src/main/java (so -d . puts org/linguate/arboratecompiler/... in the right place)
cd /workspaces/arborate-java-compiler/src/main/java
java -jar "$SABLECC_JAR" "$SPEC_FILE" -d .

# Move .dat files to resources
mv "$GEN_DIR/lexer/lexer.dat" "$RES_LEXER/lexer.dat"
mv "$GEN_DIR/parser/parser.dat" "$RES_PARSER/parser.dat"
