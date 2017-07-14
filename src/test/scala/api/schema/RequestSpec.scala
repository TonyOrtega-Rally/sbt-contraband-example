package api.schema

import org.specs2.mutable.Specification

import api.schema.codec._

object RequestSpec extends Specification {

  val requestObject = Request(
    Domain.activity,
    Map(
      "id" -> "e5a50c3f-d311-415f-b772-4c8969034266",
      "activity" -> Activity.follow.toString
    )
  )

  "Request" should {

    Set(Domain.dashboard, Domain.notification, Domain.activity)
      .foreach { domain =>
        s"should accept domain - $domain" in {
          requestObject.withDomain(domain).domain === domain
        }
      }

    "should have the Domain of type 'activity'" in {
      requestObject.domain === Domain.activity
    }

    "should contain an activity value of 'follow'" in {
      requestObject.result.getOrElse("activity", null) === Activity.follow.toString
    }
  }
}
