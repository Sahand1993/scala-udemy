package lectures.part2oop

import playground.{Cinderella => Princess, PrinceCharming}

import java.util.Date
import java.sql.{Date => SqlDate}

import java.sql._ // imports everything

object PackagingAndImports extends App {
  // package members are accessible by their simple name
  val writer: Writer = new Writer(firstName = "Lennart", surname = "Bladh", 1961)

  // import the package
  val princess = new Princess // package.Cinderella = fully qualified name

  // packages are in hierarchy
  // matching folder structure

  // package object
  sayHello
  println(SPEED_OF_LIGHT)

  // imports
  val prince = new PrinceCharming

  // 1. Use FQ names
  val date = new Date
  val sqlDate = new SqlDate(2019, 5, 4)
  // 2. use aliasing

  // default imports
  // java.lang - String, Object, Exception
  // scala - Int, Nothing, Function
  // scala.Predef - println, ???

}
