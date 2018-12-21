**airportdb** is a small tool to generate a JSON database of airports with google coordinates and google placeIds.


Two different sources are used as an example:

- one in JSON format shamelessly copied from [here](https://pastebin.com/Sz7V7Zpz)
- the [Global Airport Database](http://www.partow.net/miscellaneous/airportdatabase/) in CSV [format](http://www.partow.net/downloads/GlobalAirportDatabase.zip) 


Build the fat-jar with

```
-$ sbt assembly
```

and then export your google maps api key and run the jar

```
-$ export MAPS_API_KEY="XXXXXX"
-$ java -jar target/scala-2.12/airportdb-assembly-0.1.jar [jsonInput | csvInput]
```

and pick the output json file.

An airport is modelled by the following case class:

```
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
                      daylightSavingsTime: String,
                      timeZoneAsString: String,
                      localizations: Map[String, Localization],
                      googlePlaceId: Option[String]
                    )
```

where `Localization` is simply `case class Localization(city: String, name: String)`. The final JSON is a straightforward marshalling of the above.

