package com.apollo

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import io.circe.literal._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze._
import pureconfig.ConfigSource
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import pureconfig.generic.auto._

object Echo extends IOApp {
  val echoService: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case x => {
      val uri = x.uri
      val headers = x.headers
      val cookie = x.cookies.mkString("Cookies(", ", ", ")")
      val ip = x.serverAddr
      Ok(json"""{"ip": $ip, "path":${uri.path.renderString}, "headers":${headers.show}, "cookies":$cookie }""")
    }
  }

  def echoHttpApp: HttpApp[IO] = Router("/echo" -> echoService).orNotFound

  case class DemoConfig(host: String, port: Int, timeoutSeconds: Int)
  val defaultConfig = DemoConfig("localhost", 9532, 5)

  def run(args: List[String]): IO[ExitCode] = {
    for {
      conf <- IO(ConfigSource.default.load[DemoConfig].getOrElse(defaultConfig))
         _ <- IO(println(s"config loaded: $conf"))
       res <- BlazeServerBuilder[IO](global)
               .bindHttp(conf.port, conf.host)
               .withHttpApp(echoHttpApp)
               .serve
               .compile
               .drain
               .as(ExitCode.Success)

//        //        .resource
//        //        .use(_ => IO.never)
//        //        .as(ExitCode.Success)
    } yield res
  }

}
