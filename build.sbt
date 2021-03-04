

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    name := "ecs-shell",
    version := "0.0.1",
    scalaVersion := "2.12.4",
    Defaults.itSettings,
    parallelExecution in Test := false,
    libraryDependencies ++= Seq(
      "software.amazon.awssdk" % "aws-sdk-java" % "2.16.11"
    )
  )
