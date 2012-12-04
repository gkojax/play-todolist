package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
class IntegrationSpec extends Specification {
  
  "Application" should {
    
    "work from within a browser" in {
      running(TestServer(3333), HTMLUNIT) { browser =>
       
        // Top Page
        browser.goTo("http://localhost:3333/")
        browser.url must equalTo("http://localhost:3333/tasks")
        browser.pageSource must contain("Todo list")
        browser.pageSource must contain("Add a new task")

        // create Label NG
        browser.$("#submit").click()
        browser.url must equalTo("http://localhost:3333/tasks")
        browser.pageSource must contain("This field is required")

        // create Label OK
        browser.$("#label").text("testLabel")
        browser.$("#submit").click()
        browser.url must equalTo("http://localhost:3333/tasks")
        browser.$("dd.error").size must equalTo(0)
      }
    }
  }
}

