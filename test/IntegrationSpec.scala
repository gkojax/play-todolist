import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class HelloWorldSpec extends Specification {
	"Application" should {
		"respond to the index Action" in {
			val Some(result) = routeAndCall(FakeRequest(GET, "/"))

			status(result) must equalTo(SEE_OTHER)
			redirectLocation(result) must equalTo(Some("/tasks"))
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
	}
}
