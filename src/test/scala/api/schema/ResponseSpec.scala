package api.schema

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import sjsonnew.JsonFormat
import sjsonnew.support.scalajson.unsafe.{ Converter, CompactPrinter }

import api.schema.codec._

object ResponseSpec extends Specification
  with Mockito {
  import CustomProtocol._

//  class ResponseMock {
//    def execute(
//      method: Method,
//      action: Action,
//      domain: Domain,
//      data: Map[String, String],
//      timestamp: String
//    ) = "execute"
//  }
//
//  val responseMock = mock[ResponseMock]

  val responseFixture = """{
    "method":"POST",
    "action":"success",
    "domain":"activity",
    "result":{
      "id":"e5a50c3f-d311-415f-b772-4c8969034266",
      "activity":"followed"
    },
    "timestamp":"2017-01-01T00:00:01.000+01:00"
  }"""
  .split('\n').map(_.trim.filter(_ >= ' ')).mkString

  val responseObject = Response(
    Method.POST,
    Action.success,
    Domain.activity,
    Map(
      "id" -> "e5a50c3f-d311-415f-b772-4c8969034266",
      "activity" -> Activity.followed.toString
    ),
    "2017-01-01T00:00:01.000+01:00"
  )

  "Response" should {

    "should deserialize as expected" in {
      CompactPrinter(
        Converter.toJson(
          responseObject
        ).get
      ) === responseFixture
    }

    Set(Method.GET, Method.PUT, Method.POST, Method.DELETE)
      .foreach { method =>
        s"should accept method - $method" in {
          responseObject.withMethod(method).method === method
        }
      }

    Set(Action.success, Action.error, Action.auth)
      .foreach { action =>
        s"should accept action - $action" in {
          responseObject.withAction(action).action === action
        }
      }

    Set(Domain.dashboard, Domain.notification, Domain.activity)
      .foreach { domain =>
        s"should accept domain - $domain" in {
          responseObject.withDomain(domain).domain === domain
        }
      }

    "should have the Domain of type 'activity'" in {
      responseObject.domain === Domain.activity
    }

    "should contain an activity value of 'followed'" in {
      responseObject.result.getOrElse("activity", null) === Activity.followed.toString
    }
  }
}
