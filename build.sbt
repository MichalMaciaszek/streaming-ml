name := "streaming-ml"

version := "0.1"

scalaVersion := "2.11.12"
val flinkVersion = "1.9.1"
val log4jVersion = "2.11.0"

val dependencies = Seq(
  // Flink
  // https://mvnrepository.com/artifact/org.apache.flink/flink-scala
  "org.apache.flink" %% "flink-scala" % flinkVersion,
  // https://mvnrepository.com/artifact/org.apache.flink/flink-core
  "org.apache.flink" % "flink-core" % flinkVersion,
  // https://mvnrepository.com/artifact/org.apache.flink/flink-streaming-scala
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion,
  "org.apache.flink" %% "flink-runtime-web" % flinkVersion,

  "org.scalatest" %% "scalatest" % "3.0.5" % Test,

  // logging
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion % Runtime,
  "org.apache.logging.log4j" % "log4j-api" % log4jVersion % Runtime,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion % Runtime,
  "org.apache.logging.log4j" % "log4j-jcl" % log4jVersion % Runtime
)

libraryDependencies ++= dependencies
libraryDependencies += "com.opendatagroup" % "hadrian" % "0.8.5"
// override default resource directory
resourceDirectory in Compile := baseDirectory.value / "src/main/resources"
