package org.vfq.tabletohtml.io

import org.vfq.tabletohtml.entities.Table
import org.vfq.tabletohtml.util.IOUtil

import scala.io.Source

/**
 * Reader of a table from a text file.
 * @param path path to the input file
 * @param encoding input file encoding
 * @param lineParser parser of input lines
 */
private[io] case class TextReader(path: String, encoding: String, lineParser: LineParser) extends Reader {

  /**
   * Read a table from the text file.
   * @return Some(table) if table was successfully read, None otherwise
   */
  override def read(): Option[Table] = {

    println(s"Reading file $path as $encoding.")

    IOUtil.using(Source.fromFile(path.toString, encoding)) {
      source => {
        val lines: Seq[String] = source.getLines().toSeq
        if (lines.nonEmpty) {
          lineParser.parse(lines)
        } else {
          println("Empty file.")
          None
        }
      }
    }
  }
}
