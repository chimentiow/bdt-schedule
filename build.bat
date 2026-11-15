@echo off
rem Компіляція програми та складання bdt.jar (потрібен JDK 17 або новіший)
cd /d "%~dp0"
if exist build rmdir /s /q build
mkdir build\classes
dir /s /b src\main\java\*.java > build\sources.txt
javac --release 17 -encoding UTF-8 -cp "lib\h2-2.2.224.jar" -d build\classes @build\sources.txt || exit /b 1
copy /y src\main\resources\*.sql build\classes\ > nul
(echo Main-Class: bdt.Main& echo Class-Path: lib/h2-2.2.224.jar) > build\manifest.txt
jar cfm bdt.jar build\manifest.txt -C build\classes .
echo Готово: bdt.jar (запуск: java -jar bdt.jar)
