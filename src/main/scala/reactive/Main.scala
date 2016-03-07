package reactive

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import akka.http.scaladsl.server.Route

object Main extends App {
  implicit val system = ActorSystem("hello-api")
  implicit val executor = system.dispatcher
  implicit val timeout = Timeout(1000.millis)

  implicit val materializer = ActorMaterializer()
  val serverBinding = Http().bindAndHandle(interface = "0.0.0.0", port = 8080, handler = mainFlow)

  def mainFlow(implicit system: ActorSystem, timeout: Timeout, executor: ExecutionContext): Route = {
    get {
      complete {
        "Hello World!"
      }
    }
  }
}
