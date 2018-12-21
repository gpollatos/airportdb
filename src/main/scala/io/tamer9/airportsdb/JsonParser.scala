package io.tamer9.airportsdb

object JsonParser {

  def fromJsonString(str: String): Either[Error, List[Airport]] = {
    import io.circe
    import io.circe.generic.semiauto._
    import io.circe.{parser, _}

    implicit val localizationDecoder: Decoder[Localization] = deriveDecoder[Localization]
    implicit val mapDecoder: Decoder[Map[String, Localization]] = Decoder.decodeMap[String, Localization]
    implicit val airportDecoder: Decoder[Airport] = deriveDecoder[Airport]

    val parserResult: Either[circe.Error, List[Airport]] = parser.decode[List[Airport]](str)
    parserResult match {
      case Left(e) => Left(new scala.Error(e))
      case Right(l) => Right(l)
    }
  }

  def toJsonString(airports: List[Airport]): String = {
    import io.circe.generic.semiauto._
    import io.circe.syntax._
    import io.circe.{Encoder, ObjectEncoder}

    implicit val localizationEncoder: ObjectEncoder[Localization] = deriveEncoder[Localization]
    implicit val mapEncoder: ObjectEncoder[Map[String, Localization]] = Encoder.encodeMap[String, Localization]
    implicit val airportItemEncoder: ObjectEncoder[Airport] = deriveEncoder[Airport]

    airports.asJson.toString()
  }

}
