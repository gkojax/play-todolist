import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "todolist2"

  val appVersion      = "1.0"

  val appDependencies = Seq(
    jdbc,
    anorm,
    "postgresql" % "postgresql" % "8.4-702.jdbc4",
    "org.mindrot" % "jbcrypt" % "0.3m",
    "jp.t2v" %% "play21.auth" % "0.7",
    "com.github.tototoshi" %% "scala-csv" % "0.6.0",
    "com.github.nscala-time" %% "nscala-time" % "0.2.0"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature"),
    resolvers += "jbcrypt repo" at "http://mvnrepository.com/",
    resolvers += "t2v.jp repo" at "http://www.t2v.jp/maven-repo/"
  )
}
