package org.vfq.tabletohtml.io

import org.scalatest.FunSuite
import org.vfq.tabletohtml.entities.{Row, Table}

class ReaderTest extends FunSuite {

  private def test(sourcePath: String, expectedTableOption: Option[Table]): Unit = {
    val actualTableOption: Option[Table] = TextReader(sourcePath, "UTF-8", MockLineParser()).read()
    assert(actualTableOption.equals(expectedTableOption))
  }

  test("Reader test: valid case") {
    val expectedTableOption: Option[Table] = Some(
      Table(
        Row(Seq("header")),
        Seq(
          Row(Seq("line one")),
          Row(Seq("line two")),
          Row(Seq("line three"))
        )
      )
    )
    test("src/test/resources/source-valid.txt", expectedTableOption)
  }

  test("Reader test: empty case") {
    test("src/test/resources/source-empty.txt", None)
  }
}
