package monix.play

import javax.inject.Inject
import monix.eval.Task
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents, Results}
import play.api.test.{FakeRequest, Helpers}

import scala.concurrent.ExecutionContext

class TestController @Inject() (
  cc: ControllerComponents,
  implicit val ec: ExecutionContext
) extends AbstractController(cc) {

  import monix.play.syntax._

  def hello = Action.task {
    Task.evalAsync { Ok("Hello") }
  }

  def helloAddress = Action.task { r =>
    Task.evalAsync { Ok("Hello " + r.remoteAddress) }
  }

  def echoJson = Action.task(parse.json) { r =>
    Task.evalAsync { Ok(r.body) }
  }
}

class MonixActionBuilderSuite extends munit.FunSuite {
  implicit val ec: ExecutionContext = ExecutionContext.global

  Helpers.running() { app =>
    val controller = app.injector.instanceOf[TestController]

    test("Action.task ignoring request") {
      controller.hello(FakeRequest()).map { response =>
        assertEquals(response, Results.Ok("Hello"))
      }
    }

    test("Action.task using request") {
      controller.helloAddress(FakeRequest()).map { response =>
        assertEquals(response, Results.Ok("Hello 127.0.0.1"))
      }
    }

    test("Action.task using action parser") {
      val request = FakeRequest().withBody(Json.obj("foo" -> Json.arr(1)))

      controller.echoJson(request).map { response =>
        assertEquals(
          response,
          Results.Ok("""{"foo":[1]}""").as("application/json")
        )
      }
    }
  }
}
