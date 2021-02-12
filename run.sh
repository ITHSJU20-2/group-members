#!/bin/bash
# Get the current version of the application
# And the current OS that's running because different OS's require different module separators
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
java --module-path target/core-"$VERSION".jar"$SEP"target/modules -m core/se.iths.groupmembers.Main

#HERE IS THE WINDOWS VERSION
#java --module-path target/core-0.1.0.jar;target/modules -m core/se.iths.groupmembers.Main