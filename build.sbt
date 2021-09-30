organization := "com.example"

scalaVersion := "2.13.6"

enablePlugins(AkkaserverlessPlugin)

ThisBuild / assemblyMergeStrategy := {
  case "module-info.class" => MergeStrategy.discard
  case PathList(ps @ _*) if ps.last endsWith ".proto" => MergeStrategy.first
  // https://github.com/lightbend/akkaserverless-java-sdk/issues/535
  case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
  case PathList("google", "protobuf", _, _) => MergeStrategy.first
  case x =>
    val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
    oldStrategy(x)
}

Compile / scalacOptions ++= Seq(
  "-target:11",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlog-reflective-calls",
  "-Xlint")
Compile / javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation", "-parameters" // for Jackson
)
