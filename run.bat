@echo off

for /f %%i in ('mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec') do set VERSION=%%i
clear
mvn clean package
java --module-path target/core-%VERSION%.jar;target/modules -m core/se.iths.groupmembers.Main