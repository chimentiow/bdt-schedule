#!/bin/bash
# Компіляція та запуск тестів-драйверів курсового проекту
set -e
cd "$(dirname "$0")"
./build.sh > /dev/null
CP="build/classes:lib/h2-2.2.224.jar"
mkdir -p build/test-classes
javac -encoding UTF-8 -cp "$CP" -d build/test-classes $(find src/test/java -name "*.java")
RUN="java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -cp build/test-classes:$CP"
$RUN bdt.test.ScheduleBasisPathDriver
$RUN bdt.test.ScheduleControllerDriver
$RUN bdt.test.IntegrationDriver
