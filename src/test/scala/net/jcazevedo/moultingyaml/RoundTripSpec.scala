package net.jcazevedo.moultingyaml

import java.io.File
import java.net.URLDecoder

import scala.io.Source

import org.scalatest.Inspectors._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class RoundTripSpec extends AnyFlatSpec {
  def getResourceURL(resource: String): String =
    URLDecoder.decode(getClass.getResource(resource).getFile, "UTF-8")

  "The parsing of YAMLs" should "work in a round trip fashion" in {
    val files = new File(getResourceURL("/examples")).listFiles()
    forAll(files) { file =>
      val yamls = Source.fromFile(file).mkString.parseYamls
      forAll(yamls) { innerYaml =>
        innerYaml.prettyPrint.parseYaml should ===(innerYaml)
      }
    }
  }
}
