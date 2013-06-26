package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class ApplicationSpec extends Specification {
  
  "Application" should {
    
    "send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }
    
    "render login form on index" in {
      running(FakeApplication()) {
        val home = route(FakeRequest(GET, "/")).get
        
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain("TO-DO LIST | login")
      }
    }

    "render index template" in {
      val html = views.html.login("Coco")

      contentType(html) must equalTo("text/html")
      contentAsString(html) must contain("TO-DO LIST")
    }
  }
}
