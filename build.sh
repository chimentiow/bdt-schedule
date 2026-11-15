#!/bin/bash
# Компіляція програми та складання bdt.jar (потрібен JDK 17 або новіший)
set -e
cd "$(dirname "$0")"
rm -rf build
mkdir -p build/classes
javac -encoding UTF-8 -cp "lib/h2-2.2.224.jar" -d build/classes $(find src/main/java -name "*.java")
cp src/main/resources/*.sql build/classes/
printf 'Main-Class: bdt.Main\nClass-Path: lib/h2-2.2.224.jar\n' > build/manifest.txt
jar cfm bdt.jar build/manifest.txt -C build/classes .
echo "Готово: bdt.jar (запуск: java -jar bdt.jar)"
