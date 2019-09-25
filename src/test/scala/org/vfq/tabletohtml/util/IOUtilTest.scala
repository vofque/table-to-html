package org.vfq.tabletohtml.util

import org.scalatest.FunSuite

class IOUtilTest extends FunSuite {

  test("Extract file extension test") {
    val actual: String = IOUtil.extractFileExtension("/complicated.path/to/some.file.txt")
    val expected: String = "txt"
    assert(actual.equals(expected))
  }

  test("Replace file extension test") {
    val actual: String = IOUtil.replaceFileExtension("/complicated.path/to/some.file.txt", "csv")
    val expected: String = "/complicated.path/to/some.file.csv"
    assert(actual.equals(expected))
  }
}
