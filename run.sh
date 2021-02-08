#!/bin/bash
clear
# Build and run the application
mvn clean install compile package
java --module-path target/modules -m core/se.iths.groupmembers.Main