package be.pbox

import akka.actor.Actor
import akka.pattern.ask
import akka.io.{IO, Tcp}
import akka.util.{Timeout, ByteString}
import spray.can.Http
import spray.http._
import spray.client.pipelining._
import scala.concurrent.duration._
import scala.concurrent.Future
import akka.event.Logging
import akka.event.LoggingAdapter

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class Handler extends Actor {
  val log = Logging(context.system, this)
  implicit val system = context.system
  import system.dispatcher
  import Tcp._

  def receive = {
    case Received(data) => {
      val receipt = data.decodeString("ISO-8859-1")
      val requestor = sender()
      implicit val timeout = Timeout(5 seconds)
      val pipeline: Future[SendReceive] =
        for (
          Http.HostConnectorInfo(connector, _) <-
          IO(Http) ? Http.HostConnectorSetup("localhost", port = 8080)
        ) yield sendReceive(connector)

      val request = Post("/receipt/new")
      val response: Future[HttpResponse] = pipeline.flatMap(pipeline => pipeline(request))
       response.map { response =>
         requestor ! Write(ByteString(response.toString))
       }

    }
    case PeerClosed => context stop self
  }
}