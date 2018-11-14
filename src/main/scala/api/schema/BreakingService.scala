package api.schema
import java.util.UUID.randomUUID

import jdk.nashorn.internal.ir.RuntimeNode

import scala.collection.immutable.Map

class BreakingService {


  case class RsClaimSet(sub: String, sid: String, iat: String, breakingChange: String, anotherBreakingChange: String)

  def getRequest: Request = {
    val id = randomUUID().toString

    val requestObject = Request(
      domain = Domain.activity,
      result = Map(
        "id" -> id,
        "activity" -> Activity.follow.toString
      )
    )

    requestObject
  }

}
