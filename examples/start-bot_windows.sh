#!/bin/bash

# Java for Windows supports java -cp "A;B;C", which is not compatible with Java for Linux

XATKIT_OPENAPI=../runtime/target/openapi-platform-jar-with-dependencies.jar

java -cp "$XATKIT;$XATKIT_OPENAPI" com.xatkit.Xatkit OpenAPIExample.properties
