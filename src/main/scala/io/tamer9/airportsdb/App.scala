package io.tamer9.airportsdb

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import io.tamer9.airportsdb.Geocoder._
import io.tamer9.airportsdb.JsonParser._

object App {

  def main(args: Array[String]): Unit = {

    if (args.length == 0) {
      println("use jsonInput or csvInput")
      sys.exit(1)
    }

    args(0) match {
      case "jsonInput" => produceFromJsonInput()
      case "csvInput" => produceFromCSVInput()
      case _ => println("use jsonInput or csvInput")
    }

  }

  def produceFromCSVInput(): Unit = {
    val bufferedSource = scala.io.Source.fromResource("GlobalAirportDatabase.txt")
    val airports: List[Airport] = bufferedSource.getLines().map(CsvParser.fromLine).toList.flatten.map(fromAirportCSV)
    reverseGeoCodeToFile(airports)
  }

  def produceFromJsonInput(): Unit = {
    val bufferedSource = scala.io.Source.fromResource("airports.json")
    val either = fromJsonString(bufferedSource.mkString)
    bufferedSource.close()
    either.fold(
      error => println(s"error $error"),
      airports => reverseGeoCodeToFile(airports)
    )
  }

  def reverseGeoCodeToFile(airports: List[Airport]): Unit = {
    val withGoogleCoords = airports.map(reverseGeocode)
    val str = toJsonString(withGoogleCoords)
    Files.write(Paths.get("airports_json_google.txt"), str.getBytes(StandardCharsets.UTF_8))
  }

}
