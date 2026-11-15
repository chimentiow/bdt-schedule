@echo off
cd /d "%~dp0"
if not exist bdt.jar call build.bat
java -jar bdt.jar
