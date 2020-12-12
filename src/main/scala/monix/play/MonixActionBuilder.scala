package monix.play

import monix.execution.Scheduler
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}

class MonixActionBuilder[+R[_], B](
  underlying: ActionBuilder[R, B],
  ec: ExecutionContext
) extends ActionBuilder[R, B] {

  def scheduler: Scheduler = Scheduler(ec)

  override protected def executionContext: ExecutionContext = ec

  override def parser: BodyParser[B] = underlying.parser

  override def invokeBlock[A](
    request: Request[A],
    block: R[A] => Future[Result]
  ): Future[Result] =
    underlying.invokeBlock(request, block)
}
