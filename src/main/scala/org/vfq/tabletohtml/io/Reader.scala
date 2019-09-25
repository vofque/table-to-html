package org.vfq.tabletohtml.io

import org.vfq.tabletohtml.entities.Table
import org.vfq.tabletohtml.util.IOUtil

/**
 * Reader of a table from a file.
 */
trait Reader {

  /**
   * Read a table from the file.
   * @return Some(table) if table was successfully read, None otherwise
   */
  def read(): Option[Table]
}

object Reader {

  /**
   * Create proper reader for the specified file extension.
   * @param path path to the input file
   * @param encoding input file encoding
   * @return new reader
   */
  def apply(path: String, encoding: String): Option[Reader] = {
    val extension: String = IOUtil.extractFileExtension(path)
    extension match {
      case "csv" =>
        Some(TextReader(path, encoding, CsvLineParser()))
      case "prn" =>
        Some(TextReader(path, encoding, FixedLengthLineParser()))
      case _ =>
        println(s"File format '$extension' not supported yet.")
        None
    }
  }
}