package api.schema
import java.util.UUID.randomUUID


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

  def getGreeting: Greetings = {
    val  greetings = Greetings(sub = "sub", iss = "iss", rel = "csr", iat = None )


    greetings
  }

}

/* //uncomment after you have the latest version in your local repo
object BreakingService {
  def main(args: Array[String]): Unit = {

    val breakingService = new BreakingService

    val greeting = breakingService.getGreeting

    /* //uncomment after v3.3.0 fields are added  in schema.contra

        println(Greetings.sayHi)
       println(greeting.customToString)
    */
    println(greeting.toString)

  }

}
*/
