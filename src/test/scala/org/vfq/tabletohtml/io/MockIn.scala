package org.vfq.tabletohtml.io

private[io] case class MockIn(lines: Seq[String]) {

  private val lineIterator: Iterator[String] = lines.iterator

  def input(text: String): String = {
    if (lineIterator.hasNext) lineIterator.next else ""
  }
}
