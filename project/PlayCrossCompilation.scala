import uk.gov.hmrc.playcrosscompilation.AbstractPlayCrossCompilation
import uk.gov.hmrc.playcrosscompilation.PlayVersion._

object PlayCrossCompilation
    extends AbstractPlayCrossCompilation(defaultPlayVersion = Play27) {
      override def playScalaVersion(crossScalaVersions: Seq[String])(scalaVersion: String): String = playVersion match {
        case Play26 => crossScalaVersions.find(!_.startsWith("2.13")).getOrElse(scalaVersion)
        case _ => super.playScalaVersion(crossScalaVersions)(scalaVersion)
      }
      override def playCrossScalaBuilds(crossScalaVersions: Seq[String]): Seq[String] = playVersion match {
        case Play26 => crossScalaVersions.filterNot(_.startsWith("2.13"))
        case _ => super.playCrossScalaBuilds(crossScalaVersions)
      }
    }
