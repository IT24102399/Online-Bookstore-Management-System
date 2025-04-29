@echo off
echo =====================================================
echo     ONLINE BOOKSTORE MANAGEMENT SYSTEM LAUNCHER
echo =====================================================
echo.

rem Check and set JAVA_HOME if not already set
IF "%JAVA_HOME%"=="" (
    echo JAVA_HOME not set. Attempting to find Java installation...

    rem Try to find Java installation in Program Files
    IF EXIST "C:\Program Files\Java\jdk*" (
        FOR /D %%i IN ("C:\Program Files\Java\jdk*") DO (
            echo Found Java at: %%i
            SET "JAVA_HOME=%%i"
            goto :java_found
        )
    )

    rem Try to find Java installation in Program Files (x86)
    IF EXIST "C:\Program Files (x86)\Java\jdk*" (
        FOR /D %%i IN ("C:\Program Files (x86)\Java\jdk*") DO (
            echo Found Java at: %%i
            SET "JAVA_HOME=%%i"
            goto :java_found
        )
    )

    echo Could not find Java installation automatically.
    echo Please set JAVA_HOME manually or install Java 11 or higher.
    pause
    exit /b 1
)

:java_found
echo Using JAVA_HOME: %JAVA_HOME%

rem Create data directory if it doesn't exist
echo Creating data directory for file storage...
mkdir data 2>nul

rem Remove existing JSON files to start fresh
echo Removing existing data files to start fresh...
del /q data\*.json 2>nul

rem Create empty JSON files for data storage
echo Creating empty JSON data files...
echo [] > data\users.json
echo [] > data\books.json
echo [] > data\orders.json
echo [] > data\payments.json
echo [] > data\reviews.json

echo.
echo Building and running the application...
echo.

rem Set additional JVM options for JDK 20 compatibility
set MAVEN_OPTS=-Xmx512m --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.util.concurrent=ALL-UNNAMED

echo Using JDK 20 with special options for compatibility

rem Run the application using Maven wrapper
call mvnw.cmd spring-boot:run

echo.
echo =====================================================
echo Application terminated. Thank you for using the
echo Online Bookstore Management System!
echo =====================================================

pause
