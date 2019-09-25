package org.vfq.tabletohtml.io

import org.scalatest.FunSuite
import org.vfq.tabletohtml.entities.Table
import org.vfq.tabletohtml.util.IOUtil

import scala.io.Source

private[io] abstract class LineParserTestBase extends FunSuite {

  private[io] def test(inputPath: String, parser: LineParser, expectedTableOption: Option[Table]): Unit = {
    IOUtil.using(Source.fromFile(inputPath)) {
      source => {
        val lines: Seq[String] = source.getLines().toSeq
        val actualTableOption: Option[Table] = parser.parse(lines)
        assert(actualTableOption.equals(expectedTableOption))
      }
    }
  }
}
