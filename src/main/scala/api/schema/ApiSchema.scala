package api.schema

import sjsonnew.JsonFormat
import sjsonnew.support.scalajson.unsafe.{ Converter, PrettyPrinter }

import scala.collection.immutable.Map
import java.util.UUID.randomUUID
import org.joda.time.DateTime

import api.schema.codec.CustomProtocol._

object ApiSchema {
  def main(args: Array[String]): Unit = {

    val id = randomUUID().toString

    val requestObject = Request(
      domain = Domain.activity,
      result = Map(
        "id" -> id,
        "activity" -> Activity.follow.toString
      )
    )


    val responseObject = Response(
      method = Method.POST,
      action = Action.success,
      domain = Domain.activity,
      result = Map(
        "id" -> id,
        "activity" -> Activity.followed.toString
      ),
      timestamp = DateTime.now.toString
    )

    val response = PrettyPrinter(
      Converter.toJsonUnsafe(responseObject)
    )

    printf("Request => %s\n", requestObject)
    printf("Response => %s\n", response)
  }
}
