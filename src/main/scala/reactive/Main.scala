package reactive

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.javadsl.server.values.Headers
import akka.http.scaladsl.model.ContentType
import akka.http.scaladsl.model.MediaType
import scala.concurrent.Future
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import RollProtocol._
import akka.pattern.CircuitBreaker
import scala.util.Success

object Main extends App {
  implicit val system = ActorSystem("die-api")
  implicit val executor = system.dispatcher
  implicit val timeout = Timeout(1000.millis)

  implicit val materializer = ActorMaterializer()
  val serverBinding = Http().bindAndHandle(interface = "0.0.0.0", port = 8080, handler = mainFlow)

  val guard = CircuitBreaker.apply(system.scheduler, 1, 1.second, 5.second)

  def mainFlow(implicit system: ActorSystem, timeout: Timeout, executor: ExecutionContext): Route = {
    get {
      onSuccess(Roller.roll(Die(6))) { roll =>
        complete {
          roll
        }
      }
    }
    path("slow") {
      get {
        val guarded = guard.withCircuitBreaker(Roller.rollSlow(Die(6)))
        onComplete(guarded) {
          case Success(roll) => complete(roll)
          case _             => complete("Server Busy")
        }
      }
    }
  }
}
