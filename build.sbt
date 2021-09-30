organization := "com.example"

scalaVersion := "2.13.6"

enablePlugins(AkkaserverlessPlugin, JavaAppPackaging, DockerPlugin)
//dockerBaseImage := "docker.io/library/adoptopenjdk:11-jre-hotspot"
dockerBaseImage := "nix-jre:latest"
dockerUsername := sys.props.get("docker.username")
dockerRepository := sys.props.get("docker.registry")

// To avoid clashes between sbt-native-packager and the base image:
Docker / daemonGroup := "root2"
Docker / daemonGroupGid := Some("2")

// Because java.home is "" and
// https://github.com/openjdk/jdk/blob/jdk-11%2B28/src/java.base/share/classes/jdk/internal/jimage/ImageReaderFactory.java
Docker / dockerCommands += com.typesafe.sbt.packager.docker.Cmd("WORKDIR", "/")

ThisBuild / dynverSeparator := "-"

Compile / scalacOptions ++= Seq(
  "-target:11",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlog-reflective-calls",
  "-Xlint")
Compile / javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation", "-parameters" // for Jackson
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.7" % Test
)
