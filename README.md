# Example Schema using `sbt-contraband`

A demonstration of some of the features provided by [sbt-contraband](https://github.com/sbt/contraband). This was mainly aimed at seeing what came from generating 'pseudo case classes in Scala' via [sbt-contraband](https://github.com/sbt/contraband) and in comparison to creating [scala case classes](http://docs.scala-lang.org/tutorials/tour/case-classes.html) from the ground up.

More information on both `sbt` and `sbt-contraband` can be found via the following links.

>[..] a build tool for Scala, Java, and more.
> - http://www.scala-sbt.org

>[..] a description language for your datatypes and APIs, currently targeting Java and Scala.
> - http://www.scala-sbt.org/contraband

## Quick Start

  ```
  $ git clone git@github.com:dataday/sbt-contraband-example.git sbt-contraband-example
  $ cd sbt-contraband-example
  $ sbt
  > ~;clean;compile;package;test;run
  ```

  Please note that the terminal session is left open to take advantage of the [project watcher](https://github.com/sbt/sbt/issues/2038).

## Output

  Using [scala RPEL](http://docs.scala-lang.org/overviews/repl/overview.html).

  ```
  $ sbt
  > ~;clean;compile;package;console
  > :paste
  ```

  You can simply edit and/or paste the following scala code into the terminal session, keying CTRL+D to start interpretation.

  ```scala
  import sjsonnew.support.scalajson.unsafe.{ Converter, PrettyPrinter }
  import scala.collection.immutable.Map
  import java.util.UUID.randomUUID
  import org.joda.time.DateTime

  import api.schema._
  import codec.CustomProtocol._

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
  ```

  This should generate something like the following:

  ```scala
  import sjsonnew.support.scalajson.unsafe.{Converter, PrettyPrinter}
  import scala.collection.immutable.Map
  import java.util.UUID.randomUUID
  import org.joda.time.DateTime
  import api.schema._
  import codec.CustomProtocol._
  id: String = dc2546b4-a951-4c30-8cda-43e824add4ab
  requestObject: api.schema.Request = Request(activity, Map(id -> dc2546b4-a951-4c30-8cda-43e824add4ab, activity -> follow))
  responseObject: api.schema.Response = Response(POST, success, activity, Map(id -> dc2546b4-a951-4c30-8cda-43e824add4ab, activity -> followed), yyyy-mm-ddThh:mm:ss.SSS+01:00)
  response: String =
  {
    "method": "POST",
    "action": "success",
    "domain": "activity",
    "result": {
      "id": "dc2546b4-a951-4c30-8cda-43e824add4ab",
      "activity": "followed"
    },
    "timestamp": "yyyy-mm-ddThh:mm:ss.SSS+01:00"
  }
  ```
