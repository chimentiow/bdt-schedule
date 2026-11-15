#!/bin/bash
# Компіляція та запуск усіх тестів: драйвери курсового проекту і тести JUnit.
# ./test.sh                 – усі тести
# ./test.sh drivers         – лише драйвери курсового проекту
# ./test.sh junit [Клас...] – лише тести JUnit (усі або вказані класи)
set -e
cd "$(dirname "$0")"
./build.sh > /dev/null
CP="build/classes:lib/h2-2.2.224.jar:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar"
mkdir -p build/test-classes
javac --release 17 -encoding UTF-8 -cp "$CP" -d build/test-classes $(find src/test/java -name "*.java")
RUN="java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Djava.awt.headless=true -cp build/test-classes:$CP"
MODE="${1:-all}"
if [ "$MODE" = "all" ] || [ "$MODE" = "drivers" ]; then
  $RUN bdt.test.ScheduleBasisPathDriver
  $RUN bdt.test.ScheduleControllerDriver
  $RUN bdt.test.IntegrationDriver
fi
if [ "$MODE" = "all" ] || [ "$MODE" = "junit" ]; then
  shift || true
  $RUN bdt.test.junit.RunTests "$@"
fi
