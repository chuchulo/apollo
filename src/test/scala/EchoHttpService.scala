package com.apollo

import cats.effect.IO
import org.http4s._
import org.http4s.headers.{Accept, Authorization}
import org.http4s.implicits._
import org.scalatest.flatspec.AnyFlatSpec
import org.typelevel.ci.CIString
class EchoHttpAppSuite extends AnyFlatSpec {

  "GET /echo/test1/test2 without headers and cookies" should "return json response without headers and cookies" in {
    val req = Request[IO](Method.GET, uri"http://http4s.org/echo/test1/test2")
    val res = Echo.echoHttpApp.run(req).unsafeRunSync()
//    println(res.headers.get(CIString("application/json")))
//    println(res.headers)
    val expected =  """{"ip":"http4s.org","path":"/echo/test1/test2","headers":"Headers()","cookies":"Cookies()"}"""
    assertResult(expected)(res.as[String].unsafeRunSync())
  }


  "POST /echo/xxxx" should "test echo request with headers and cookies" in {
    val req = Request[IO](Method.POST,
      uri"http://dummy.org/echo/xxxx",
      HttpVersion.`HTTP/2.0`,
      Headers.of(Accept(MediaType.application.json))).addCookie("name","tomas")

    val res = Echo.echoHttpApp.run(req).unsafeRunSync()
    val expected =  """{"ip":"dummy.org","path":"/echo/xxxx","headers":"Headers(Accept: application/json, Cookie: name=tomas)","cookies":"Cookies(name=tomas)"}"""
    assertResult(expected)(res.as[String].unsafeRunSync())
  }
}