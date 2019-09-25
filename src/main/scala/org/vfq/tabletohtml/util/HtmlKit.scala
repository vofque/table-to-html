package org.vfq.tabletohtml.util

import org.vfq.tabletohtml.entities.{Row, Table}

import scala.xml.Elem

object HtmlKit {

  def makeDocument(table: Table, cssFileName: String): Elem = {
    <html>
      <head>
        <link rel="stylesheet" type="text/css" href={cssFileName}/>
      </head>
      <body>{makeTable(table)}</body>
    </html>
  }

  def makeTable(table: Table): Elem = {
    <table>{Seq(makeHeaderRow(table.header)) ++ table.entries.map(makeEntryRow)}</table>
  }

  def makeHeaderRow(row: Row): Elem = {
    <tr>{row.values.map(value => <th>{value}</th>)}</tr>
  }

  def makeEntryRow(row: Row): Elem = {
    <tr>{row.values.map(value => <td>{value}</td>)}</tr>
  }
}
