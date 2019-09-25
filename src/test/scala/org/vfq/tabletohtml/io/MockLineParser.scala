package org.vfq.tabletohtml.io

import org.vfq.tabletohtml.entities.{Row, Table}

private[io] case class MockLineParser() extends LineParser {

  private[io] def parse(lines: Seq[String]): Option[Table] = {

    val header: Row = Row(Seq(lines.head))
    val entries: Seq[Row] = lines
      .slice(1, lines.size)
      .map(line => Row(Seq(line)))

    Some(Table(header, entries))
  }
}
