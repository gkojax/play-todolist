import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class ControllerSpec extends Specification {
  "Application" should {
    "respond to the index Action" in {
      running(FakeApplication()) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/"))

        status(result) must equalTo(SEE_OTHER)
        redirectLocation(result) must equalTo(Some("/tasks"))
      }

    }

    "respond to the tasks Action" in {
      running(TestServer(3333)) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/tasks"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("text/html")
        charset(result) must beSome("utf-8")
        contentAsString(result) must contain("Add a new task")
      }
    }

    "respond to the new tasks Action without parameter" in {
      running(TestServer(3333)) {
        val Some(result) = routeAndCall(FakeRequest(POST, "/tasks"))

        status(result) must equalTo(BAD_REQUEST)
        redirectLocation(result) must equalTo(None)
      }
    }

    "respond to the new task Action" in {
      running(TestServer(3333)) {
        val fake = FakeRequest(POST, "/tasks").withFormUrlEncodedBody(("label", "testLabel"))
        val Some(result) = routeAndCall(fake)

        status(result) must equalTo(SEE_OTHER)
        redirectLocation(result) must equalTo(Some("/tasks"))
      }
    }

    "respond to the delete task Action" in {
      running(TestServer(3333)) {
        val Some(result) = routeAndCall(FakeRequest(POST, "/tasks/1/delete"))

        status(result) must equalTo(SEE_OTHER)
        redirectLocation(result) must equalTo(Some("/tasks"))
      }
    }
  }
}
