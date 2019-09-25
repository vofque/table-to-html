package org.vfq.tabletohtml.io

import java.nio.file.{Files, Paths}

import org.scalatest.FunSuite
import org.vfq.tabletohtml.entities.{Row, Table}

import scala.collection.JavaConverters._

class WriterTest extends FunSuite {

  private val expectedPath: String = "src/test/resources/expected-result.html"
  private val actualPath: String = "src/test/resources/actual-result.html"
  private val actualCssPath: String = "src/test/resources/actual-result.css"

  test("Writer test") {

    val table: Table = Table(
      Row(Seq("name", "age")),
      Seq(
        Row(Seq("John", "23")),
        Row(Seq("Jack", "34")),
      )
    )

    Writer(actualPath).write(table)

    val expectedLines: Seq[String] = Files.readAllLines(Paths.get(expectedPath)).asScala.map(_.trim)
    val actualLines: Seq[String] = Files.readAllLines(Paths.get(actualPath)).asScala.map(_.trim)

    assert(actualLines.equals(expectedLines))

    Files.delete(Paths.get(actualPath))
    Files.delete(Paths.get(actualCssPath))
  }
}
