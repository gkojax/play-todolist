package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class ModelSpec extends Specification {
  
  import models._

  "Computer model" should {
    
    "be retrieved by id" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {

        val a = Administrator
        println(a)

      }
    }  
  }
}