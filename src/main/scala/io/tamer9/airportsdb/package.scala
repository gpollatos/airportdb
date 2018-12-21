package io.tamer9

import io.tamer9.airportsdb.Geocoder.GoogleApiKey

import scala.util.{Success, Try}

package object airportsdb {

  case class Localization(city: String, name: String)

  case class Airport(
                      openflightsAirportId: Int,
                      name: String,
                      city: String,
                      country: String,
                      iataCode: String,
                      icaoCode: String,
                      latitude: Double,
                      longitude: Double,
                      elevation: Int,
                      offsetFromUtc: Option[Double],
                      daylightSavingsTime: Option[String],
                      timeZoneAsString: Option[String],
                      localizations: Map[String, Localization],
                      googlePlaceId: Option[String]
                    )


  case class AirportCSV(
                         icao: String,
                         iata: String,
                         airportName: String,
                         city: String,
                         country: String,
                         latDegrees: Integer,
                         latMinutes: Integer,
                         latSeconds: Integer,
                         latDirection: Char,
                         lngDegrees: Integer,
                         lngMinutes: Integer,
                         lngSeconds: Integer,
                         lngDirection: Char,
                         altitude: Integer,
                         lat: Double,
                         lng: Double
                       )


  def fromAirportCSV(airportCSV: AirportCSV): Airport = {
    Airport(
      -1,
      airportCSV.airportName,
      airportCSV.city,
      airportCSV.country,
      airportCSV.iata,
      airportCSV.icao,
      airportCSV.lat,
      airportCSV.lng,
      airportCSV.altitude,
      None,
      None,
      None,
      Map("en" -> Localization(airportCSV.city, airportCSV.airportName)),
      None
    )
  }

  implicit val apiKey: GoogleApiKey = Try(sys.env("MAPS_API_KEY")) match {
    case Success(value) => GoogleApiKey(value)
    case _ =>
      println("Please make sure you set the MAPS_API_KEY env var")
      sys.exit(1)
  }

}
