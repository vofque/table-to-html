## Description

This is a test console Scala application, reading a table from a file and generating an HTML containing this table.

Supported input file formats:
- **.csv** with comma delimiters
- **.prn** with fixed column width

To support a new input file format you should createa subclass of `org.vfq.tabletohtml.io.Reader` and register it in `Reader.apply`.

## Usage

Build:
```
mvn clean package
```
Launch:
```
java -jar target/vfq-table-to-html-1.0.0.jar <source-path> <source-encoding>
```
Examples:
```
java -jar target/vfq-table-to-html-1.0.0.jar data-in-ansi.csv Cp1252
java -jar target/vfq-table-to-html-1.0.0.jar data-in-utf8.prn UTF-8
```
