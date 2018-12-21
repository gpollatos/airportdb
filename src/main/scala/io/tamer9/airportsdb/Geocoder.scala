package io.tamer9.airportsdb

import com.google.maps.model.{AddressType, GeocodingResult, LatLng}
import com.google.maps.{GeoApiContext, GeocodingApi}

object Geocoder {

  case class GoogleApiKey(key: String) extends AnyVal

  def reverseGeocode(airport: Airport)(implicit apiKey: GoogleApiKey): Airport = {
    val ctx = new GeoApiContext.Builder().apiKey(apiKey.key).build()
    val results: List[GeocodingResult] = GeocodingApi
      .reverseGeocode(ctx, new LatLng(airport.latitude, airport.longitude))
      .await().toList

    val googleResult = results.filter(_.types.contains(AddressType.AIRPORT))

    googleResult match {
      case Nil => airport
      case head :: _ => airport.copy(
        latitude = head.geometry.location.lat,
        longitude = head.geometry.location.lng,
        googlePlaceId = Some(head.placeId)
      )
    }
  }


}
