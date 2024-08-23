@echo off
mvn install:install-file -Dfile=appian-selenium-api-24.2.jar -DgroupId=appian -DartifactId=appian-selenium-api -Dversion=24.2 -Dpackaging=jar

IF %ERRORLEVEL% NEQ 0 (
    echo Failed to install the JAR file.
) ELSE (
    echo JAR file has been installed to your local Maven repository.
)

PAUSE
