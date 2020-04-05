## Описание

Реализованный проект - консольное Scala-приложение, считывающее таблицу из файла и генерирующее HTML-файл, содержащий эту таблицу.

Поддерживаемые форматы входных файлов:
- **.csv** с запятыми в качестве разделителя
- **.prn** с фиксированным размером колонок

Для поддержки нового формата необходимо создать подкласс класса `org.vfq.tabletohtml.io.Reader` и зарегистрировать его в `Reader.apply`.

## Использование

Сборка проекта:
```
mvn clean package
```
Запуск приложения:
```
java -jar target/vfq-table-to-html-1.0.0.jar <source-path> <source-encoding>
```
Примеры запуска:
```
java -jar target/vfq-table-to-html-1.0.0.jar data-in-ansi.csv Cp1252
java -jar target/vfq-table-to-html-1.0.0.jar data-in-utf8.prn UTF-8
```
Если на вход был передан файл **data.csv**, приложение сгенерирует и поместит в ту же директорию файлы:
- **data.html**
- **data.css**

## Допущения

Приложение воспринимает все данные как текст и не применяет специального форматирования для различных типов данных.
