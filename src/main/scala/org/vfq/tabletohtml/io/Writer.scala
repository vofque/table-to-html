package org.vfq.tabletohtml.io

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}

import org.vfq.tabletohtml.entities.Table
import org.vfq.tabletohtml.util.{HtmlKit, IOUtil}

import scala.io.Source
import scala.xml.{Elem, PrettyPrinter}

/**
 * Writer of a table into an HTML file.
 * @param path path to the output file
 */
case class Writer(path: String) {

  private val cssPath: String = IOUtil.replaceFileExtension(path, "css")
  private val printer: PrettyPrinter = new PrettyPrinter(180, 4)

  import Writer._

  /**
   * Write the specified table into the HTML file.
   * @param table table to write
   */
  def write(table: Table): Unit = {

    println(s"Writing file $path.")

    // Write HTML file
    val htmlDocument: Elem = HtmlKit.makeDocument(table, Paths.get(cssPath).getFileName.toString)
    val contents: String = printer.format(htmlDocument)
    Files.write(Paths.get(path), contents.getBytes(StandardCharsets.UTF_8))

    // Write CSS file
    IOUtil.using(Source.fromInputStream(getClass.getResourceAsStream(cssResourceName))) {
      source => Files.write(Paths.get(cssPath), source.mkString.getBytes(StandardCharsets.UTF_8))
    }
  }
}

object Writer {

  private val cssResourceName: String = "/table.css"
}
