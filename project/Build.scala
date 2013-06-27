import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "play-todolist"
  val appVersion      = "1.2"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "com.github.seratch" %% "scalikejdbc" % "[1.6,)",
    "com.github.seratch" %% "scalikejdbc-play-plugin" % "[1.6,)"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
    scalacOptions ++= Seq("-deprecation", "-feature")
  )

}
