# ІС «Будинок дитячої творчості» – модуль «Розклад і облік занять»

Курсовий проект з дисципліни «Розробка інформаційних систем» (варіант 14).
Програма веде тижневий розклад занять секцій будинку дитячої творчості з перевіркою
зайнятості керівників і приміщень, облік фактичних занять і відвідування, довідники
секцій, керівників, учасників і приміщень, облік заходів та оплати занять.

## Засоби реалізації

- Java 17, графічний інтерфейс Swing
- вбудована СУБД H2 2.2.224 (файл `lib/h2-2.2.224.jar`)

## Структура проекту

| Каталог | Вміст |
|---|---|
| `src/main/java/bdt/model` | класи предметної області (розклад, запис розкладу, інтервал часу, секція тощо) |
| `src/main/java/bdt/controller` | контролери прецедентів |
| `src/main/java/bdt/dao` | інтерфейси та класи доступу до даних |
| `src/main/java/bdt/db` | з’єднання з базою даних |
| `src/main/java/bdt/ui` | вікна та панелі інтерфейсу користувача |
| `src/main/java/bdt/ui/form` | поля форм довідників |
| `src/main/resources` | `schema.sql` (структура БД), `data.sql` (тестові дані), `queries.sql` (запити) |
| `src/test/java/bdt/test` | драйвери та заглушки курсового проекту |
| `src/test/java/bdt/test/junit` | автоматизовані тести JUnit |
| `load-test` | тест-план Apache JMeter для навантажувального тестування |
| `lib` | бібліотеки |

## Збирання та запуск

Потрібен JDK 17 або новіший (перевірка: `java -version`).

```
./build.sh        # macOS / Linux  (у Windows: build.bat)
java -jar bdt.jar # або подвійне клацання на run.command / run.bat
```

Під час першого запуску поруч створюється база `bdt_db.mv.db` з тестовими даними.
Щоб повернути початкові дані, закрийте програму і видаліть цей файл.

## Тестування

```
./test.sh                    # усі тести
./test.sh drivers            # драйвери курсового проекту (39 тестових варіантів)
./test.sh junit              # тести JUnit (202 тести)
./test.sh junit НазваКласу     # окремий клас тестів, наприклад SetTimeCauseEffectTest
```

Тести JUnit 4 лежать у пакеті `bdt.test.junit`:

| Метод проектування тестів | Класи тестів | Тестів |
|---|---|---|
| Таблиці рішень | `LessonDecisionTableTest`, `AddEntryDecisionTableTest` | 24 |
| Функціональні діаграми | `SetTimeCauseEffectTest`, `SetTimeResetsRoomTest` | 14 |
| Класи еквівалентності | `TimeSlotEquivalenceTest`, `FeeEquivalenceTest`, `LessonInputEquivalenceTest`, `FormFieldEquivalenceTest`, `DatabaseConstraintsEquivalenceTest` | 77 |
| Аналіз граничних значень | `TimeSlotBoundaryTest`, `OverlapBoundaryTest`, `CapacityAndFeeBoundaryTest`, `DatabaseBoundaryTest`, `TopicLengthBoundaryTest` | 87 |

Контролери тестуються із заглушками сховищ (`TestData`), обмеження бази даних – на базі H2
в оперативній пам’яті (`TestDatabase`). Бібліотеки JUnit 4.13.2 і Hamcrest 1.3 лежать у `lib`.

## Навантажувальне тестування

Тест-план Apache JMeter і скрипт запуску лежать у папці `load-test`, опис – у `load-test/README.md`.
