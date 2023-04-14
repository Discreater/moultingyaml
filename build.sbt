
ThisBuild / scalaVersion := "2.13.10"
ThisBuild / version := "0.4.3-SNAPSHOT"
ThisBuild / organization := "net.jcazevedo"

crossScalaVersions := Seq("2.13.10", "2.12.17")

val isScala3 = Def.setting(
  CrossVersion.partialVersion(scalaVersion.value).exists(_._1 == 3)
)

lazy val root = (project in file("."))
  .settings(
    name := "moultingyaml",
    libraryDependencies ++= Seq(
      "com.github.nscala-time" %% "nscala-time" % "2.33.0-SNAPSHOT",
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.yaml" % "snakeyaml" % "1.32",
      "org.scalatest" %% "scalatest" % "3.2.15" % "test"
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked",
      "-feature",
      "-language:implicitConversions") ++
      (CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((3, major)) => Seq("-XSemanticdb")
        case Some((2, major)) if major >= 13 => Seq("-Ywarn-unused:imports", "-Yrangepos")
        case Some((2, major)) if major >= 11 => Seq("-Ywarn-unused-import", "-Yrangepos")
        case _ => Seq()
      }),
    addCompilerPlugin("org.scalameta" % "semanticdb-scalac" % "4.7.6" cross CrossVersion.full),
    Compile / console / scalacOptions ~= (_ filterNot (Set("-Ywarn-unused:imports", "-Ywarn-unused-import").contains)),
    Test / console / scalacOptions := ( Compile / console / scalacOptions).value,
  )

//scalariformPreferences := scalariformPreferences.value
//  .setPreference(DanglingCloseParenthesis, Prevent)
//  .setPreference(DoubleIndentConstructorArguments, true)

//publishMavenStyle := true
//
//publishTo := Some(
//  if (version.value.trim.endsWith("SNAPSHOT"))
//    "snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
//  else
//    "releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
//
//publishArtifact in Test := false
//
//pomIncludeRepository := { _ => false }
//
//licenses := Seq("MIT License" ->
//  url("http://www.opensource.org/licenses/mit-license.php"))
//
//homepage := Some(url("https://github.com/jcazevedo/moultingyaml"))
//
//pomExtra := (
//  <scm>
//    <url>https://github.com/jcazevedo/moultingyaml.git</url>
//    <connection>scm:git:git@github.com:jcazevedo/moultingyaml.git</connection>
//  </scm>
//  <developers>
//    <developer>
//      <id>jcazevedo</id>
//      <name>Joao Azevedo</name>
//      <url>http://jcazevedo.net</url>
//    </developer>
//  </developers>)
