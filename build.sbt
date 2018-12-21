name := "airportdb"
version := "0.1"
scalaVersion := "2.12.8"
scalacOptions += "-Ypartial-unification"

resolvers += Resolver.bintrayIvyRepo("com.eed3si9n", "sbt-plugins")

val circeVersion = "0.10.0"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "com.google.maps" % "google-maps-services" % "0.9.1"
libraryDependencies += "org.tpolecat" %% "atto-core" % "0.6.4"
libraryDependencies += "org.typelevel" %% "cats-core" % "1.5.0"
