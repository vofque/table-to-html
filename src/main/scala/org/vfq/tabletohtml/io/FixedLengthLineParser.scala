package org.vfq.tabletohtml.io

import java.util.regex.Pattern

import org.vfq.tabletohtml.entities.{Row, Table}

import scala.collection.mutable
import scala.io.StdIn.readLine

/**
 * Parser of a table from a sequence of lines in fixed-length format.
 */
private[io] case class FixedLengthLineParser(input: String => String, output: String => Unit)
  extends LineParser {

  import FixedLengthLineParser._

  /**
   * Parse a table from the sequence of lines in fixed-length format.
   * @return Some(table) if table was successfully parsed, None otherwise
   */
  private[io] override def parse(lines: Seq[String]): Option[Table] = {

    output("Parsing fixed-length file format.")

    val lineLength = lines.map(_.length).max
    val paddedLines: Seq[String] = lines.map(_.padTo(lineLength, ' '))

    val columnIntervals: Seq[Interval] = getColumnIntervals(paddedLines, lineLength)
    val rows: Seq[Row] = paddedLines.map(extractRowFromLine(_, columnIntervals))

    val header: Row = rows.head
    val entries: Seq[Row] = rows.slice(1, rows.size)

    Some(Table(header, entries))
  }

  private def getColumnIntervals(lines: Seq[String], lineLength: Int): Seq[Interval] = {
    val suggestedColumnIntervals: Seq[Interval] = suggestColumnIntervals(lines, lineLength)
    output("Suggested column limits:")
    val showLines: Seq[String] = lines.slice(0, showLineCount)
    printSplitLines(showLines, suggestedColumnIntervals)
    input("Are you OK with suggested column limits? (yes / no): ") match {
      case "yes" => suggestedColumnIntervals
      case _ => getUserColumnIntervals(showLines, lineLength)
    }
  }

  private def suggestColumnIntervals(lines: Seq[String], lineLength: Int): Seq[Interval] = {
    val columnLimitBuffer: mutable.Buffer[Int] = mutable.ArrayBuffer()
    var gapAtPrevious: Boolean = false
    for (index <- Range(0, lineLength)) {
      val gapAtCurrent = linesHaveGapAt(index, lines)
      if (gapAtPrevious && !gapAtCurrent) columnLimitBuffer.append(index)
      gapAtPrevious = gapAtCurrent
    }
    columnLimitsToIntervals(columnLimitBuffer, lineLength)
  }

  private def linesHaveGapAt(index: Int, lines: Seq[String]): Boolean = {
    lines
      .map(_.charAt(index).toString)
      .map(spacePattern.matcher(_).matches)
      .forall(identity)
  }

  private def getUserColumnIntervals(lines: Seq[String], lineLength: Int): Seq[Interval] = {
    val iterator: Iterator[Option[Seq[Interval]]] = Iterator.continually {
      readUserColumnIntervals(lines, lineLength)
    }
    iterator
      .dropWhile(_.isEmpty)
      .next()
      .get
  }

  private def readUserColumnIntervals(lines: Seq[String], lineLength: Int): Option[Seq[Interval]] = {
    printLinesWithScale(lines, lineLength)
    output(s"Please enter new column limits (1 to $lineLength or 'end' to stop):")
    val iterator: Iterator[Int] = Iterator.continually {
      getUserColumnLimit(lineLength)
    }
    val columnLimits: Seq[Int] = iterator.takeWhile(_ >= 0).toSeq.sortBy(identity)
    val columnIntervals: Seq[Interval] = columnLimitsToIntervals(columnLimits, lineLength)
    output("New column limits:")
    printSplitLines(lines, columnIntervals)
    input("Are you OK with new column limits? (yes / no): ") match {
      case "yes" => Some(columnIntervals)
      case _ => None
    }
  }

  private def getUserColumnLimit(lineLength: Int): Int = {
    val iterator: Iterator[Option[Int]] = Iterator.continually {
      readUserColumnLimit(lineLength)
    }
    iterator.dropWhile(_.isEmpty).next().get
  }

  private def readUserColumnLimit(lineLength: Int): Option[Int] = {
    val answer = input(s"End of the next column: ")
    if (intPattern.matcher(answer).matches) {
      val limit: Int = answer.toInt
      if ((limit >= 1) && (limit <= lineLength)) {
        Some(limit)
      } else {
        output(s"Value should be in bounds: (1, $lineLength).")
        None
      }
    } else {
      answer match {
        case "end" => Some(-1)
        case _ =>
          output(s"Please enter a number from 1 to $lineLength or 'end' to stop.")
          None
      }
    }
  }

  private def columnLimitsToIntervals(limits: Seq[Int], lineLength: Int): Seq[Interval] = {
    val starts: Seq[Int] = Seq(0) ++ limits
    val ends: Seq[Int] = limits ++ Seq(lineLength)
    starts.zip(ends).map {
      case (start, end) => Interval(start, end)
    }
  }

  private def extractRowFromLine(line: String, intervals: Seq[Interval]): Row = {
    Row(splitLine(line, intervals).map(_.trim()))
  }

  private def splitLine(line: String, intervals: Seq[Interval]): Seq[String] = {
    intervals.map(interval => line.substring(interval.start, interval.end))
  }

  private def printLinesWithScale(lines: Seq[String], lineLength: Int): Unit = {
    output("")
    printScale(lineLength)
    lines.foreach(output)
    output(".....")
  }

  private def printScale(lineLength: Int): Unit = {
    val decimalCount: Int = lineLength / 10
    val scaleLines: Seq[String] = Seq(
      Range(0, decimalCount).map(v => (v + 1) * 10).map("%10d".format(_)).mkString,
      ((("." * 9) + "|") * decimalCount).padTo(lineLength, '.')
    )
    scaleLines.foreach(output)
  }

  private def printSplitLines(lines: Seq[String], intervals: Seq[Interval]): Unit = {
    output("")
    lines.map(splitLine(_, intervals).mkString("|")).foreach(output)
    output(".....")
  }
}

private[io] object FixedLengthLineParser {

  private val showLineCount: Int = 10
  private val spacePattern: Pattern = "\\s".r.pattern
  private val intPattern: Pattern = "\\d+".r.pattern

  private case class Interval(start: Int, end: Int)

  /**
   * Create fixed-length line parser with standard input and output.
   * @return new fixed-length line parser
   */
  def apply(): FixedLengthLineParser = {
    FixedLengthLineParser(readLine(_), println(_))
  }
}
