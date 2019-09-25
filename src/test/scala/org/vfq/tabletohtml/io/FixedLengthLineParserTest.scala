package org.vfq.tabletohtml.io

import org.vfq.tabletohtml.entities.{Row, Table}

class FixedLengthLineParserTest extends LineParserTestBase {

  private def test(inputPath: String, answers: Seq[String], expectedTableOption: Option[Table]): Unit = {
    val in: MockIn = MockIn(answers)
    val out: DroppedOut = DroppedOut()
    val parser: LineParser = FixedLengthLineParser(in.input, out.output)
    test(inputPath, parser, expectedTableOption)
  }

  test("Fixed length parser test: case A") {
    val answers: Seq[String] = Seq("yes")
    val expectedTableOption: Option[Table] = Some(
      Table(
        Row(Seq("first", "name", "last name", "age", "comment", "", "", "")),
        Seq(
          Row(Seq("Jack", "", "Johnson", "22", "a nice guy", "", "", "")),
          Row(Seq("Nick", "", "Jackson", "44", "not really", "a", "nice", "guy")),
          Row(Seq("Sam", "", "Nickson", "2", "", "", "", ""))
        )
      )
    )
    test("src/test/resources/data.prn", answers, expectedTableOption)
  }

  test("Fixed length parser test: case B") {
    val answers: Seq[String] = Seq("no", "13", "23", "27", "end", "yes")
    val expectedTableOption: Option[Table] = Some(
      Table(
        Row(Seq("first name", "last name", "age", "comment")),
        Seq(
          Row(Seq("Jack", "Johnson", "22", "a nice guy")),
          Row(Seq("Nick", "Jackson", "44", "not really a nice guy")),
          Row(Seq("Sam", "Nickson", "2", ""))
        )
      )
    )
    test("src/test/resources/data.prn", answers, expectedTableOption)
  }

  test("Fixed length parser test: case C") {
    val answers: Seq[String] = Seq("no", "19", "25", "end", "yes")
    val expectedTableOption: Option[Table] = Some(
      Table(
        Row(Seq("first name   last n", "ame ag", "e comment")),
        Seq(
          Row(Seq("Jack         Johnso", "n    2", "2 a nice guy")),
          Row(Seq("Nick         Jackso", "n    4", "4 not really a nice guy")),
          Row(Seq("Sam          Nickso", "n", "2"))
        )
      )
    )
    test("src/test/resources/data.prn", answers, expectedTableOption)
  }

  test("Fixed length parser test: case D") {
    val answers: Seq[String] = Seq("no", "5", "20", "end", "no", "2", "end", "no", "23", "27", "end", "yes")
    val expectedTableOption: Option[Table] = Some(
      Table(
        Row(Seq("first name   last name", "age", "comment")),
        Seq(
          Row(Seq("Jack         Johnson", "22", "a nice guy")),
          Row(Seq("Nick         Jackson", "44", "not really a nice guy")),
          Row(Seq("Sam          Nickson", "2", ""))
        )
      )
    )
    test("src/test/resources/data.prn", answers, expectedTableOption)
  }
}
