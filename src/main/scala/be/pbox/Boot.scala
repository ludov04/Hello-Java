package be.pbox
import akka.actor.{ActorSystem, Actor, ActorRef, Props}
import akka.io.{ IO, Tcp }
import akka.util.{Timeout, ByteString}
import java.net.InetSocketAddress


object Boot extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")
  // create and start our service actor
  val server = system.actorOf(Props[Server], "PBOX")


  // start a new HTTP server on port 8080 with our service actor as the handler
 
}
