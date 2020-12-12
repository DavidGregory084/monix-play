package monix.play

import monix.eval.Task
import play.api.mvc.{Action, ActionBuilder, AnyContent, Result}
import play.api.mvc.BodyParser
import scala.concurrent.ExecutionContext

object syntax {
  implicit class MonixPlayActionBuilderSyntax[+R[_], B](
    underlying: ActionBuilder[R, B]
  ) extends MonixPlayActionBuilderOps[R, B] {
    def actionBuilder: ActionBuilder[R, B] = underlying
  }

  abstract class MonixPlayActionBuilderOps[+R[_], B] {
    def actionBuilder: ActionBuilder[R, B]

    def task(
      block: => Task[Result]
    )(implicit ec: ExecutionContext): Action[AnyContent] = {
      val taskBuilder = new MonixActionBuilder[R, B](actionBuilder, ec)
      taskBuilder.async { block.runToFuture(taskBuilder.scheduler) }
    }

    def task(
      block: R[B] => Task[Result]
    )(implicit ec: ExecutionContext): Action[B] = {
      val taskBuilder = new MonixActionBuilder[R, B](actionBuilder, ec)
      taskBuilder.async { block(_).runToFuture(taskBuilder.scheduler) }
    }

    def task[A](
      bodyParser: BodyParser[A]
    )(block: R[A] => Task[Result])(implicit ec: ExecutionContext): Action[A] = {
      val taskBuilder = new MonixActionBuilder[R, B](actionBuilder, ec)
      taskBuilder.async(bodyParser) {
        block(_).runToFuture(taskBuilder.scheduler)
      }
    }
  }
}
