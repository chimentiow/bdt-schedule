#!/bin/bash
cd "$(dirname "$0")"
[ -f bdt.jar ] || ./build.sh
java -jar bdt.jar
