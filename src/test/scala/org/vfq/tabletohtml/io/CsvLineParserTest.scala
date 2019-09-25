package org.vfq.tabletohtml.io

import org.vfq.tabletohtml.entities.{Row, Table}

class CsvLineParserTest extends LineParserTestBase {

  private def test(inputPath: String, expectedTableOption: Option[Table]): Unit = {
    val out: DroppedOut = DroppedOut()
    val parser: LineParser = CsvLineParser(out.output)
    test(inputPath, parser, expectedTableOption)
  }

  test("CSV parser test: valid case") {
    val expectedTableOption: Option[Table] = Some(
      Table(
        Row(Seq("first name", "last name", "age", "comment")),
        Seq(
          Row(Seq("Jack", "Johnson, J", "22", "a nice guy")),
          Row(Seq("Nick", "Jackson", "44", "not really a nice guy")),
          Row(Seq("Sam", "Nickson", "2", ""))
        )
      )
    )
    test("src/test/resources/data-valid.csv", expectedTableOption)
  }

  test("CSV parser test: invalid case") {
    test("src/test/resources/data-invalid.csv", None)
  }
}
