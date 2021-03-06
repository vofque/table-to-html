package org.vfq.tabletohtml

import org.vfq.tabletohtml.io.{Reader, Writer}
import org.vfq.tabletohtml.util.IOUtil

object Main extends App {

  require(args.length == 2, "App arguments: <source-path> <source-encoding>")

  val sourcePath: String = args(0)
  val sourceEncoding: String = args(1)

  val resultPath: String = IOUtil.replaceFileExtension(sourcePath, "html")

  Reader(sourcePath, sourceEncoding).foreach {
    _.read().foreach {
      Writer(resultPath).write(_)
    }
  }
}
