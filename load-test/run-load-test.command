#!/bin/bash
# Навантажувальне тестування ІС «Будинок дитячої творчості» в Apache JMeter
# (режим без графічного інтерфейсу, чотири рівні навантаження).
#
# Використання:
#   bash run-load-test.command [шлях до папки apache-jmeter-5.6.3]
# Якщо шлях не вказано, папка apache-jmeter-* шукається поруч із проектом,
# у «Завантаженнях» і на робочому столі.
# Рівні навантаження можна змінити: LEVELS="1 5 20" bash run-load-test.command
set -e
HERE="$(cd "$(dirname "$0")" && pwd)"
PROJECT="$(dirname "$HERE")"
LEVELS="${LEVELS:-1 10 25 50}"
LOOPS="${LOOPS:-30}"

JMETER_DIR="${1:-$JMETER_HOME}"
if [ -z "$JMETER_DIR" ]; then
  for candidate in "$HERE"/apache-jmeter-* "$PROJECT"/apache-jmeter-* "$PROJECT"/../apache-jmeter-* \
                   "$PROJECT"/../*/apache-jmeter-* "$HOME"/Downloads/apache-jmeter-* \
                   "$HOME"/Desktop/apache-jmeter-* "$HOME"/apache-jmeter-*; do
    if [ -f "$candidate/bin/ApacheJMeter.jar" ]; then
      JMETER_DIR="$candidate"
      break
    fi
  done
fi
if [ ! -f "$JMETER_DIR/bin/ApacheJMeter.jar" ]; then
  echo "Не знайдено Apache JMeter. Завантажте архів apache-jmeter-5.6.3.zip з https://jmeter.apache.org/download_jmeter.cgi,"
  echo "розпакуйте його і вкажіть шлях до папки: bash run-load-test.command /шлях/до/apache-jmeter-5.6.3"
  exit 1
fi
echo "JMeter: $JMETER_DIR"
java -version

# Програма та драйвер H2 мають бути в папці lib JMeter: тест-план викликає класи програми.
[ -f "$PROJECT/bdt.jar" ] || "$PROJECT/build.sh"
cp "$PROJECT/bdt.jar" "$PROJECT/lib/h2-2.2.224.jar" "$JMETER_DIR/lib/"

RESULTS="$HERE/results"
mkdir -p "$RESULTS"
for THREADS in $LEVELS; do
  OUT="$RESULTS/threads-$THREADS"
  rm -rf "$OUT"
  mkdir -p "$OUT"
  RAMP=$(( THREADS / 5 + 1 ))
  echo
  echo "=== Користувачів: $THREADS, повторень сценарію: $LOOPS, наростання: $RAMP с ==="
  java -Xmx1g -jar "$JMETER_DIR/bin/ApacheJMeter.jar" -n -t "$HERE/bdt-load-test.jmx" \
       -JprojectDir="$PROJECT" -Jthreads="$THREADS" -Jrampup="$RAMP" -Jloops="$LOOPS" \
       -l "$OUT/results.jtl" -j "$OUT/jmeter.log" -e -o "$OUT/report"
done
echo
echo "Готово. Результати: $RESULTS"
echo "Звіт для кожного рівня навантаження: threads-N/report/index.html"
