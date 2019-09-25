package org.vfq.tabletohtml.io

import org.vfq.tabletohtml.entities.Table

/**
 * Parser of a table from a sequence of lines.
 */
private[io] trait LineParser {

  /**
   * Parse a table from the sequence of lines.
   * @return Some(table) if table was successfully parsed, None otherwise
   */
  private[io] def parse(lines: Seq[String]): Option[Table]
}
