package maml.ast.codec.tree

import maml.ast._

import cats.syntax.either._
import io.circe.syntax._
import org.scalacheck.Prop.forAll
import org.scalatest._
import org.scalatest.prop._


class MamlTreeCodecSpec extends PropSpec with Checkers {
  property("bijective serialization on whole tree") {
    check(forAll(Generators.genMamlTree()) { (ast: MamlTree) =>
      ast.asJson.as[MamlTree] match {
        case Left(f) =>
          println("failure", f)
          fail(f)
        case Right(mamlTree) =>
          println("succ", mamlTree)
          mamlTree == ast
      }
    })
  }

  property("bijective serialization on operation") {
    check(forAll(Generators.genBinaryOpAST(100)) { (ast: MamlTree) =>
      ast.asJson.as[MamlTree] match {
        case Left(f) =>
          println("failure", f)
          fail(f)
        case Right(mamlTree) =>
          println("succ", mamlTree)
          mamlTree == ast
      }
    })
  }
}
