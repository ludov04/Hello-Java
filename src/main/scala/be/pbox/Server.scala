package be.pbox

import java.net.InetSocketAddress

import akka.actor.{Props, Actor}
import akka.io.{IO, Tcp}



class Server extends Actor {
 
  import Tcp._
  import context.system


  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 9100))
 
  def receive = {

    case b @ Bound(localAddress) ⇒
      // do some logging or setup ...
 
    case CommandFailed(_: Bind) ⇒ context stop self
 
    case c @ Connected(remote, local) ⇒
      val handler = context.actorOf(Props[Handler])
      val connection = sender()
      connection ! Register(handler)
  }
 
}