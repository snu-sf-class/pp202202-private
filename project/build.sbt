lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "pp202202.project",
      scalaVersion := "2.13.8"
    )
  ),
  name := "pp202202-project",
  retrieveManaged := true
)

libraryDependencies += "org.parboiled" %% "parboiled" % "2.4.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % Test
