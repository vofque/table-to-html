package org.vfq.tabletohtml.util

import scala.util.matching.Regex

object IOUtil {

  private val filePathRegex: Regex = "(.*)\\.([^.]+)".r

  def extractFileExtension(filePath: String): String = {
    filePathRegex.replaceAllIn(filePath, "$2")
  }

  def replaceFileExtension(filePath: String, extension: String): String = {
    val filePathBase: String = filePathRegex.replaceAllIn(filePath, "$1")
    s"$filePathBase.$extension"
  }

  def using[T, R <: { def close(): Unit }](resource: R)(function: R => T): T =
    try {
      function(resource)
    } finally {
      resource.close()
    }
}
