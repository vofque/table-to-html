# Тестовое задание

## Инструкции

Даны два файла data.csv и data.prn. Эти файлы необходимо сконвертировать в html с помощью кода, который вы реализуете.

Вы можете пушать изменения прямо в этот репозиторий. Обновите README инструкциями и допущениями.

## Ограничения

Завершите мини-проект за 5 рабочих дней. Используйте любые языки, инструменты, библиотеки.
После завершения проекта пришлите письмо на sayevsky@gmail.com.
Удачи!

## Вопросы

Если возникнут вопросы, присылайте sayevsky@gmail.com

# Раздел кандидата

## Описание

Реализованный проект - консольное приложение, считывающее таблицу из файла и генерирующее HTML-файл, содержащий эту таблицу.

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
