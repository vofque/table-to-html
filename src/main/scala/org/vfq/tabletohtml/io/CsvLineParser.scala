package org.vfq.tabletohtml.io

import org.vfq.tabletohtml.entities.{Row, Table}

/**
 * Parser of a table from a sequence of lines in CSV format.
 */
private[io] case class CsvLineParser(output: String => Unit) extends LineParser {

  import CsvLineParser._

  /**
   * Parse a table from the sequence of lines in CSV format.
   * @return Some(table) if table was successfully parsed, None otherwise
   */
  private[io] override def parse(lines: Seq[String]): Option[Table] = {

    output("Parsing CSV file format.")

    val header: Row = Row(parseLine(lines.head))
    val entryLines: Seq[String] = lines.slice(1, lines.size)
    parseEntries(entryLines, header) match {
      case Some(entries) => Some(Table(header, entries))
      case None => None
    }
  }

  private def parseEntries(lines: Seq[String], header: Row): Option[Seq[Row]] = {
    val entries: Seq[Row] = lines.zipWithIndex.flatMap {
      case (line, index) => parseEntry(line, index, header)
    }
    if (entries.length == lines.length) {
      Some(entries)
    } else {
      output(s"Failed to parse ${lines.length - entries.length} entry lines.")
      None
    }
  }

  private def parseEntry(line: String, index: Int, header: Row): Option[Row] = {
    val values: Seq[String] = parseLine(line)
    if (values.length == header.values.length) {
      Some(Row(values))
    } else {
      output(s"Found ${values.length} instead of ${header.values.length} values in line ${index + 1}: '$line'.")
      None
    }
  }

  private def parseLine(line: String): Seq[String] = {
    line
      .split(delimiterPattern, -1)
      .map(_.trim.replaceAll(quotesPattern, ""))
  }
}

private[io] object CsvLineParser {

  /**
   * Create CSV line parser with standard output.
   * @return new CSV line parser
   */
  def apply(): CsvLineParser = {
    CsvLineParser(println(_))
  }

  private val delimiterPattern: String = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"
  private val quotesPattern: String = "^\"|\"$"
}