#!/bin/bash
# Get the version of the project and the right separator
VERSION=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec)
OS=$(uname)

SEP=";" # For some reason Windows require a semicolon which breaks the script on MacOS
if [[ "$OS =~ ^darwin" ]];
 then
   SEP=":"
fi

clear

# Build and run the application
mvn clean package
java -p core/target/core-"$VERSION".jar"$SEP"spi/target/spi-"$VERSION".jar"$SEP"router/target/router-"$VERSION".jar \
 -m core/se.iths.groupmembers.Main