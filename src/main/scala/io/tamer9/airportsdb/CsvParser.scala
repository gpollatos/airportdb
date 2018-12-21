package io.tamer9.airportsdb

// http://www.partow.net/miscellaneous/airportdatabase/
// 01	ICAO Code	String (3-4 chars, A - Z)
// 02	IATA Code	String (3 chars, A - Z)
// 03	Airport Name	String
// 04	City/Town	String
// 05	Country	String
// 06	Latitude Degrees	Integer [0,360]
// 07	Latitude Minutes	Integer [0,60]
// 08	Latitude Seconds	Integer [0,60]
// 09	Latitude Direction	Char (N or S)
// 10	Longitude Degrees	Integer [0,360]
// 11	Longitude Minutes	Integer [0,60]
// 12	Longitude Seconds	Integer [0,60]
// 13	Longitude Direction	Char (E or W)
// 14	Altitude	Integer [-99999,+99999]
//    (Altitude in meters from mean sea level)
// 15	Latitude Decimal Degrees	Floating point [-90,90]
// 16	Longitude Decimal Degrees	Floating point [-180,180]

import atto.Atto._
import atto._

object CsvParser {

  def fromLine(line: String): Option[AirportCSV] = {
    val colon: Parser[Char] = char(':')
    val word: Parser[String] = stringOf(notChar(':'))
    val airport: Parser[AirportCSV] =
      for {
        icao <- word <~ colon
        iata <- word <~ colon
        airportName <- word <~ colon
        city <- word <~ colon
        country <- word <~ colon
        latDegrees <- int <~ colon
        latMinutes <- int <~ colon
        latSeconds <- int <~ colon
        latDirection <- letter <~ colon
        lngDegrees <- int <~ colon
        lngMinutes <- int <~ colon
        lngSeconds <- int <~ colon
        lngDirection <- letter <~ colon
        altitude <- int <~ colon
        lat <- double <~ colon
        lng <- double
      } yield
        AirportCSV(icao, iata, airportName, city, country,
          latDegrees, latMinutes, latSeconds, latDirection,
          lngDegrees, lngMinutes, lngSeconds, lngDirection,
          altitude, lat, lng)

    (airport parseOnly line).option
  }

}
